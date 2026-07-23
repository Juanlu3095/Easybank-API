package com.jcooldevelopment.easybank_api.dto.Country;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {

    @NotNull(message = "Id cannot be null.")
    private int id;

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @NotBlank(message = "Code cannot be blank.")
    private String code;
}
