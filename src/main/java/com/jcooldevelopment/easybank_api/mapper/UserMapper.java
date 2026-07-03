package com.jcooldevelopment.easybank_api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.dto.User.CreateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UpdateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UserDto;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper mapper) {
        this.modelMapper = mapper;
    }

    public User CreateUserDtoToEntity(CreateUserDto createUserDto) {
       return modelMapper.map(createUserDto, User.class);
    }

    public User UpdateUserDtoToEntity(UpdateUserDto updateUserDto) {
       return modelMapper.map(updateUserDto, User.class);
    }

    public UserDto EntityToDto(User User) {
        return modelMapper.map(User, UserDto.class);
    }
}
