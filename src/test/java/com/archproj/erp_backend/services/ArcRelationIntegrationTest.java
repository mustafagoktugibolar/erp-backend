package com.archproj.erp_backend.services;

import com.archproj.erp_backend.models.ArcObject;
import com.archproj.erp_backend.models.Company;
import com.archproj.erp_backend.utils.CompanyTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArcRelationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ArcRelationService arcRelationService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ArcObjectService arcObjectService;

    @Test
    void testCompanyToArcObjectTrigger() throws Exception {
        // 1. Create a Company (Source)
        Company company = new Company();
        company.setName("Trigger Source Company");
        company.setEmail("trigger@source.com");
        company.setType(CompanyTypeEnum.CORPORATION);
        Map<String, Object> companyData = new HashMap<>();
        companyData.put("status", "INACTIVE");
        company.setData(companyData);

        Company savedCompany = companyService.createCompany(company);
        Long sourceId = savedCompany.getId();

        // 2. Create an ArcObject (Target)
        ArcObject target = new ArcObject();
        target.setModuleId(99L);
        Map<String, Object> targetData = new HashMap<>();
        targetData.put("name", "Target Object");
        targetData.put("clientStatus", "STANDARD");
        target.setData(targetData);

        ArcObject savedTarget = arcObjectService.save(target);
        Long targetId = savedTarget.getArc_object_id();

        // 3. Create Relation with Rule
        // Rule: If Company status becomes ACTIVE, Target clientStatus becomes VIP
        String settingsJson = "{\"triggerField\":\"status\", \"targetField\":\"clientStatus\", \"valueMapping\":{\"ACTIVE\":\"VIP\"}}";

        arcRelationService.createRelation(
                "COMPANY", sourceId,
                "ARC_OBJECT", targetId,
                "SYNC_TEST",
                settingsJson);

        // 4. Update Company Status to ACTIVE
        savedCompany.setName("Updated Source");
        Map<String, Object> newData = new HashMap<>();
        newData.put("status", "ACTIVE");
        savedCompany.setData(newData);

        // Call API or Service directly? Integrating testing usually hits API or
        // Service.
        // Let's use Service to ensure event firing works.
        // Note: Controller calls service.updateCompany which fires event.
        companyService.updateCompany(sourceId, savedCompany);

        // 5. Verify Target Update
        // Wait a bit for async event? Default ApplicationEvents are synchronous unless
        // configured otherwise.
        // Ours are synchronous in ArcRelationListener.

        ArcObject updatedTarget = arcObjectService.getById(targetId);
        String newStatus = (String) updatedTarget.getData().get("clientStatus");

        assertThat(newStatus).isEqualTo("VIP");
    }
}
