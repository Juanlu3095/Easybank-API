package com.jcooldevelopment.easybank_api.service.User;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.User.CreatePinDto;
import com.jcooldevelopment.easybank_api.dto.User.CreateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UpdateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UserAdminDto;
import com.jcooldevelopment.easybank_api.dto.User.UserDto;
import com.jcooldevelopment.easybank_api.dto.User.UserFilterDto;
import com.jcooldevelopment.easybank_api.exception.UserAlreadyEnabledException;

public interface UserService {

    /**
     * Finds all Users with filters. For admin role onñy.
     * @param filterDto It containts page, size and data about users to filter.
     * @return User DTO for admin
     */
    public PaginatedResponse<UserAdminDto> getAll(UserFilterDto filterDto);

    /**
     * It obtains authenticated user data using JWT. For client role only
     * @return User data as DTO.
     */
    public UserDto getByAuth();

    public UserAdminDto getById(UUID id);

    /**
     * Creates user by admin. It does not send confirmation email.
     * @param createUserDto The necessary data to create a new user.
     * @return UserDto.
     */
    public UserAdminDto create(CreateUserDto createUserDto);

    public UserAdminDto update(UUID id, UpdateUserDto updateUserDto);

    public void delete(UUID id);

    /**
     * Allows to send again email to activate account.
     * @param id The id of user to resend email to activate account.
     * @throws UserAlreadyEnabledException if user is already enabled.
     * @return true if email was sent, and false if not.
     */
    public boolean resendEmail(UUID id);

    /**
     * Sets authenticated user's pin. For client role.
     * @param createPinDto The pin to set.
     * @throws ClientPinAlreadySetException If user's pin is already set.
     */
    public void setPin(CreatePinDto createPinDto);
}
