package com.jcooldevelopment.easybank_api.service.Operation;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.Operation.CreateOperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationAdminDto;
import com.jcooldevelopment.easybank_api.dto.Operation.OperationDto;
import com.jcooldevelopment.easybank_api.dto.Operation.UpdateOperationDto;

public interface OperationService {

    /**
     * Obtains all operations in database.
     * @param page In a paginated response, the result page to obtain.
     * @param size The number of results in each page.
     * @return Paginated response with operation DTOs for admin role.
     */
    PaginatedResponse<OperationAdminDto> getAll(int page, int size);

    /**
     * Obtains all operations for the given account in database.
     * @param page In a paginated response, the result page to obtain.
     * @param size The number of results in each page.
     * @return Paginated response with operation DTOs.
     */
    PaginatedResponse<OperationAdminDto> getByAccount(UUID accountId, int page, int size);

    /**
     * Obtains all operations for the given user by JWT in database.
     * @param page In a paginated response, the result page to obtain.
     * @param size The number of results in each page.
     * @return Paginated response with operation DTOs for client role.
     */
    PaginatedResponse<OperationDto> getByAuth(int page, int size);

    /**
     * Obtains all operations for the given user by JWT in database.
     * @param userId The user's id.
     * @param page In a paginated response, the result page to obtain.
     * @param size The number of results in each page.
     * @return Paginated response with operation DTOs for admin role.
     */
    PaginatedResponse<OperationAdminDto> getByUser(UUID userId, int page, int size);

    /**
     * Obtains an operation for the given operation's id.
     * Checks if that operation is assigned to JWT's user before returning the info.
     * @param operationId The operation's id.
     * @return Operation DTO.
     */
    OperationDto getById(UUID operationId);

    /**
     * Creates a new operation in database.
     * @param createOperationDto The DTO to create an operation, usually from a form.
     * @return The operation created.
     */
    OperationAdminDto create(CreateOperationDto createOperationDto);

    /**
     * Updates an operation by id.
     * @param operationId The operation to update's id.
     * @param updateOperationDto The DTO to update an operation, usually from a form.
     * @return The operation created.
     */
    OperationAdminDto update(UUID operationId, UpdateOperationDto updateOperationDto);

    /**
     * Deletes an operation by id in database.
     * @param operationId The id of the operation to delete.
     */
    void delete(UUID operationId);
}
