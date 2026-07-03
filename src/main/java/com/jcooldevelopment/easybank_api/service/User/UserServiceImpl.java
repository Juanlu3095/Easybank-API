package com.jcooldevelopment.easybank_api.service.User;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.common.PaginatedResponse;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;
import com.jcooldevelopment.easybank_api.dto.User.CreateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UpdateUserDto;
import com.jcooldevelopment.easybank_api.dto.User.UserDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.mapper.UserMapper;
import com.jcooldevelopment.easybank_api.repository.UserRepository;
import com.jcooldevelopment.easybank_api.utils.DataFormater;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl (UserRepository repository, UserMapper mapper) {
        this.userRepository = repository;
        this.userMapper = mapper;
    }

    @Override
    public PaginatedResponse<UserDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(User::getSurname).descending());
        Page<User> users = this.userRepository.findAll(pageable);
        Page<UserDto> usersDto = new PageImpl<UserDto>(users.getContent()
            .stream()
            .map(userDto -> userMapper.EntityToDto(userDto))
            .toList()
        );

        PaginatedResponse<UserDto> paginatedResponse = DataFormater.paginate(usersDto);
        return paginatedResponse;
    }

    @Override
    public UserDto getById(UUID id) {
        User user = this.userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Incidence not found."));

        return userMapper.EntityToDto(user);
    }

    @Override
    public UserDto create(CreateUserDto createUserDto) {
        User userToSave = userMapper.CreateUserDtoToEntity(createUserDto);
        userToSave.setUsercode("12343");
        userToSave.setCreatedAt(LocalDateTime.now());
        User savedUser = this.userRepository.save(userToSave);
        return userMapper.EntityToDto(savedUser);
    }

    @Override
    public UserDto update(UUID id, UpdateUserDto updateUserDto) {
        User userToUpdate = this.userRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("User not found."));

        userToUpdate.setName(updateUserDto.getName());
        userToUpdate.setSurname(updateUserDto.getSurname());
        userToUpdate.setDni(updateUserDto.getDni());
        userToUpdate.setEmail(updateUserDto.getEmail());
        userToUpdate.setPhone(updateUserDto.getPhone());
        userToUpdate.setRole(UserRole.valueOf(updateUserDto.getRole()));
        userToUpdate.setUsercode("1234"); // this should be updated with other class method since the app creates it
        userToUpdate.setPassword(updateUserDto.getPassword());
        userToUpdate.setPin(updateUserDto.getPin());
        User savedUser = this.userRepository.save(userToUpdate);

        return userMapper.EntityToDto(savedUser);
    }

    @Override
    public boolean delete(UUID id) {
        User user = this.userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Incidence not found."));
        
        userRepository.delete(user);
        return true;
    }

}
