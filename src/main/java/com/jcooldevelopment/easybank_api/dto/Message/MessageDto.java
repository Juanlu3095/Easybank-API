package com.jcooldevelopment.easybank_api.dto.Message;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    
    private UUID id;

    @NotBlank(message="Name cannot be blank.")
    @Length(max=50, message="Name cannot have more than 50 characters.")
    @JsonProperty("name") // Allows to establish the key in json of a response
    private String name;

    @NotBlank(message="Surname cannot be blank.")
    @Length(max=100, message="Surname cannot have more than 100 characters.")
    @JsonProperty("surname")
    private String surname;

    @Email(regexp="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message="Email format is not correct.")
    @Length(max=50, message="Email cannot have more than 50 characters.")
    @JsonProperty("email")
    private String email;

    @Length(max=50, message="Phone cannot have more than 50 characters.")
    @NotBlank(message="Phone cannot be blank.")
    @JsonProperty("phone")
    private String phone;

    @NotBlank(message="Message cannot be blank.")
    @JsonProperty("message")
    private String message;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
