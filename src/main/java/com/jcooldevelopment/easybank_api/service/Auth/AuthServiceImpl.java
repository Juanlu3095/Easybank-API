package com.jcooldevelopment.easybank_api.service.Auth;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;
import com.jcooldevelopment.easybank_api.contracts.enums.UserStatus;
import com.jcooldevelopment.easybank_api.dto.Auth.RegisterDto;
import com.jcooldevelopment.easybank_api.repository.UserRepository;
import com.jcooldevelopment.easybank_api.utils.EncryptUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(HttpServletRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public boolean register(RegisterDto userRegister) {
        var usercode = "";
        boolean usercodeExists = true;

        // Creates usercode
        do {
            usercode = EncryptUtils.generateUsercode();
            usercodeExists = this.userRepository.existsByUsercode(usercode);
        } while (usercodeExists == true);

        User user = User.builder()
            .usercode(usercode)
            .password(this.passwordEncoder.encode(userRegister.getPassword()))
            .name(userRegister.getName())
            .surname(userRegister.getSurname())
            .dni(userRegister.getDni())
            .email(userRegister.getEmail())
            .phone(userRegister.getPhone())
            .role(UserRole.CLIENT)
            .status(UserStatus.NOT_ENABLED)
            .build();
        
        this.userRepository.save(user);
        return true;
    }

}
