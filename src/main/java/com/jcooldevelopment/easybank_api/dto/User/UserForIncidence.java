package com.jcooldevelopment.easybank_api.dto.User;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// User DTO class used for IncidenceAdminDto to avoid using sensible info
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class UserForIncidence{

    private UUID id;

    private String name;

    private String surname;

    private String dni;

    private String email;
    
    private String phone;

    private String role;
}
