package com.jcooldevelopment.easybank_api.service.Email;

import com.jcooldevelopment.easybank_api.exception.EmailCouldNotBeSendException;

public interface EmailService {

    /**
     * Sends message to a given mail address with code to activate the account of an user.
     * @param usercode The usercode from user.
     * @param activationCode The email to send will have a link with this code to activate the account.
     * @param destination The email address
     * @throws EmailCouldNotBeSendException if MessagingException
     */
    void sendMailToEnableUser(String usercode, String activationCode, String destination);
}
