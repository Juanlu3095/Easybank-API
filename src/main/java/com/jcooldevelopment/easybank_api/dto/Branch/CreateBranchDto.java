package com.jcooldevelopment.easybank_api.dto.Branch;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBranchDto {

    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @Length(min = 4, max = 4, message = "IBAN branch's length must be 4.")
    private String ibanCode; // The IBAN part which identifies the branch

    @Length(min = 3, max = 3, message = "BIC/SWIFT branch's length must be 3.")
    private String bicCode; // The BIC/SWIFT part which identifies the branch

    @NotBlank(message = "Address cannot be blank.")
    private String address;

    @NotBlank(message = "City cannot be blank.")
    private String city;

    @NotNull(message = "Country cannot be null.")
    private int countryId;
}
