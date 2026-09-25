package com.jcooldevelopment.easybank_api.dto.Incidence;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.IncidenceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class IncidenceAdminDto {

    private UUID id;

    @JsonIgnoreProperties({ "password", "pin"}) // Makes columns "password" and "pin" not visible
    private User user;

    private IncidenceType incidence_type;

    private String message;

    private IncidenceStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
