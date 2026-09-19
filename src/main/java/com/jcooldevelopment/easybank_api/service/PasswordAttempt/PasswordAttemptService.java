package com.jcooldevelopment.easybank_api.service.PasswordAttempt;

import com.jcooldevelopment.easybank_api.contracts.entity.PasswordAttempt;

public interface PasswordAttemptService {
    
    /**
     * Adds a password attempt in redis. If there is none, it creates and if there is one document,
     * counter increase by 1.
     * @param usercode The usercode which identifies user.
     * @return The PasswordAttempt entity already updated.
     */
    PasswordAttempt addAttempt(String usercode);

    /**
     * Deletes a password attempt document from redis.
     * @param usercode The usercode which identifies user.
     */
    void deleteAttempts(String usercode);
}
