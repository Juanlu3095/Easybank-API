package com.jcooldevelopment.easybank_api.dto.Message;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class MessageFilterDto {

    // Integer + @Min allows to use null and we will not get an error.
    @Min(value = 1, message = "Page minimal value is 1.")
    private Integer page = 1; // 1 is default value if there is no one provided.

    @Min(value = 1, message = "Page minimal size is 1.")
    private Integer size = 5;
    
    private String name;

    private String surname;

    @Email(message = "Email format is not valid.")
    private String email;

    private String phone;

    private String message;
}
