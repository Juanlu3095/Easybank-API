package com.jcooldevelopment.easybank_api.service.Auth;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.ResetPasswordToken;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.UserRole;
import com.jcooldevelopment.easybank_api.contracts.enums.UserStatus;
import com.jcooldevelopment.easybank_api.dto.Auth.ChangePasswordDto;
import com.jcooldevelopment.easybank_api.dto.Auth.LoginDto;
import com.jcooldevelopment.easybank_api.dto.Auth.RegisterDto;
import com.jcooldevelopment.easybank_api.dto.Auth.ResetPasswordDto;
import com.jcooldevelopment.easybank_api.exception.DniAlreadyExistsException;
import com.jcooldevelopment.easybank_api.exception.EmailAlreadyExistsException;
import com.jcooldevelopment.easybank_api.exception.IncorrectPasswordException;
import com.jcooldevelopment.easybank_api.exception.ResetPasswordExpiredException;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.repository.UserRepository;
import com.jcooldevelopment.easybank_api.service.ActivationCode.ActivationCodeService;
import com.jcooldevelopment.easybank_api.service.Email.EmailService;
import com.jcooldevelopment.easybank_api.service.Jwt.JwtService;
import com.jcooldevelopment.easybank_api.service.ResetPasswordToken.ResetPasswordTokenService;
import com.jcooldevelopment.easybank_api.utils.EncryptUtils;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ActivationCodeService activationCodeService;
    private final JwtService jwtService;
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
        EmailService emailService,
        ActivationCodeService activationCodeService,
        JwtService jwtService,
        ResetPasswordTokenService resetPasswordTokenService,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.activationCodeService = activationCodeService;
        this.jwtService = jwtService;
        this.resetPasswordTokenService = resetPasswordTokenService;
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
        userRegister.setDni(userRegister.getDni().toUpperCase());
        var usercode = "";
        boolean usercodeExists = true;

        int countByEmail = this.userRepository.countByEmail(userRegister.getEmail());
        int countByDni = this.userRepository.countByDni(userRegister.getDni());

        if (countByEmail > 0) throw new EmailAlreadyExistsException("This email already exists.");
        if (countByDni > 0) throw new DniAlreadyExistsException("This DNI already exists.");

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
        
        User savedUser = this.userRepository.save(user);
        String activationCode = this.activationCodeService.createCode(savedUser.getId());
        this.emailService.sendMailToEnableUser(usercode, activationCode, user.getEmail());
        return true;
    }

    @Override
    public boolean changePassword(ChangePasswordDto passwordRequest) {
        String usercode = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("User does not exists."));

        if (!this.passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword()))
            throw new IncorrectPasswordException("Old password is incorrect.");
        
        user.setPassword(this.passwordEncoder.encode(passwordRequest.getPassword()));
        this.userRepository.save(user);
        return true;
    }

    @Override
    public void forgotPassword(String email) {
        User user = this.userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("This email does not exist."));

        String token = this.resetPasswordTokenService.createToken(user);
        this.emailService.sendMailToResetPassword(email, token);
    }

    @Override
    public void resetPassword(String token, ResetPasswordDto resetPasswordDto){
        ResetPasswordToken resetToken = this.resetPasswordTokenService.findByToken(token);

        if (resetToken.getExpires_at().isBefore(LocalDateTime.now())) throw new ResetPasswordExpiredException("Token expired.");

        User userToUpdate = this.userRepository.findById(resetToken.getUser().getId())
            .orElseThrow(()-> new ResourceNotFoundException("User not found."));

        userToUpdate.setPassword(this.passwordEncoder.encode(resetPasswordDto.getPassword()));
        this.userRepository.save(userToUpdate);

        // Delete used token in database
        this.resetPasswordTokenService.deleteByToken(resetToken.getToken()); // Here getToken returns the hash, don't forget!!!
    }

}
