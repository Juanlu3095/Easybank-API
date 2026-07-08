package com.jcooldevelopment.easybank_api.service.Auth;

import com.jcooldevelopment.easybank_api.dto.Auth.RegisterDto;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    String login (HttpServletRequest request);

    boolean register (RegisterDto userRegister);
}
