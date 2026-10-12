package com.jcooldevelopment.easybank_api.feature;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.CustomProblemDetail;
import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.dto.IncidenceType.IncidenceTypeDto;
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

    @BeforeAll
    public void beforeAll() {
        this.incidenceTypeRepository.deleteAll();
    }

    @BeforeEach
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

        var result = mockMvcTester
            .get()
            .uri("/api/incidencetype")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result)
            .hasStatus(HttpStatus.UNAUTHORIZED);
    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void getAllIncidenceTypes_AsClient() throws Exception {
        this.createMockIncidenceTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/incidencetype")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        String json = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<List<IncidenceTypeDto>> apiresponse = objectMapper.readValue(json, new TypeReference<Apiresponse<List<IncidenceTypeDto>>>(){});
        
        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(200);
        assertThat(apiresponse.getData()).hasSize(3);
        assertThat(apiresponse.getData().get(1)).hasOnlyFields("id", "name");
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getAllIncidenceTypes_AsAdmin() throws Exception {
        this.createMockIncidenceTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/incidencetype")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        String json = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<List<IncidenceTypeDto>> apiresponse = objectMapper.readValue(json, new TypeReference<Apiresponse<List<IncidenceTypeDto>>>(){});
        
        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(200);
        assertThat(apiresponse.getData()).hasSize(3);
        assertThat(apiresponse.getData().get(1)).hasOnlyFields("id", "name");
    }

    @Test
    public void getIncidenceType_NoCrendentials() throws Exception {
        ArrayList<IncidenceType> incidenceTypes = this.createMockIncidenceTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/incidencetype/" + incidenceTypes.get(1).getId())
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(401);
        
    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void getIncidenceType_AsClient() throws Exception {
        ArrayList<IncidenceType> incidenceTypes = this.createMockIncidenceTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/incidencetype/" + incidenceTypes.get(1).getId())
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        String json = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<IncidenceTypeDto> apiresponse = objectMapper.readValue(json, new TypeReference<Apiresponse<IncidenceTypeDto>>(){});
        
        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(200);
        assertThat(apiresponse.getData().getId()).isEqualTo(incidenceTypes.get(1).getId());
        assertThat(apiresponse.getData().getName()).isEqualTo(incidenceTypes.get(1).getName());
        assertThat(apiresponse.getData()).hasOnlyFields("id", "name");
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getIncidenceType_AsAdmin() throws Exception {
        ArrayList<IncidenceType> incidenceTypes = this.createMockIncidenceTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/incidencetype/" + incidenceTypes.get(1).getId())
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        String json = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<IncidenceTypeDto> apiresponse = objectMapper.readValue(json, new TypeReference<Apiresponse<IncidenceTypeDto>>(){});
        
        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(200);
        assertThat(apiresponse.getData().getId()).isEqualTo(incidenceTypes.get(1).getId());
        assertThat(apiresponse.getData().getName()).isEqualTo(incidenceTypes.get(1).getName());
        assertThat(apiresponse.getData()).hasOnlyFields("id", "name");
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getIncidenceType_AsAdmin_NotFound() throws Exception {
        ArrayList<IncidenceType> incidenceTypes = this.createMockIncidenceTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/incidencetype/" + incidenceTypes.get(1).getId() + 1)
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result)
            .hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    public void postIncidenceType_NoCrendentials() throws Exception {
        String mockIncidenceTypeName = "Transfer error";
        String requestBody = String.format("""
            {
                "name": "%s"
            }
            """,
            mockIncidenceTypeName
        );

        var result = mockMvcTester
            .post()
            .uri("/api/incidencetype")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(401);
    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void postIncidenceType_AsClient() throws Exception {
        String mockIncidenceTypeName = "Transfer error";
        String requestBody = String.format("""
            {
                "name": "%s"
            }
            """,
            mockIncidenceTypeName
        );

        var result = mockMvcTester
            .post()
            .uri("/api/incidencetype")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(403);
        
        var searchForIncidenceType = this.incidenceTypeRepository.findByName(mockIncidenceTypeName);

        assertThat(searchForIncidenceType).isEmpty();
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void postIncidenceType_AsAdmin() throws Exception {
        String mockIncidenceTypeName = "Transfer error";
        String requestBody = String.format("""
            {
                "name": "%s"
            }
            """,
            mockIncidenceTypeName
        );

        var result = mockMvcTester
            .post()
            .uri("/api/incidencetype")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<IncidenceTypeDto> apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<Apiresponse<IncidenceTypeDto>>(){});

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(201);
        assertThat(apiresponse.getMessage()).isEqualTo("Incidence type saved.");
        assertThat(apiresponse.getData().getName()).isEqualTo(mockIncidenceTypeName);
        assertThat(apiresponse.getData()).isInstanceOf(IncidenceTypeDto.class);

        // Validate if only the created incidence type is in database
        List<IncidenceType> incidenceTypesFromRepo = this.incidenceTypeRepository.findAll();

        assertThat(incidenceTypesFromRepo.size()).isEqualTo(1);
        assertThat(incidenceTypesFromRepo.get(0).getName()).isEqualTo(mockIncidenceTypeName);
    }

    // https://www.baeldung.com/parameterized-tests-junit-5
    @ParameterizedTest // For using various parameters in one test. @Test is no needed.
    @ValueSource(strings = {"", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    @WithMockUser(value = "user", roles = "ADMIN")
    public void postIncidenceType_AsAdmin_BadFormat(String incidenceTypeName) throws Exception {
        String requestBody = String.format("""
            {
                "name": ""
            }
            """,
            incidenceTypeName
        );

        var result = mockMvcTester
            .post()
            .uri("/api/incidencetype")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(422);
        
        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        CustomProblemDetail apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<CustomProblemDetail>(){});

        assertThat(apiresponse.getStatus()).isEqualTo(422);
        assertThat(apiresponse.getErrors()).containsKey("name");
        assertThat(apiresponse.getTitle()).isEqualTo("Request body not valid");
        assertThat(apiresponse.getType()).isEqualTo(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/422"));
        assertThat(apiresponse.getDetail()).isEqualTo("One or more fields have wrong format.");
    }

    @Test
    public void putIncidenceType_NoCrendentials() throws Exception {
        ArrayList<IncidenceType> incidenceTypes = this.createMockIncidenceTypes();

        String requestBody = String.format("""
            {
                "name": ""
            }
            """
        );

        var result = mockMvcTester
            .put()
            .uri("/api/incidencetype/" + incidenceTypes.get(1).getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(401);
    
        // Verify if incidence type in database has not been updated
        Optional<IncidenceType> incidenceTypeInDatabase = this.incidenceTypeRepository.findById(incidenceTypes.get(1).getId());
        
        assertThat(incidenceTypeInDatabase.get().getName()).isEqualTo(incidenceTypes.get(1).getName());
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
