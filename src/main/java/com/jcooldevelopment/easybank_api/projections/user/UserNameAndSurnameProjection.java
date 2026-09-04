package com.jcooldevelopment.easybank_api.projections.user;

// Used for operation entity to write orderer name and surname
public record UserNameAndSurnameProjection(
    String name,
    String surname
) {
}
