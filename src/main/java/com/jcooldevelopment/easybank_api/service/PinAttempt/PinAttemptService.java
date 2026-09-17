package com.jcooldevelopment.easybank_api.service.PinAttempt;

import com.jcooldevelopment.easybank_api.contracts.entity.PinAttempt;

public interface PinAttemptService {

    /**
     * Adds attempt to database if exists. If not it creates and set attempt value to 1.
     * @param usercode The user's usercode to search in database.
     * @return The updated entity.
     */
    public PinAttempt addAttempt(String usercode);

    /**
     * Deletes row in database, which makes the counter zero again.
     * @param usercode The user's usercode to search in database.
     */
    public void deletePinAttempt(String usercode);
}
