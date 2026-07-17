package com.jcooldevelopment.easybank_api.service.ActivationCode;

import java.time.LocalDateTime;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.ActivationCode;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.UserStatus;
import com.jcooldevelopment.easybank_api.exception.ActivationCodeExpiredException;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.exception.UserAlreadyEnabledException;
import com.jcooldevelopment.easybank_api.repository.ActivationCodeRepository;
import com.jcooldevelopment.easybank_api.repository.UserRepository;
import com.jcooldevelopment.easybank_api.utils.EncryptUtils;

@Service
public class ActivationCodeServiceImpl implements ActivationCodeService{

    private final ActivationCodeRepository activationCodeRepository;
    private final UserRepository userRepository;

    public ActivationCodeServiceImpl(ActivationCodeRepository activationCodeRepository, UserRepository userRepository) {
        this.activationCodeRepository = activationCodeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createCode(UUID id) {
        User user = this.userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        TreeMap<String, String> code = EncryptUtils.generateActivationCode();
        
        ActivationCode activationCode = new ActivationCode();
        activationCode.setCode(code.get("hash"));
        activationCode.setUser_id(user);
        this.activationCodeRepository.save(activationCode);
        return code.get("activationCode");
    }

    @Override
    public boolean enableUser(String code) { // Should delete code from database and enable user
        String hash = EncryptUtils.shaHash(code);

        ActivationCode activationCode = this.activationCodeRepository.findByCode(hash)
            .orElseThrow(() -> new ResourceNotFoundException("Code not found."));

        if(activationCode.getExpires_at().isBefore(LocalDateTime.now())) throw new ActivationCodeExpiredException("This activation code has expired.");

        User user = this.userRepository.findById(activationCode.getUser_id().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        if (user.isEnabled()) throw new UserAlreadyEnabledException("This account is already enabled.");
        
        user.setStatus(UserStatus.ENABLED);
        User updatedUser = this.userRepository.save(user);
        if (updatedUser.isEnabled()) {
            this.activationCodeRepository.delete(activationCode);
            return true;
        }
        return false;
    }

}
