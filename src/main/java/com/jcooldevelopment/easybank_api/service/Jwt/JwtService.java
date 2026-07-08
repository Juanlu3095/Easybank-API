package com.jcooldevelopment.easybank_api.service.Jwt;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    public String getToken (UserDetails user);
}
