package com.jcooldevelopment.easybank_api.feature;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.repository.IncidenceTypeRepository;

@SpringBootTest 
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IncidenceTypeTest {
    
    @Autowired 
    MockMvcTester mockMvcTester;

    @Autowired 
    IncidenceTypeRepository incidenceTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    String mockIncidenceTypeName = "Question";

    public void beforeAll() {
        this.incidenceTypeRepository.deleteAll();
    }

    public void beforeEach() {
        this.incidenceTypeRepository.deleteAll();
    }

    private IncidenceType createMockIncidenceType(){
        IncidenceType incidenceType = new IncidenceType();
        incidenceType.setName(mockIncidenceTypeName);
        return this.incidenceTypeRepository.save(incidenceType);
    }

    @Test
    public void getAllIncidenceTypes_NoCrendentials() throws Exception {

    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void getAllIncidenceTypes_AsClient() throws Exception {
        
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getAllIncidenceTypes_AsAdmin() throws Exception {
        
    }

    @Test
    public void getIncidenceType_NoCrendentials() throws Exception {

    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void getIncidenceType_AsClient() throws Exception {
        
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getIncidenceType_AsAdmin() throws Exception {
        
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getIncidenceType_AsAdmin_NotFound() throws Exception {

    }

    @Test
    public void postIncidenceType_NoCrendentials() throws Exception {

    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void postIncidenceType_AsClient() throws Exception {
        
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void postIncidenceType_AsAdmin() throws Exception {
        
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void postIncidenceType_AsAdmin_BadFormat() throws Exception {

    }

    @Test
    public void putIncidenceType_NoCrendentials() throws Exception {

    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void putIncidenceType_AsClient() throws Exception {
        
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void putIncidenceType_AsAdmin() throws Exception {
        
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void putIncidenceType_AsAdmin_NotFound() throws Exception {

    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void putIncidenceType_AsAdmin_BadFormat() throws Exception {

    }

    @Test
    public void deleteIncidenceType_NoCrendentials() throws Exception {

    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void deleteIncidenceType_AsClient() throws Exception {
        
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void deleteIncidenceType_AsAdmin() throws Exception {
        
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void deleteIncidenceType_AsAdmin_NotFound() throws Exception {

    }

}
