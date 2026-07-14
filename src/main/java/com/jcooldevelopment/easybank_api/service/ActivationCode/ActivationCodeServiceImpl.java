package com.jcooldevelopment.easybank_api.service.ActivationCode;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.ActivationCode;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.UserStatus;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
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
    public String createCode(UUID id, String usercode) {
        User user = this.userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        String code = EncryptUtils.generateActivationCode();
        
        ActivationCode activationCode = new ActivationCode();
        activationCode.setCode(code);
        activationCode.setUsed(false);
        activationCode.setUser_id(user);
        ActivationCode savedActivationCode = this.activationCodeRepository.save(activationCode);
        return savedActivationCode.getCode();
    }

    @Override
    public boolean enableUser(String code) { // Should delete code from database and verify if isUsed is false to activate
        ActivationCode activationCode = this.activationCodeRepository.findByCode(code)
            .orElseThrow(() -> new ResourceNotFoundException("Code not found."));

        User user = this.userRepository.findById(activationCode.getUser_id().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        
        user.setStatus(UserStatus.ENABLED);
        this.userRepository.save(user);
        return true;
    }

}
