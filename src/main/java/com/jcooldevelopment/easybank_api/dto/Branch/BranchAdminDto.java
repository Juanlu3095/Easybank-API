package com.jcooldevelopment.easybank_api.dto.Branch;

import com.jcooldevelopment.easybank_api.contracts.entity.Country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchAdminDto {

    private Long id;

    private String name;

    private String ibanCode; // The IBAN part which identifies the branch

    private String bicCode; // The BIC/SWIFT part which identifies the branch

    private String localizationCode;

    private String address;

    private String city;

    private Country country;
}
