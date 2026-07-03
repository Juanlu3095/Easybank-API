package com.jcooldevelopment.easybank_api.dto.User;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id;

    @NotBlank(message = "Name cannot be blank.")
    @Length(min = 1, max = 50)
    private String name;
    
    @NotBlank(message = "Surname cannot be blank.")
    @Length(min = 1, max = 100)
    private String surname;

    @NotBlank(message = "DNI cannot be blank.")
    @Length(min = 10, max = 10)
    private String dni;

    @Email(message = "Email format is not valid.")
    @Length(min = 1, max = 100)
    private String email;

    @NotBlank(message = "Phone cannot be blank.")
    @Length(min = 1, max = 45)
    private String phone;

    @NotBlank(message = "Usercode cannot be blank.")
    private String usercode;

    private UserRole role;
}
