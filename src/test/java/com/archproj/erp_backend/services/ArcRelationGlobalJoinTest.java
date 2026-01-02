package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.ArcObjectEntity;
import com.archproj.erp_backend.entities.ArcRelationEntity;
import com.archproj.erp_backend.entities.ModuleEntity;
import com.archproj.erp_backend.models.ArcObject;
import com.archproj.erp_backend.repositories.ArcObjectRepository;
import com.archproj.erp_backend.repositories.ArcRelationRepository;
import com.archproj.erp_backend.repositories.ModuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ArcRelationGlobalJoinTest {

    @Autowired
    private ArcRelationService arcRelationService;

    @Autowired
    private ArcObjectService arcObjectService;

    @Autowired
    private ArcRelationRepository arcRelationRepository;

    @Autowired
    private ArcObjectRepository arcObjectRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    public void testGlobalSmartTriggerWithJoinKey() throws InterruptedException {
        // 1. Create Modules
        ModuleEntity projectModule = new ModuleEntity();
        projectModule.setName("PROJECT_MODULE");
        projectModule = moduleRepository.save(projectModule);

        ModuleEntity invoiceModule = new ModuleEntity();
        invoiceModule.setName("INVOICE_MODULE");
        invoiceModule = moduleRepository.save(invoiceModule);

        // 2. Create Source Objects (Projects)
        // Project A (Matches)
        ArcObject projectA = new ArcObject();
        projectA.setModuleId(projectModule.getId());
        projectA.setData(new HashMap<>(Map.of("project_code", "PRJ-100", "status", "ACTIVE")));
        projectA = arcObjectService.save(projectA);

        // 3. Create Target Objects (Invoices)
        // Invoice 1 (Should Match PRJ-100)
        ArcObject invoice1 = new ArcObject();
        invoice1.setModuleId(invoiceModule.getId());
        invoice1.setData(new HashMap<>(Map.of("linked_project_code", "PRJ-100", "invoice_status", "DRAFT")));
        invoice1 = arcObjectService.save(invoice1);

        // Invoice 2 (Should NOT Match - different code)
        ArcObject invoice2 = new ArcObject();
        invoice2.setModuleId(invoiceModule.getId());
        invoice2.setData(new HashMap<>(Map.of("linked_project_code", "PRJ-999", "invoice_status", "DRAFT")));
        invoice2 = arcObjectService.save(invoice2);

        // 4. Define GLOBAL Rule
        // When PROJECT status becomes "COMPLETED", find INVOICE where
        // linked_project_code == project_code, and set invoice_status = "ISSUED"
        String settingsJson = """
                {
                    "triggerField": "status",
                    "targetField": "invoice_status",
                    "valueMapping": {
                        "COMPLETED": "ISSUED"
                    },
                    "joinKeySource": "project_code",
                    "joinKeyTarget": "linked_project_code"
                }
                """;

        arcRelationService.createRelation(
                "PROJECT_MODULE",
                -1L, // Source -1 (Global)
                "INVOICE_MODULE",
                -1L, // Target -1 (Global)
                "SMART_TRIGGER",
                settingsJson);

        // 5. Trigger the Update
        projectA.set("status", "COMPLETED");
        arcObjectService.save(projectA); // This publishes event -> listener triggers global update

        // 6. Verify Results
        // Invoice 1 should be UPDATED to "ISSUED" because PRJ-100 matched
        ArcObject updatedInvoice1 = arcObjectService.getById(invoice1.getArc_object_id());
        Assertions.assertEquals("ISSUED", updatedInvoice1.get("invoice_status"),
                "Invoice 1 should have been updated via Join Key");

        // Invoice 2 should REMAIN "DRAFT" because PRJ-999 did not match
        ArcObject updatedInvoice2 = arcObjectService.getById(invoice2.getArc_object_id());
        Assertions.assertEquals("DRAFT", updatedInvoice2.get("invoice_status"), "Invoice 2 should not change");
    }
}
