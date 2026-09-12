package com.jcooldevelopment.easybank_api.dto.Operation;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class OperationAuthorizationDto {

    // https://stackoverflow.com/questions/74899635/is-there-a-java-bean-validation-to-accept-only-numbers
    @Pattern(regexp = "^[0-9]+(\\.[0-9]+)?$", message = "Pin must be a numeric value")
    @Length(min = 4, max = 6, message = "Pin value must have between 4 and 6 characters long.")
    private String pin;
}
