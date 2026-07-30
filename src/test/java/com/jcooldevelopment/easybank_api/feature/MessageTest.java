package com.jcooldevelopment.easybank_api.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcooldevelopment.easybank_api.contracts.entity.Message;
import com.jcooldevelopment.easybank_api.controller.MessageController;
import com.jcooldevelopment.easybank_api.dto.Message.CreateMessageDto;
import com.jcooldevelopment.easybank_api.dto.Message.MessageDto;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MessageTest {

    @Value("${spring.datasource.url}")
    private String db;

    @Autowired
    private MessageController messageController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID idMessage;

    private Message message = new Message();

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

        message.setEmail(newMessage.getEmail());
        message.setId(this.idMessage);
        message.setMessage(newMessage.getMessage());
        message.setName(newMessage.getName());
        message.setPhone(newMessage.getPhone());
        message.setSurname(newMessage.getSurname());

        String response = result.getResponse().getContentAsString();
        MessageDto savedMessage = this.objectMapper.readValue(response, MessageDto.class);
        this.idMessage = savedMessage.getId();

        assertEquals(result.getResponse().getHeader("Location"), "/api/message/" + this.idMessage);
    }

    @Test
    public void createMessageBadEmailFormat() throws Exception {
        
    }

    @Test
    public void getAllMessages() throws Exception {

    }

    @Test
    public void getAllMessages_NoCredentials() throws Exception {

    }

    @Test
    public void getMessage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/message/" + this.idMessage)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andReturn();

        String response = result.getResponse().getContentAsString();
        MessageDto obtainedMessage = this.objectMapper.readValue(response, MessageDto.class);

        assertEquals(this.message.getEmail(), obtainedMessage.getEmail());
        assertEquals(this.message.getId(), obtainedMessage.getId());
        assertEquals(this.message.getMessage(), obtainedMessage.getMessage());
        assertEquals(this.message.getName(), obtainedMessage.getName());
        assertEquals(this.message.getPhone(), obtainedMessage.getPhone());
        assertEquals(this.message.getSurname(), obtainedMessage.getSurname());
        assertNotNull(obtainedMessage.getCreatedAt());
    }

    @Test
    public void getMessage_NoCredentials() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/message/" + this.idMessage)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andReturn();

        assertEquals(403, result.getResponse().getStatus());
    }

    @Test
    public void getMessage_NotFound() throws Exception {
        
    }

    @Test
    public void updateMessage() throws Exception {
        
    }

    @Test
    public void updateMessage_NoCredentials() throws Exception {
        
    }

    @Test
    public void updateMessage_NotFound() throws Exception {
        
    }

    @Test
    public void updateMessage_BadEmailFormat() throws Exception {
        
    }

    @Test
    public void deleteMessage() throws Exception {
        
    }

    @Test
    public void deleteMessage_NoCredentials() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/message/" + this.idMessage)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andReturn();

        assertEquals(result.getResponse().getStatus(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void deleteMessage_NotFound() throws Exception {
        
    }
}
