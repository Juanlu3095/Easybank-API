package com.jcooldevelopment.easybank_api.service.ResetPasswordToken;

import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.ResetPasswordToken;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.repository.ResetPasswordTokenRepository;
import com.jcooldevelopment.easybank_api.utils.EncryptUtils;

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService{

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    public ResetPasswordTokenServiceImpl(ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    @Override
    public String createToken(User user) {
        TreeMap<String, String> codes = EncryptUtils.generateTokenToResetPassword();
        this.resetPasswordTokenRepository.deleteByUser_id(user.getId()); // It only needs user.id
        ResetPasswordToken token = new ResetPasswordToken();
        token.setToken(codes.get("hash"));
        token.setUser(user);
        this.resetPasswordTokenRepository.save(token);
        // Delete other tokens the user has in database before saving the new one
        return codes.get("token");
    }


}
