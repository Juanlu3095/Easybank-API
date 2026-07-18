package com.jcooldevelopment.easybank_api.service.Auth;

import com.jcooldevelopment.easybank_api.dto.Auth.ChangePasswordDto;
import com.jcooldevelopment.easybank_api.dto.Auth.LoginDto;
import com.jcooldevelopment.easybank_api.dto.Auth.RegisterDto;
import com.jcooldevelopment.easybank_api.exception.IncorrectPasswordException;

public interface AuthService {

    String login (LoginDto request);

    boolean register (RegisterDto userRegister);
    
    /**
     * Allows change update password. Credentials are needed. User data is in SecurityContextHolder.
     * @throws IncorrectPasswordException if old password is not correct.
     * @return True if successful, false if not.
    */
   boolean changePassword(ChangePasswordDto passwordRequest);

   /**
    * Allows to send an email to update password. Credentials are not needed.
    * @return
    */
   boolean forgotPassword();

   boolean resetPassword();
}
