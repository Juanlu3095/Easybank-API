package com.jcooldevelopment.easybank_api.dto.Incidence;

import java.util.UUID;

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
    private UUID user_id;

    @NotNull(message="There is no incidence type selected.")
    private int incidence_type;

    @NotBlank(message="Name cannot be blank.")
    private String message;

    @NotBlank(message="Name cannot be blank.")
    private IncidenceStatus status;
}
