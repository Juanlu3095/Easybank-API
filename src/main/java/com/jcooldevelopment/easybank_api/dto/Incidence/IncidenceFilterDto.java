package com.jcooldevelopment.easybank_api.dto.Incidence;

import com.jcooldevelopment.easybank_api.annotations.EnumValidatorAnnotation;
import com.jcooldevelopment.easybank_api.contracts.enums.IncidenceStatus;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class IncidenceFilterDto {

    // The page to retrieve, the name of the variable is the same for the url
    // Integer + @Min allows to use null and we will not get an error.
    @Min(value = 1, message = "Page minimal value is 1.")
    private Integer page = 1; // 1 is default value if there is no one provided.

    // The size of data in page
    @Min(value = 1, message = "Page minimal size is 1.")
    private Integer size = 5;

    private String message;

    @EnumValidatorAnnotation(enumClass = IncidenceStatus.class, allowNull = true)
    private String status;

    private String incidenceType;
}
