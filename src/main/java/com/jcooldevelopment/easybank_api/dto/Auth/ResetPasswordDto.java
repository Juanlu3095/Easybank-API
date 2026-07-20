package com.jcooldevelopment.easybank_api.dto.Auth;

import com.jcooldevelopment.easybank_api.annotations.PasswordMatchAnnotation;

@PasswordMatchAnnotation(message = "Fields password and repeat password do not match.")
public class ResetPasswordDto extends PasswordRepeatDto{

}
