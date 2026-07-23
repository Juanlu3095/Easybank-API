package com.jcooldevelopment.easybank_api.dto.Country;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCountryDto {

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @Length(min = 2, max = 2, message = "Code lenght must be 2.")
    @NotBlank(message = "Code cannot be blank.")
    private String code;
}
