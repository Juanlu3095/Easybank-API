package com.jcooldevelopment.easybank_api.dto.Message;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMessageDto {
    @NotBlank(message="Name cannot be blank.")
    @Length(max=50, message="Name cannot have more than 50 characters.")
    private String name;

    @NotBlank(message="Surname cannot be blank.")
    @Length(max=100, message="Surname cannot have more than 100 characters.")
    private String surname;

    @Email(regexp="^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message="Email format is not correct.")
    @Length(max=50, message="Email cannot have more than 50 characters.")
    private String email;

    @Length(max=50, message="Phone cannot have more than 50 characters.")
    @NotBlank(message="Phone cannot be blank.")
    private String phone;

    @NotBlank(message="Message cannot be blank.")
    private String message;
}
