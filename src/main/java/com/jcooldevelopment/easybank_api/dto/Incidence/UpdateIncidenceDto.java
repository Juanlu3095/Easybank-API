package com.jcooldevelopment.easybank_api.dto.Incidence;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.anotations.EnumValidatorAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.IncidenceStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIncidenceDto {
    
    @NotNull(message="There is no user selected.")
    private UUID user_id; // This must be in JWT, not here

    @NotNull(message="There is no incidence type selected.")
    private int incidence_type;

    @NotBlank(message="Name cannot be blank.")
    private String message;

    @EnumValidatorAnnotation(enumClass = IncidenceStatus.class, message = "Must be an incidence status valid value.")
    private String status; // If user is ADMIN it can be updated, if is client no
}
