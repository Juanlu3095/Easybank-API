package com.jcooldevelopment.easybank_api.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jcooldevelopment.easybank_api.contracts.common.Apiresponse;
import com.jcooldevelopment.easybank_api.controller.MessageController;
import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.UpdateMessageDto;
import com.jcooldevelopment.easybank_api.repository.MessageRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Need this to use non-static beforeAll: https://www.baeldung.com/java-beforeall-afterall-non-static
public class MessageTest {

    @Value("${spring.datasource.url}")
    private String db;

    @Autowired
    private MessageController messageController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageRepository messageRepository;

    // Auxiliar method to create Messages, so all test are independent
    private MessageDto createMockMessage() throws Exception{
        CreateMessageDto newMessage = new CreateMessageDto();
        newMessage.setEmail("gustavo@gmail.com");
        newMessage.setMessage("Hola");
        newMessage.setName("Gustavo");
        newMessage.setPhone("952211222");
        newMessage.setSurname("Ramírez");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/message")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newMessage))
        )
        .andReturn();

        String response = result.getResponse().getContentAsString();
        Apiresponse<MessageDto> apiresponse = this.objectMapper.readValue(
            response,
            new TypeReference<Apiresponse<MessageDto>>() {}
        );

        return apiresponse.getData();
    }

    @BeforeAll
    public void beforeAll() throws Exception{
        // https://mkyong.com/java/jackson-java-8-date-time-type-java-time-localdate-not-supported-by-default/
        objectMapper.registerModule(new JavaTimeModule()); // Needs a jackson-datatype-jsr310 to make LocalDateTime compatible with Jackson

        this.messageRepository.deleteAll(); // Deletes all rows in message table
    }

    @Test
    public void getDatabaseName(){
        assertEquals("jdbc:postgresql://127.0.0.1:5432/easybank_test", db);
    }

    @Test
    public void controller() {
        assertNotNull(messageController);
    }

    @Test
    public void createMessage() throws Exception{
        CreateMessageDto newMessage = new CreateMessageDto();
        newMessage.setEmail("pepe@gmail.com");
        newMessage.setMessage("Adios");
        newMessage.setName("Pepe");
        newMessage.setPhone("952214578");
        newMessage.setSurname("Pérez");
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/message")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newMessage))
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andReturn();

        // https://elbauldelprogramador.com/como-mapear-json-a-objetos-java-con-jackson-objectmapper/
        // https://samedesilva.medium.com/jackson-objectmapper-in-rest-assured-8511dd62c608
        String response = result.getResponse().getContentAsString();
        // API returns APIRESPONSE<MESSAGEDTO>, must use TypeReference to get MessageDto
        Apiresponse<MessageDto> apiresponse = this.objectMapper.readValue(
            response,
            new TypeReference<Apiresponse<MessageDto>>() {} // https://stackoverflow.com/questions/11664894/jackson-deserialize-using-generic-class
        );

        assertEquals(result.getResponse().getHeader("Location"), "/api/message/" + apiresponse.getData().getId());
    }

    @Test
    public void createMessageBadEmailFormat() throws Exception {
        CreateMessageDto newMessage = new CreateMessageDto();
        newMessage.setEmail("pepe.gmail.com");
        newMessage.setMessage("Adios");
        newMessage.setName("Pepe");
        newMessage.setPhone("952214578");
        newMessage.setSurname("Pérez");
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/message")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newMessage))
        )
        .andExpect(MockMvcResultMatchers.status().isUnprocessableContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN") // https://codefarm0.medium.com/deep-dive-into-rest-api-integration-testing-in-spring-boot-d7ac3051cc07
    public void getAllMessages() throws Exception {
        this.messageRepository.deleteAll();
        MessageDto newMessage = this.createMockMessage();
        UUID id = newMessage.getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/message")
            .contentType(MediaType.APPLICATION_JSON)
        )

        // $ is: message: ..., data: {data: ...} because is a paginatedResponse
        // https://anotherdayanotherbug.wordpress.com/2015/03/16/tests-de-integracion-para-un-servicio-rest-con-spring/
        // https://stackoverflow.com/questions/42725199/how-to-use-mockmvcresultmatchers-jsonpath
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.data", Matchers.hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.data[0].id", Matchers.equalTo(id.toString())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageSize", Matchers.is(1))); // Also: https://stackoverflow.com/questions/13745332/how-to-count-members-with-jsonpath
    }

    @Test
    public void getAllMessages_NoCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void getMessage() throws Exception {
        MessageDto newMessage = this.createMockMessage();
        UUID id = newMessage.getId();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/message/" + id)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        String response = result.getResponse().getContentAsString();
        Apiresponse<MessageDto> apiresponse = this.objectMapper.readValue(
            response,
            new TypeReference<Apiresponse<MessageDto>>() {}
        );

        assertEquals(newMessage.getEmail(), apiresponse.getData().getEmail());
        assertEquals(newMessage.getId(), apiresponse.getData().getId());
        assertEquals(newMessage.getMessage(), apiresponse.getData().getMessage());
        assertEquals(newMessage.getName(), apiresponse.getData().getName());
        assertEquals(newMessage.getPhone(), apiresponse.getData().getPhone());
        assertEquals(newMessage.getSurname(), apiresponse.getData().getSurname());
        assertNotNull(apiresponse.getData().getCreatedAt());
    }

    @Test
    public void getMessage_NoCredentials() throws Exception {
        UUID id = this.createMockMessage().getId();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/message/" + id)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andReturn();

        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void getMessage_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/1ba94857-7215-46f9-b9e8-bd81e3d98e31")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void updateMessage_NoCredentials() throws Exception {
        MessageDto newMessage = this.createMockMessage();
        UpdateMessageDto updatedMessage = new UpdateMessageDto();
        updatedMessage.setEmail("pepe@gmail.com");
        updatedMessage.setMessage("Adios");
        updatedMessage.setName("Pepe");
        updatedMessage.setPhone("952214578");
        updatedMessage.setSurname("Pérez");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/" + newMessage.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedMessage))
        )
        .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void updateMessage_NotFound() throws Exception {
        UpdateMessageDto updatedMessage = new UpdateMessageDto();
        updatedMessage.setEmail("pepe.gmail.com");
        updatedMessage.setMessage("Adios");
        updatedMessage.setName("Pepe");
        updatedMessage.setPhone("952214578");
        updatedMessage.setSurname("Pérez");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/1ba94857-7215-46f9-b9e8-bd81e3d98e31")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedMessage))
        )
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void updateMessage_BadEmailFormat() throws Exception {
        MessageDto newMessage = this.createMockMessage();
        UpdateMessageDto updatedMessage = new UpdateMessageDto();
        updatedMessage.setEmail("pepe.gmail.com");
        updatedMessage.setMessage("Adios");
        updatedMessage.setName("Pepe");
        updatedMessage.setPhone("952214578");
        updatedMessage.setSurname("Pérez");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/message/" + newMessage.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedMessage))
        )
        .andExpect(MockMvcResultMatchers.status().isUnprocessableContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void updateMessage() throws Exception {
        MessageDto newMessage = this.createMockMessage();
        UpdateMessageDto updatedMessage = new UpdateMessageDto();
        updatedMessage.setEmail("pepito@gmail.com");
        updatedMessage.setMessage("Adios");
        updatedMessage.setName("Pepito");
        updatedMessage.setPhone("952212456");
        updatedMessage.setSurname("Pérez Montero");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/message/" + newMessage.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedMessage))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.email", Matchers.equalTo(updatedMessage.getEmail())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.message", Matchers.equalTo(updatedMessage.getMessage())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name", Matchers.equalTo(updatedMessage.getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.phone", Matchers.equalTo(updatedMessage.getPhone())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.surname", Matchers.equalTo(updatedMessage.getSurname())));
    }
    
    @Test
    public void deleteMessage_NoCredentials() throws Exception{
        UUID id = this.createMockMessage().getId();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/message/" + id)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andReturn();
    
        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteMessage_NotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/message/1ba94857-7215-46f9-b9e8-bd81e3d98e31")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void deleteMessage() throws Exception {
        UUID id = this.createMockMessage().getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/message/" + id)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk());
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/" + id)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
