package com.jcooldevelopment.easybank_api.service.ActivationCode;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.exception.ActivationCodeExpiredException;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;

public interface ActivationCodeService {

    /**
    * Creates code to activate user, saves hashed code and returns original activation code.
    * @param id The id of the user
    * @return The generated activation code
    * @throws ResourceNotFoundException if user is not found
    */
    String createCode(UUID id);
    
    /**
    * Enables user by code and deletes that activation code.
    * @param code The code to enable an user
    * @return True if all is correct, false if not
    * @throws ResourceNotFoundException if code is not found
    * @throws ActivationCodeExpiredException if code's expires_at is before than actual LocalDateTime
    */
    boolean enableUser(String code);
}
