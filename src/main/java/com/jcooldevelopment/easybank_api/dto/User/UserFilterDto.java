package com.jcooldevelopment.easybank_api.dto.User;

import com.jcooldevelopment.easybank_api.annotations.DniValidatorAnnotation;
import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;
import com.jcooldevelopment.easybank_api.contracts.enums.UserStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class UserFilterDto {

    // Integer + @Min allows to use null and we will not get an error.
    @Min(value = 1, message = "Page minimal value is 1.")
    private Integer page = 1; // 1 is default value if there is no one provided.

    @Min(value = 1, message = "Page minimal size is 1.")
    private Integer size = 5;

    private String name;

    private String surname;

    @DniValidatorAnnotation (allowNull = true, message = "DNI format is not valid.")
    private String dni;

    @Email(message = "Email format is not valid.")
    private String email;

    private String phone;

    @EnumValidatorAnnotation(enumClass = UserRole.class, allowNull = true)
    private String role;

    @EnumValidatorAnnotation(enumClass = UserStatus.class, allowNull = true)
    private String status;
}
