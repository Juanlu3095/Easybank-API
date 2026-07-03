package com.jcooldevelopment.easybank_api.service.User;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.dto.User.CreateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UpdateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UserDto;

public interface UserService {

    public PaginatedResponse<UserDto> getAll(int page, int size);

    public UserDto getById(UUID id);

    public UserDto create(CreateUserDto createUserDto);

    public UserDto update(UUID id, UpdateUserDto updateUserDto);

    public boolean delete(UUID id);
}
