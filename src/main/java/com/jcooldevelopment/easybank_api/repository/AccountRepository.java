package com.jcooldevelopment.easybank_api.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jcooldevelopment.easybank_api.contracts.entity.Account;
import com.jcooldevelopment.easybank_api.contracts.entity.User;

public interface AccountRepository extends JpaRepository<Account, UUID>{

    Page<Account> findByUsers(Pageable pageable, User user);

    Optional<Account> findByIban(String iban);

    @Query(
        value = """
        SELECT COUNT(*)
        FROM user_account
        WHERE user_account.account_id = ?1
        AND user_account.user_id =
        (SELECT users.id FROM users WHERE users.usercode = ?2)
        """,
        nativeQuery = true
    )
    int accountBelongsToUser(UUID accounId, String usercode);
}
