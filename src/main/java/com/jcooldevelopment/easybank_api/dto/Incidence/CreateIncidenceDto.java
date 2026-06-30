package com.jcooldevelopment.easybank_api.dto.Incidence;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jcooldevelopment.easybank_api.contracts.enums.IncidenceStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateIncidenceDto {

    @NotNull(message="There is no user selected.")
    private UUID user_id; // This must be in JWT, not here

    @NotNull(message="There is no incidence type selected.")
    private int incidence_type;

    @JsonIgnore // With this, status will only be changed here, inside the class in contructor, it is not an available field, even if it appears in request
    private IncidenceStatus status;

    @NotBlank(message="Name cannot be blank.")
    private String message;

    public CreateIncidenceDto(@NotNull(message = "There is no user selected.") UUID user_id,
            @NotNull(message = "There is no incidence type selected.") int incidence_type,
            @NotBlank(message = "Name cannot be blank.") String message) {
        this.user_id = user_id;
        this.incidence_type = incidence_type;
        this.status = IncidenceStatus.IN_PROCESS;
        this.message = message;
    }
    
}