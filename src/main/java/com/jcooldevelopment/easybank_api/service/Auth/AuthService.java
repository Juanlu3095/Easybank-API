package com.jcooldevelopment.easybank_api.service.Auth;

import com.jcooldevelopment.easybank_api.dto.Auth.LoginDto;
import com.jcooldevelopment.easybank_api.dto.Auth.RegisterDto;

public interface AuthService {

    String login (LoginDto request);

    boolean register (RegisterDto userRegister);
}
