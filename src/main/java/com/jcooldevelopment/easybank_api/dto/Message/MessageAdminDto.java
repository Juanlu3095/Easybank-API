package com.jcooldevelopment.easybank_api.dto.Message;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class MessageAdminDto {

    private UUID id;

    @JsonProperty("name") // Allows to establish the key in json of a response
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("message")
    private String message; 

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
