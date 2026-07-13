package com.jcooldevelopment.easybank_api.service.Email;

public interface EmailService {

    void sendMailToEnableUser(String usercode, String activationCode, String destination);
}
