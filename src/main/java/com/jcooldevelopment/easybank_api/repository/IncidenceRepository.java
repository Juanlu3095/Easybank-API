package com.jcooldevelopment.easybank_api.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jcooldevelopment.easybank_api.contracts.entity.Incidence;
import com.jcooldevelopment.easybank_api.projections.incidence.IncidenceProjection;

public interface IncidenceRepository extends JpaRepository<Incidence, UUID>{

    @Query(
        value = """
        SELECT 
        incidences.id as id,
        incidences.created_at as created_at,
        incidences.message as message,
        incidences.status as status,
        incidences.updated_at as updated_at,
        incidenceType.name as incidenceType,
        incidenceType.id as incidenceTypeId
        FROM incidences
        INNER JOIN incidence_type
            ON incidence.incidence_type = incidenceType.id
        WHERE incidences.user_id =
        (
            SELECT users.id
            FROM users
            WHERE users.usercode = ?1
        )
        """,
        nativeQuery = true
    )
    Page<IncidenceProjection> findByUsercode(String usercode, Pageable pageable);

    @Query(
        value = """
        SELECT count(*)
        FROM incidences
        WHERE incidences.id = ?1 
        AND
        incidences.user_id =
        (
            SELECT users.id
            FROM users
            WHERE users.usercode = ?2
        )
        """,
        nativeQuery = true
    )
    int incidenceBelongsToUser(UUID incidenceId, String usercode);

    // https://es.stackoverflow.com/questions/289548/obtener-id-de-una-nueva-inserci%C3%B3n-en-la-base-de-datos
    /**
     * Creates incidence as client role. Used instead of JPA's save method for optimization.
     * @param user_id
     * @param incidenceTypeId
     * @param message
     * @param status
     * @return The created incidence's uuid.
     */
    @Query(value = """
        INSERT INTO incidences
            (user_id, incidence_type, message, status)
        VALUES
            (?1, ?2, ?3, ?4)
        RETURNING id
    """,
    nativeQuery = true
    )
    UUID saveIncidenceAndReturnId(UUID user_id, int incidenceTypeId, String message, String status);
}
