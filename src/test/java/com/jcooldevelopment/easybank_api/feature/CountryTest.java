package com.jcooldevelopment.easybank_api.feature;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

// https://stackoverflow.com/questions/59020569/how-to-assert-that-the-controller-has-been-created-in-spring-boot
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Collection;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.common.CustomProblemDetail;
import com.jcooldevelopment.easybank_api.contracts.entity.Country;
import com.jcooldevelopment.easybank_api.dto.Country.CountryDto;
import com.jcooldevelopment.easybank_api.repository.CountryRepository;

import com.fasterxml.jackson.core.type.TypeReference;

// https://blog.jetbrains.com/idea/2025/04/a-practical-guide-to-testing-spring-controllers-with-mockmvctester/
@SpringBootTest 
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Need this to use non-static beforeAll: https://www.baeldung.com/java-beforeall-afterall-non-static
public class CountryTest {

    @Autowired 
    MockMvcTester mockMvcTester;

    @Autowired 
    CountryRepository countryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String mockCountryName = "España";
    private String mockCountryCode = "ES";

    @BeforeAll // It must be public always
    public void beforeAll() {
        this.countryRepository.deleteAll();
    }

    @BeforeEach // It must be public always
    public void beforeEach() {
        this.countryRepository.deleteAll();
    }

    // Creates country for testing
    private Country createMockCountry() {
        Country newCountry = new Country();
        newCountry.setName(mockCountryName);
        newCountry.setCode(mockCountryCode);
        return this.countryRepository.save(newCountry);
    }

