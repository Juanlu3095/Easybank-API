package com.jcooldevelopment.easybank_api.feature;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.CustomProblemDetail;
import com.jcooldevelopment.easybank_api.contracts.entity.AccountType;
import com.jcooldevelopment.easybank_api.dto.AccountType.AccountTypeDto;
import com.jcooldevelopment.easybank_api.repository.AccountTypeRepository;

@SpringBootTest 
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountTypeTest {

    @Autowired 
    MockMvcTester mockMvcTester;

    @Autowired 
    AccountTypeRepository accountTypeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired 
    private Faker faker;

    @BeforeEach 
    public void beforeEach() {
        this.accountTypeRepository.deleteAll();
    }

    @BeforeAll 
    public void beforeAll() {
        this.accountTypeRepository.deleteAll();
    }

    private ArrayList<AccountType> createMockAccountTypes(){
        ArrayList<AccountType> accountTypes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            AccountType accountType = new AccountType();
            accountType.setName(this.faker.funnyName().name());
            accountType.setTerms(this.faker.dragonBall().character());
            AccountType savedAccountType = this.accountTypeRepository.save(accountType);
            accountTypes.add(savedAccountType);
        }
        return accountTypes;
    }

    @Test 
    public void getAllAccountTypes_NoCredentials() throws Exception{
        this.createMockAccountTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/accounttype")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(401);
        // Again, Spring Security passes exception to filters and we can't see the instance class
        // (result.getMvcResult().getResolvedException()).isInstanceOf(AuthenticationException.class);

        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        ProblemDetail apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<ProblemDetail>(){});

        // This properties are set in AuthorizationExceptionHandler class
        assertThat(apiresponse.getTitle()).isEqualTo("User not authenticated");
        assertThat(apiresponse.getStatus()).isEqualTo(401);
        assertThat(apiresponse.getDetail()).isEqualTo("You need to log to access data.");
        assertThat(apiresponse.getType()).isEqualTo(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/401"));
        
    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void getAllAccountTypes_AsClient() throws Exception{
        ArrayList<AccountType> accountTypes = this.createMockAccountTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/accounttype")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result).hasStatus(HttpStatus.OK);    

        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<List<AccountTypeDto>> apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<Apiresponse<List<AccountTypeDto>>>(){});

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(200);
        assertThat(apiresponse.getData()).hasSize(3);
        assertThat(apiresponse.getData().get(1)).hasOnlyFields("id", "name", "terms");
        assertThat(apiresponse.getData().get(0).getName()).isEqualTo(accountTypes.get(0).getName());
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getAllAccountTypes_AsAdmin() throws Exception{
        ArrayList<AccountType> accountTypes = this.createMockAccountTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/accounttype")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result).hasStatus(HttpStatus.OK);    

        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<List<AccountTypeDto>> apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<Apiresponse<List<AccountTypeDto>>>(){});

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(200);
        assertThat(apiresponse.getData()).hasSize(3);
        assertThat(apiresponse.getData().get(1)).hasOnlyFields("id", "name", "terms");
        assertThat(apiresponse.getData().get(0).getName()).isEqualTo(accountTypes.get(0).getName());

    }

    @Test 
    public void getAccountType_NoCredentials() throws Exception{
        ArrayList<AccountType> accountTypes = this.createMockAccountTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/accounttype/" + accountTypes.get(1).getId())
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(401);
        
        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        ProblemDetail apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<ProblemDetail>(){});

        // This properties are set in AuthorizationExceptionHandler class
        assertThat(apiresponse.getTitle()).isEqualTo("User not authenticated");
        assertThat(apiresponse.getStatus()).isEqualTo(401);
        assertThat(apiresponse.getDetail()).isEqualTo("You need to log to access data.");
        assertThat(apiresponse.getType()).isEqualTo(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/401"));
        
    }   

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void getAccountType_AsClient() throws Exception{
        ArrayList<AccountType> accountTypes = this.createMockAccountTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/accounttype/" + accountTypes.get(1).getId())
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        String json = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<AccountTypeDto> apiresponse = objectMapper.readValue(json, new TypeReference<Apiresponse<AccountTypeDto>>(){});
        
        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(200);
        assertThat(apiresponse.getData().getId()).isEqualTo(accountTypes.get(1).getId());
        assertThat(apiresponse.getData().getName()).isEqualTo(accountTypes.get(1).getName());
        assertThat(apiresponse.getData().getTerms()).isEqualTo(accountTypes.get(1).getTerms());
        assertThat(apiresponse.getData()).hasOnlyFields("id", "name", "terms");
    
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getAccountType_AsAdmin() throws Exception{
        ArrayList<AccountType> accountTypes = this.createMockAccountTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/accounttype/" + accountTypes.get(1).getId())
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        String json = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<AccountTypeDto> apiresponse = objectMapper.readValue(json, new TypeReference<Apiresponse<AccountTypeDto>>(){});
        
        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(200);
        assertThat(apiresponse.getData().getId()).isEqualTo(accountTypes.get(1).getId());
        assertThat(apiresponse.getData().getName()).isEqualTo(accountTypes.get(1).getName());
        assertThat(apiresponse.getData().getTerms()).isEqualTo(accountTypes.get(1).getTerms());
        assertThat(apiresponse.getData()).hasOnlyFields("id", "name", "terms");
        
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getAccountType_NotFound() throws Exception{
        this.createMockAccountTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/accounttype/9193e491-8f97-4f2f-85d9-0a9cd3c8c98a")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result).hasStatus(HttpStatus.NOT_FOUND);

        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        ProblemDetail apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<ProblemDetail>(){});

        assertThat(apiresponse.getStatus()).isEqualTo(404);
        assertThat(apiresponse.getTitle()).isEqualTo("Resource not found");
        assertThat(apiresponse.getType()).isEqualTo(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/404"));
        assertThat(apiresponse.getDetail()).isEqualTo("Account type not found.");
    
    }

    // It seems the path variable format is validated too and gets an 400 error if it is no good
    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getAccountType_BadPathVariable() throws Exception{
        this.createMockAccountTypes();

        var result = mockMvcTester
            .get()
            .uri("/api/accounttype/1")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result).hasStatus(HttpStatus.UNPROCESSABLE_CONTENT);

        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        ProblemDetail apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<ProblemDetail>(){});

        assertThat(apiresponse.getStatus()).isEqualTo(422);
        assertThat(apiresponse.getTitle()).isEqualTo("Invalid path variable");
        assertThat(apiresponse.getType()).isEqualTo(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/422"));
        assertThat(apiresponse.getDetail()).isEqualTo("Path variable has no correct format.");
    
    }

    @Test
    public void postAccountType_NoCredentials() throws Exception {
        String mockName = "Example";
        String mockTerms = "Example terms";
        String requestBody = String.format("""
            {
                "name": "%s",
                "terms": "%s"
            }
            """,
            mockName, mockTerms);
        
        var result = mockMvcTester
            .post()
            .uri("/api/accounttype")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(401);
    
        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        ProblemDetail apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<ProblemDetail>(){});

        // This properties are set in AuthorizationExceptionHandler class
        assertThat(apiresponse.getTitle()).isEqualTo("User not authenticated");
        assertThat(apiresponse.getStatus()).isEqualTo(401);
        assertThat(apiresponse.getDetail()).isEqualTo("You need to log to access data.");
        assertThat(apiresponse.getType()).isEqualTo(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/401"));
        
        // Validate if row was not updated
        List<AccountType> searchAccountType = this.accountTypeRepository.findAll();

        assertThat(searchAccountType).isEmpty();
    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void postAccountType_AsClient() throws Exception {
        String mockName = "Example";
        String mockTerms = "Example terms";
        String requestBody = String.format("""
            {
                "name": "%s",
                "terms": "%s"
            }
            """,
            mockName, mockTerms);
        
        var result = mockMvcTester
            .post()
            .uri("/api/accounttype")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(403);
    
        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        ProblemDetail apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<ProblemDetail>(){});

        // This properties are set in AuthorizationExceptionHandler class
        assertThat(apiresponse.getTitle()).isEqualTo("Access denied");
        assertThat(apiresponse.getStatus()).isEqualTo(403);
        assertThat(apiresponse.getDetail()).isEqualTo("User has no permissions to access.");
        assertThat(apiresponse.getType()).isEqualTo(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/403"));
        
        // Validate if row was not updated
        List<AccountType> searchAccountType = this.accountTypeRepository.findAll();

        assertThat(searchAccountType).isEmpty();
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void postAccountType_AsAdmin() throws Exception {
        String mockName = "Example";
        String mockTerms = "Example terms";
        String requestBody = String.format("""
            {
                "name": "%s",
                "terms": "%s"
            }
            """,
            mockName, mockTerms);

        // Validates if there is nothing in database
        assertThat(this.accountTypeRepository.findAll()).isEmpty();
        
        var result = mockMvcTester
            .post()
            .uri("/api/accounttype")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(201);
    
        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<AccountTypeDto> apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<Apiresponse<AccountTypeDto>>(){});
    
        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(201);
        assertThat(apiresponse.getMessage()).isEqualTo("Account type saved.");
        assertThat(apiresponse.getData().getName()).isEqualTo(mockName);
        assertThat(apiresponse.getData().getTerms()).isEqualTo(mockTerms);
        assertThat(apiresponse.getData()).isInstanceOf(AccountTypeDto.class);

        List<AccountType> accountTypesFromRepo = this.accountTypeRepository.findAll();

        assertThat(accountTypesFromRepo.size()).isEqualTo(1);
        assertThat(accountTypesFromRepo.get(0).getName()).isEqualTo(mockName);
        assertThat(accountTypesFromRepo.get(0).getTerms()).isEqualTo(mockTerms);
    
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void postAccountType_AsAdmin_BodyBadFormat() throws Exception {
        String mockName = "";
        String mockTerms = "";
        String requestBody = String.format("""
            {
                "name": "%s",
                "terms": "%s"
            }
            """,
            mockName, mockTerms);
        
        var result = mockMvcTester
            .post()
            .uri("/api/accounttype")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(422);
    
        String jsonresponse = result.getMvcResult().getResponse().getContentAsString();
        CustomProblemDetail apiresponse = this.objectMapper.readValue(jsonresponse, new TypeReference<CustomProblemDetail>(){});

        assertThat(apiresponse.getStatus()).isEqualTo(422);
        assertThat(apiresponse.getErrors()).containsKeys("name", "terms");
        assertThat(apiresponse.getErrors().get("name")).isEqualTo("Name cannot be blank.");
        assertThat(apiresponse.getErrors().get("terms")).isEqualTo("Terms cannot be blank.");
        assertThat(apiresponse.getDetail()).isEqualTo("One or more fields have wrong format.");
        assertThat(apiresponse.getType()).isEqualTo(URI.create("https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/422"));
        assertThat(apiresponse.getTitle()).isEqualTo("Request body not valid");
        assertThat(apiresponse.getTimestamp()).isNotNull();
        assertThat(result.getMvcResult().getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class);
        
    }
}
