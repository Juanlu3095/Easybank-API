package com.jcooldevelopment.easybank_api.service.ActivationCode;

import java.util.UUID;

public interface ActivationCodeService {

    String createCode(UUID id, String usercode);
    
    boolean enableUser(String code);
}
