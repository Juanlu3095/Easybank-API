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

import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.contracts.entity.Country;
import com.jcooldevelopment.easybank_api.repository.CountryRepository;

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

    private Country mockCountry;

    @BeforeAll // It must be public always
    public void beforeAll() {
        this.countryRepository.deleteAll();

        this.mockCountry = new Country();
        mockCountry.setName("España");
        mockCountry.setCode("ES-es");
    }

    @BeforeEach // It must be public always
    public void beforeEach() {
        this.countryRepository.deleteAll();
    }

    // Creates country for testing
    private void createMockCountry() {
        this.countryRepository.save(mockCountry);
    }

    @Test // Tests must be public always
    @WithMockUser(username = "user", roles = "CLIENT")
    public void getAllCountries() throws Exception {
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
                assertThat(response.getData()).isIn();
            });
    }
}
