package com.jcooldevelopment.easybank_api.service.Account;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Account.AccountAdminDto;
import com.jcooldevelopment.easybank_api.dto.Account.AccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountAdminDto;
import com.jcooldevelopment.easybank_api.dto.Account.CreateAccountDto;
import com.jcooldevelopment.easybank_api.dto.Account.UpdateAccountAdminDto;

public interface AccountService {

    /**
     * Returns all accounts in database.
     * @param page In a paginated response, the result page to obtain.
     * @param size The number of results in each page.
     * @return Paginated response with account DTOs for admin role.
     */
    PaginatedResponse<AccountAdminDto> getAll(int page, int size);

    /**
     * Returns all accounts in database for given user in Security context holder.
     * @param page In a paginated response, the result page to obtain.
     * @param size The number of results in each page.
     * @return Paginated response with account DTOs for client role.
     */
    PaginatedResponse<AccountDto> getAllByUser(int page, int size);
    
    /**
     * Returns account in database for given id.
     * @param id The account's id to obtain.
     * @return Account DTO for admin role.
     */
    AccountAdminDto getByIdForAdmin(UUID id);

    /**
     * Returns account in database for given id.
     * @param id The account's id to obtain.
     * @return Account DTO for client role.
     */
    AccountDto getById(UUID id); // Client must prove he has credentials for the given id

    /**
     * Creates an account with security context holder user. For client role.
     * @param createAccountDto DTO for creating account by client role.
     * @return Account DTO for client role.
     */
    AccountDto create(CreateAccountDto createAccountDto);

    /**
     * Creates an account. For admin role.
     * @param createAccountAdminDto DTO for creating account by admin role.
     * @return Account DTO for admin role.
     */
    AccountAdminDto createByAdmin(CreateAccountAdminDto createAccountAdminDto);

    /**
     * Updates an account by id. For admin role.
     * @param updateAccountAdminDto DTO for updating account by admin role.
     * @return Account DTO for admin role.
     */
    AccountAdminDto updateByAdmin(UUID id, UpdateAccountAdminDto updateAccountAdminDto);

    /**
     * Deletes an account by id.
     * @param id The account's id to delete.
     */
    void delete(UUID id);
}
