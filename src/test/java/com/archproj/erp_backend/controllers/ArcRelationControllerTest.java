package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.entities.ArcRelationEntity;
import com.archproj.erp_backend.services.ArcRelationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArcRelationController.class)
class ArcRelationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArcRelationService arcRelationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createRelation_ShouldReturnCreatedRelation() throws Exception {
        // Arrange
        ArcRelationController.CreateRelationRequest request = new ArcRelationController.CreateRelationRequest(
                "COMPANY", 1L, "CUSTOMER", 2L, "PARTNER", "{}");

        ArcRelationEntity createdEntity = new ArcRelationEntity("COMPANY", 1L, "CUSTOMER", 2L, "PARTNER", "{}");
        createdEntity.setId(10L);

        given(arcRelationService.createRelation(
                anyString(), any(Long.class), anyString(), any(Long.class), anyString(), anyString()))
                .willReturn(createdEntity);

        // Act & Assert
        mockMvc.perform(post("/api/relations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.sourceType").value("COMPANY"))
                .andExpect(jsonPath("$.relationType").value("PARTNER"));
    }

    @Test
    void getRelations_ShouldReturnList() throws Exception {
        // Arrange
        ArcRelationEntity entity = new ArcRelationEntity("COMPANY", 1L, "CUSTOMER", 2L, "PARTNER", "{}");
        given(arcRelationService.getRelationsBySource("COMPANY", 1L))
                .willReturn(List.of(entity));

        // Act & Assert
        mockMvc.perform(get("/api/relations")
                .param("sourceType", "COMPANY")
                .param("sourceId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sourceType").value("COMPANY"));
    }

    @Test
    void getRelations_ShouldReturnAllRelations_WhenNoParamsProvided() throws Exception {
        // Arrange
        ArcRelationEntity entity = new ArcRelationEntity("COMPANY", 1L, "CUSTOMER", 2L, "PARTNER", "{}");
        given(arcRelationService.getAllRelations()).willReturn(List.of(entity));

        // Act & Assert
        mockMvc.perform(get("/api/relations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sourceType").value("COMPANY"));
    }

    @Test
    void deleteRelation_ShouldReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(arcRelationService).deleteRelation(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/relations/1"))
                .andExpect(status().isNoContent());
    }
}
