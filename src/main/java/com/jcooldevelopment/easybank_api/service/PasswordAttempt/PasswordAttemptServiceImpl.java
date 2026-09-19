package com.jcooldevelopment.easybank_api.service.PasswordAttempt;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.PasswordAttempt;
import com.jcooldevelopment.easybank_api.exception.ResourceNotFoundException;
import com.jcooldevelopment.easybank_api.repository.PasswordAttemptRepository;

@Service 
public class PasswordAttemptServiceImpl implements PasswordAttemptService{

    private final PasswordAttemptRepository passwordAttemptRepository;

    public PasswordAttemptServiceImpl(PasswordAttemptRepository passwordAttemptRepository){
        this.passwordAttemptRepository = passwordAttemptRepository;
    }

    @Override
    public PasswordAttempt addAttempt(String usercode) {
        Optional<PasswordAttempt> passwordAttempts = this.passwordAttemptRepository.findByUsercode(usercode);
        if(passwordAttempts.isPresent()){
            PasswordAttempt updatedPasswordAttempt = passwordAttempts.get();
            updatedPasswordAttempt.setAttempts_number(updatedPasswordAttempt.getAttempts_number() + 1);
            return this.passwordAttemptRepository.save(updatedPasswordAttempt);
        } else {
            PasswordAttempt passwordAttempt = new PasswordAttempt();
            passwordAttempt.setUsercode(usercode);
            passwordAttempt.setAttempts_number(1);
            return this.passwordAttemptRepository.save(passwordAttempt);
        }
    }

    @Override
    public void deleteAttempts(String usercode) {
        PasswordAttempt passwordAttempts = this.passwordAttemptRepository.findByUsercode(usercode)
            .orElseThrow(() -> new ResourceNotFoundException("This user has no failed attempts."));
        
        this.passwordAttemptRepository.delete(passwordAttempts);
    }

}
