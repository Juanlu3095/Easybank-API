package com.jcooldevelopment.easybank_api.dto.Branch;

public record BranchDto(
    Long id,
    String name,
    String address,
    String city,
    String country
) {

}
