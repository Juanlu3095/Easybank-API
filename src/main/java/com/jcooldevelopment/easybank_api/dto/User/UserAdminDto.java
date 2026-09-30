package com.jcooldevelopment.easybank_api.dto.User;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class UserAdminDto {

    private UUID id;

    private String name;
    
    private String surname;

    private String dni;

    private String email;

    private String phone;

    private String usercode;

    private UserRole role;
}
