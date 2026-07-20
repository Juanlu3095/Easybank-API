package com.jcooldevelopment.easybank_api.service.ResetPasswordToken;

import java.util.UUID;

import com.jcooldevelopment.easybank_api.contracts.entity.ResetPasswordToken;
import com.jcooldevelopment.easybank_api.contracts.entity.User;

public interface ResetPasswordTokenService {

    /**
     * Creates the token, saves it in database and returns the token.
     * @param user The user entity.
     * @return The token to send in mail.
     */
    String createToken(User user);

    /**
     * Verifies if the given token to reset password exists and returns user id.
     * @param token The hashed token to verify.
     * @return The user UUID id.
     */
    UUID findUserByToken (String token);

    ResetPasswordToken findByToken(String token);

    void deleteByToken(String token);
}
