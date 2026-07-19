package com.jcooldevelopment.easybank_api.service.ResetPasswordToken;

import com.jcooldevelopment.easybank_api.contracts.entity.User;

public interface ResetPasswordTokenService {

    /**
     * Creates the token, saves it in database and returns the token.
     * @param user The user entity.
     * @return The token to send in mail.
     */
    String createToken(User user);
}
