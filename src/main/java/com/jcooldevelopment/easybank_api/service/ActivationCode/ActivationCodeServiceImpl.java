package com.jcooldevelopment.easybank_api.service.ActivationCode;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.ActivationCode;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.contracts.enums.UserStatus;
import com.jcooldevelopment.easybank_api.exception.ActivationCodeExpiredException;
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

    /**
    * Creates code to activate user.
    * @author Juanlu3095
    * @param id The id of the user
    * @param usercode The usercode from user
    * @return The generated activation code
    * @throws ResourceNotFoundException if user is not found
    */
    @Override
    public String createCode(UUID id, String usercode) {
        User user = this.userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        String code = EncryptUtils.generateActivationCode();
        
        ActivationCode activationCode = new ActivationCode();
        activationCode.setCode(code);
        activationCode.setUser_id(user);
        ActivationCode savedActivationCode = this.activationCodeRepository.save(activationCode);
        return savedActivationCode.getCode();
    }

    /**
    * Enables user by code and deletes that activation code.
    * @author Juanlu3095
    * @param code The code to enable an user
    * @return True if all is correct, false if not
    * @throws ResourceNotFoundException if code is not found
    * @throws ActivationCodeExpiredException if code's expires_at is before than actual LocalDateTime
    */
    @Override
    public boolean enableUser(String code) { // Should delete code from database and enable user
        ActivationCode activationCode = this.activationCodeRepository.findByCode(code)
            .orElseThrow(() -> new ResourceNotFoundException("Code not found."));

        if(activationCode.getExpires_at().isBefore(LocalDateTime.now())) throw new ActivationCodeExpiredException("This activation code has expired.");

        User user = this.userRepository.findById(activationCode.getUser_id().getId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        
        user.setStatus(UserStatus.ENABLED);
        User updatedUser = this.userRepository.save(user);
        if (updatedUser.isEnabled()) {
            this.activationCodeRepository.delete(activationCode);
            return true;
        }
        return false;
    }

}
