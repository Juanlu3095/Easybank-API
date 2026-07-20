package com.jcooldevelopment.easybank_api.service.ResetPasswordToken;

import java.util.TreeMap;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.ResetPasswordToken;
import com.jcooldevelopment.easybank_api.contracts.entity.User;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
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
        return codes.get("token");
    }

    public UUID findUserByToken (String token) {
        String hash = EncryptUtils.shaHash(token);
        ResetPasswordToken resetToken = this.resetPasswordTokenRepository.findByToken(hash)
            .orElseThrow(() -> new ResourceNotFoundException("This token to reset password does not exist."));

        return resetToken.getUser().getId();
    }

    public ResetPasswordToken findByToken(String token){
        String hash = EncryptUtils.shaHash(token);

        return this.resetPasswordTokenRepository.findByToken(hash)
            .orElseThrow(() -> new ResourceNotFoundException("This token to reset password does not exist."));
    }

    public void deleteByToken(String token){
        ResetPasswordToken resetToken = this.resetPasswordTokenRepository.findByToken(token)
            .orElseThrow(() -> new ResourceNotFoundException("Reset token not found."));
        
        this.resetPasswordTokenRepository.delete(resetToken);
    }
}
