package com.jcooldevelopment.easybank_api.service.Auth;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;
import com.jcooldevelopment.easybank_api.contracts.enums.UserStatus;
import com.jcooldevelopment.easybank_api.dto.Auth.LoginDto;
import com.jcooldevelopment.easybank_api.dto.Auth.RegisterDto;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.repository.UserRepository;
import com.jcooldevelopment.easybank_api.service.Jwt.JwtService;
import com.jcooldevelopment.easybank_api.utils.EncryptUtils;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(LoginDto request) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsercode(), request.getPassword()));
        UserDetails user = this.userRepository.findByUsercode(request.getUsercode())
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        
        return this.jwtService.getToken(user);
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
