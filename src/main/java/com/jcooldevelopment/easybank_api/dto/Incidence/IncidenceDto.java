package com.jcooldevelopment.easybank_api.dto.Incidence;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.IncidenceStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidenceDto {

    private UUID id;

    @NotNull(message="There is no user selected.")
    @JsonIgnoreProperties({ "password", "pin"}) // Makes columns "password" and "pin" not visible
    private User user_id;

    @NotNull(message="There is no incidence type selected.")
    private IncidenceType incidence_type;

    @NotBlank(message="Message cannot be blank.")
    private String message;

    @NotBlank(message="Status cannot be blank.")
    private IncidenceStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
