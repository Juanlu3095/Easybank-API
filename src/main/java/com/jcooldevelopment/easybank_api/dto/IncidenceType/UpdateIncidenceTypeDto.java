package com.jcooldevelopment.easybank_api.dto.IncidenceType;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIncidenceTypeDto {

    @NotBlank(message="Name cannot be blank.")
    @Length(max=50, message="Name cannot have more than 50 characters.")
    private String name;
}
