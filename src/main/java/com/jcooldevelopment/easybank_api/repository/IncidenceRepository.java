package com.jcooldevelopment.easybank_api.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jcooldevelopment.easybank_api.contracts.entity.Incidence;
import com.jcooldevelopment.easybank_api.projections.incidence.IncidenceAdminProjection;
import com.jcooldevelopment.easybank_api.projections.incidence.IncidenceProjection;

public interface IncidenceRepository extends JpaRepository<Incidence, UUID>{

    /**
     * Get all incidences which belongs to usercode's user.
     * @param usercode The usercode that identifies user.
     * @param pageable Pagination with no sort.
     * @return IncidenceProjection for client role.
     */
    @Query(
        value = """
        SELECT incidences.id as id,
        incidences.created_at as created_at,
        incidences.message as message,
        incidences.status as status,
        incidences.updated_at as updated_at,
        incidence_type.name as incidenceType,
        incidence_type.id as incidenceTypeId
        FROM incidences
        LEFT JOIN incidence_type
            ON incidences.incidence_type = incidence_type.id
        INNER JOIN users
            ON incidences.user_id = users.id
        WHERE incidences.user_id =
        (
            SELECT users.id
            FROM users
            WHERE users.usercode = ?1
        )
            AND (?2 IS NULL
                OR ?2 = ''
                OR incidences.message = ?2)
            AND (?3 IS NULL
                OR ?3 = ''
                OR incidences.status = ?3)
            AND (?4 IS NULL
                OR ?4 = ''
                OR incidence_type.name = ?4)
        ORDER BY incidences.created_at DESC
        """,
        nativeQuery = true,
        countQuery = """
            SELECT COUNT(*)
            FROM incidences
            INNER JOIN incidence_type
                ON incidences.incidence_type = incidence_type.id
            WHERE incidences.user_id =
            (
                SELECT users.id
                FROM users
                WHERE users.usercode = ?1
            )
            AND (?2 IS NULL
                OR ?2 = ''
                OR incidences.message = ?2)
            AND (?3 IS NULL
                OR ?3 = ''
                OR incidences.status = ?3)
            AND (?4 IS NULL
                OR ?4 = ''
                OR incidence_type.name = ?4)    
        """
    )
    Page<IncidenceProjection> findByUsercode(
        String usercode,
        String message,
        String status,
        String incidenceType,
        Pageable pageable
    );

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
    
    /**
     * Gets incidences pageable for admin with no sensible info.
     * @param pageable Pagination with no sort.
     * @return IncidenceAdminProjection, obviously for admin role.
     */
    @Query(value = """
    SELECT
    incidences.id as id,
    incidences.created_At as created_at,
    incidences.message as message,
    incidences.status as status,
    incidences.updated_at as updated_at,
    incidence_type.name as incidenceType,
    incidence_type.id as incidenceTypeId,
    users.id as userId,
    users.name as userName,
    users.surname as userSurname,
    users.email as email,
    users.phone as phone,
    users.dni as dni,
    users.role as userRole
    FROM incidences
    INNER JOIN incidence_type
        ON incidences.incidence_type = incidence_type.id
    INNER JOIN users
        ON incidences.user_id = users.id
    WHERE (?1 IS NULL
            OR ?1 = ''
            OR incidences.message LIKE CONCAT('%', ?1, '%'))
        AND (?2 IS NULL
                OR ?2 = ''
                OR incidences.status = ?2)
        AND (?3 IS NULL
                OR ?3 = ''
                OR users.name LIKE CONCAT('%', ?3, '%'))
        AND (?4 IS NULL
                OR ?4 = ''
                OR users.surname LIKE CONCAT('%', ?4, '%'))
        AND (?5 IS NULL
                OR ?5 = ''
                OR users.email LIKE CONCAT('%', ?5, '%'))
        AND (?6 IS NULL
                OR ?6 = ''
                OR users.dni LIKE CONCAT('%', ?6, '%'))
        AND (?7 IS NULL
                OR ?7 = ''
                OR incidence_type.name = ?7)  
    ORDER BY incidences.created_at DESC
    """,
    countQuery = """
    SELECT COUNT(*)
    FROM incidences
    INNER JOIN incidence_type
        ON incidences.incidence_type = incidence_type.id
    INNER JOIN users
        ON incidences.user_id = users.id
    WHERE (?1 IS NULL
            OR ?1 = ''
            OR incidences.message LIKE CONCAT('%', ?1, '%'))
        AND (?2 IS NULL
                OR ?2 = ''
                OR incidences.status = ?2)
        AND (?3 IS NULL
                OR ?3 = ''
                OR users.name LIKE CONCAT('%', ?3, '%'))
        AND (?4 IS NULL
                OR ?4 = ''
                OR users.surname LIKE CONCAT('%', ?4, '%'))
        AND (?5 IS NULL
                OR ?5 = ''
                OR users.email LIKE CONCAT('%', ?5, '%'))
        AND (?6 IS NULL
                OR ?6 = ''
                OR users.dni LIKE CONCAT('%', ?6, '%'))
        AND (?7 IS NULL
                OR ?7 = ''
                OR incidence_type.name = ?7)    
    """,
    nativeQuery = true)
    Page<IncidenceAdminProjection> findAllAsProjection(
        String message,
        String status,
        String userName,
        String userSurname,
        String userEmail,
        String userDni,
        String incidenceType,
        Pageable pageable
    );
}
