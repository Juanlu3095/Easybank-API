package com.jcooldevelopment.easybank_api.feature;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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

    @Autowired 
    private Faker faker;

    String mockIncidenceTypeName = "Question";

    public void beforeAll() {
        this.incidenceTypeRepository.deleteAll();
    }

    public void beforeEach() {
        this.incidenceTypeRepository.deleteAll();
    }

    private ArrayList<IncidenceType> createMockIncidenceTypes(){
        ArrayList<IncidenceType> incidenceTypes = new ArrayList<>();
        // Creates 3 random incidenceTypes
        // https://stackoverflow.com/questions/69279535/java-spring-boot-how-to-seed-a-database-like-in-php-laravel
        for(int i = 0; i <3; i++) {
            IncidenceType newIncidenceType = new IncidenceType();
            newIncidenceType.setName(faker.funnyName().name());
            IncidenceType savedIncidenceType = this.incidenceTypeRepository.save(newIncidenceType);
            incidenceTypes.add(savedIncidenceType);
        }
        return incidenceTypes;
    }

    @Test
    public void getAllIncidenceTypes_NoCrendentials() throws Exception {
        this.createMockIncidenceTypes();
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
