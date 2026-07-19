package com.jcooldevelopment.easybank_api.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.ResetPasswordToken;
import com.jcooldevelopment.easybank_api.repository.ResetPasswordTokenRepository;

@Component
@EnableScheduling
public class ResetPasswordTokenTasks {

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    public ResetPasswordTokenTasks(ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    /**
    * Programmed task to delete expired tokens for reset password.
    */
    @Scheduled(cron = " 0 0 * * * *")
    public void deleteExpiredTokens () {
        List<ResetPasswordToken> tokens = this.resetPasswordTokenRepository.findAll();
        for (ResetPasswordToken token : tokens) {
            if(token.getExpires_at().isBefore(LocalDateTime.now())) {
                this.resetPasswordTokenRepository.delete(token);
            }
        }
    }
}
