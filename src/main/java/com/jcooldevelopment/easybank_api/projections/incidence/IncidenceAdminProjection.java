package com.jcooldevelopment.easybank_api.projections.incidence;

import java.time.LocalDateTime;
import java.util.UUID;

public record IncidenceAdminProjection(
    UUID id,
    LocalDateTime created_at,
    String message,
    String status,
    LocalDateTime updated_at,
    String incidenceType,
    Integer incidenceTypeId,
    UUID userId,
    String userName,
    String userSurname,
    String email,
    String phone,
    String dni,
    String userRole
) {

}
