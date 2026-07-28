package com.jcooldevelopment.easybank_api.dto.Branch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;
}
