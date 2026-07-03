package com.jcooldevelopment.easybank_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.jcooldevelopment.easybank_api.contracts.entity.User;


public interface UserRepository extends JpaRepository<User, UUID>{

    // https://stackoverflow.com/questions/29202277/update-single-field-using-spring-data-jpa
    @Modifying // This is used only with @Query
    @Query("update User user SET user.pin = ?2 WHERE user.id = ?1") // Numbers are parameters order in method
    boolean updatePin(UUID id, String pin);

    @Modifying // This is used only with @Query
    @Query("update User user SET user.password = ?2 WHERE user.id = ?1")
    boolean updatePassword(UUID id, String password);

    boolean existsByEmail(String email);
}