    @Test
    public void getAllCountries_NoCredentials() throws Exception {
        this.createMockCountry();

        var result = mockMvcTester
            .get()
            .uri("/api/country")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result)
            .hasStatus(HttpStatus.UNAUTHORIZED);            
    }

    @Test // Tests must be public always
    @WithMockUser(username = "user", roles = "CLIENT")
    public void getAllCountriesAsClient() throws Exception {
        this.createMockCountry();

        var result = mockMvcTester
            .get()
            .uri("/api/country")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result)
            .hasStatus(HttpStatus.OK)
            .bodyJson()
            .convertTo(Apiresponse.class)
            .satisfies(response -> {
                assertThat(response.getMessage()).isEqualTo("Countries were found.");
                // CASTING is necessary to found values in collection. Remember getData returns a generic type
                // https://assertj.github.io/doc/ extracting to access data key and values
                assertThat((Collection<Country>)response.getData()) 
                    .extracting("name", "code")
                        .hasSize(1)
                        .contains(tuple(mockCountryName, mockCountryCode));                                       
            });
    }

    @Test // Tests must be public always
    @WithMockUser(username = "user", roles = "ADMIN")
    public void getAllCountriesAsAdmin() throws Exception {
        this.createMockCountry();

        var result = mockMvcTester
            .get()
            .uri("/api/country")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result)
            .hasStatus(HttpStatus.OK)
            .bodyJson()
            .convertTo(Apiresponse.class)
            .satisfies(response -> {
                assertThat(response.getMessage()).isEqualTo("Countries were found.");
                // CASTING is necessary to found values in collection. Remember getData returns a generic type
                // https://assertj.github.io/doc/ extracting to access data key and values
                assertThat((Collection<Country>)response.getData()) 
                    .extracting("name", "code")
                        .hasSize(1)
                        .contains(tuple(mockCountryName, mockCountryCode));                                       
            });
    }

    @Test
    public void getCountryById_NoCredentials() throws Exception {
        this.createMockCountry();

        var result = mockMvcTester
            .get()
            .uri("/api/country/1")
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        assertThat(result)
            .hasStatus(HttpStatus.UNAUTHORIZED);    
    }

    @Test 
    @WithMockUser(value = "user", roles = "CLIENT")
    public void getCountryById_AsClient() throws Exception {
        Country newCountry = this.createMockCountry();

        var result = mockMvcTester
            .get()
            .uri("/api/country/" + newCountry.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        String json = result.getMvcResult().getResponse().getContentAsString();

        // Deserialization: Tries to convert into Apiresponse<CountryDto>. If success continues
        // https://stackoverflow.com/questions/64320631/parameterizedtypereference-returning-linkedhashmap-when-called-as-function-with/64322074
        // https://www.baeldung.com/jackson-linkedhashmap-cannot-be-cast
        Apiresponse<CountryDto> apiresponse = objectMapper.readValue(json, new TypeReference<Apiresponse<CountryDto>>(){});

        assertThat(apiresponse.getMessage()).isEqualTo("Country found.");
        assertThat(apiresponse.getData()).hasOnlyFields("id", "name", "code");
        assertThat(apiresponse.getData()).isInstanceOf(CountryDto.class);
        assertThat(apiresponse.getData().getName()).isEqualTo(mockCountryName);
        assertThat(apiresponse.getData().getCode()).isEqualTo(mockCountryCode);
    }

    @Test 
    @WithMockUser(value = "user", roles = "ADMIN")
    public void getCountryById_AsAdmin() throws Exception {
        Country newCountry = this.createMockCountry();

        var result = mockMvcTester
            .get()
            .uri("/api/country/" + newCountry.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .exchange();

        String json = result.getMvcResult().getResponse().getContentAsString();

        // Deserialization: Tries to convert into Apiresponse<CountryDto>. If success continues
        Apiresponse<CountryDto> apiresponse = objectMapper.readValue(json, new TypeReference<Apiresponse<CountryDto>>(){});

        assertThat(apiresponse.getMessage()).isEqualTo("Country found.");
        assertThat(apiresponse.getData()).hasOnlyFields("id", "name", "code");
        assertThat(apiresponse.getData()).isInstanceOf(CountryDto.class);
        assertThat(apiresponse.getData().getName()).isEqualTo(mockCountryName);
        assertThat(apiresponse.getData().getCode()).isEqualTo(mockCountryCode);
    }

    @Test
    public void createCountry_NoCredentials () throws Exception {
        // java thinks %s as a String, that is why in name and code %s is used and not %d for example
        String requestBody = String.format("""
            {
                "name": "%s",
                "code": "%s"
            }        
        """,
        mockCountryName, mockCountryCode);

        var result = mockMvcTester
            .post()
            .uri("/api/country")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        assertThat(result.getResponse().getStatus()).isEqualTo(401); // getStatus only returns int not UNAUTHORIZATED
    }

    @Test
    @WithMockUser(value = "user", roles = "CLIENT")
    public void createCountry_AsClient () throws Exception {
        String requestBody = String.format("""
            {
                "name": "%s",
                "code": "%s"
            }        
        """,
        mockCountryName, mockCountryCode);

        var result = mockMvcTester
            .post()
            .uri("/api/country")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(403);
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void createCountry_AsAdmin () throws Exception {
        String requestBody = String.format("""
            {
                "name": "%s",
                "code": "%s"
            }        
        """,
        mockCountryName, mockCountryCode);

        var result = mockMvcTester
            .post()
            .uri("/api/country")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        String jsonResponse = result.getMvcResult().getResponse().getContentAsString();
        Apiresponse<CountryDto> apiresponse = objectMapper.readValue(jsonResponse, new TypeReference<Apiresponse<CountryDto>>(){});

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(201);
        assertThat(apiresponse.getMessage()).isEqualTo("Country saved.");
        assertThat(apiresponse.getData()).hasOnlyFields("id", "name", "code");
        assertThat(apiresponse.getData()).isInstanceOf(CountryDto.class);
        assertThat(apiresponse.getData().getName()).isEqualTo(mockCountryName);
        assertThat(apiresponse.getData().getCode()).isEqualTo(mockCountryCode);

        // Verify if the created country is in database
        Optional<Country> savedCountry = this.countryRepository.findById(apiresponse.getData().getId());
        assertThat(savedCountry.isPresent());
        savedCountry.ifPresent(country -> {
                assertThat(country.getName()).isEqualTo(apiresponse.getData().getName());
                assertThat(country.getCode()).isEqualTo(apiresponse.getData().getCode());
            }
        );
    }

    @Test
    @WithMockUser(value = "user", roles = "ADMIN")
    public void createCountry_AsAdminBadFormat () throws Exception {
        String requestBody = String.format("""
            {
                "name": "%s",
                "code": "%s"
            }        
        """,
        mockCountryName, "ES-es");

        var result = mockMvcTester
            .post()
            .uri("/api/country")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .exchange();

        String jsonResponse = result.getMvcResult().getResponse().getContentAsString();
        CustomProblemDetail apiresponse = objectMapper.readValue(jsonResponse, new TypeReference<CustomProblemDetail>(){});

        assertThat(result.getMvcResult().getResponse().getStatus()).isEqualTo(422);
        assertThat(apiresponse.getStatus()).isEqualTo(422);
        assertThat(apiresponse.getTitle()).isEqualTo("Request body not valid");
    }
}
