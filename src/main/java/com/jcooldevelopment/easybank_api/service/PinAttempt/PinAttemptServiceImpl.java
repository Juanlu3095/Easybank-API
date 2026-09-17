package com.jcooldevelopment.easybank_api.service.PinAttempt;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jcooldevelopment.easybank_api.contracts.entity.PinAttempt;
import com.jcooldevelopment.easybank_api.repository.PinAttemptRepository;

@Service 
public class PinAttemptServiceImpl implements PinAttemptService{

    private final PinAttemptRepository pinAttemptRepository;

    public PinAttemptServiceImpl(PinAttemptRepository pinAttemptRepository){
        this.pinAttemptRepository = pinAttemptRepository;
    }

    public PinAttempt addAttempt(String usercode){
        Optional<PinAttempt> userAttempt = this.pinAttemptRepository.findByUsercode(usercode);
        if (userAttempt.isPresent()) {
            PinAttempt updatedPinAttempt = userAttempt.get(); // Need to extract Pin to avoid error for been optional value
            updatedPinAttempt.setAttempts_number(updatedPinAttempt.getAttempts_number() + 1);
            return this.pinAttemptRepository.save(updatedPinAttempt);
        } else {
            PinAttempt newUserAttempt = new PinAttempt();
            newUserAttempt.setAttempts_number(1);
            newUserAttempt.setUsercode(usercode);
            return this.pinAttemptRepository.save(newUserAttempt);
        }
    }

    public void deletePinAttempt(String usercode){
        Optional<PinAttempt> userAttempt = this.pinAttemptRepository.findByUsercode(usercode);
        if (userAttempt.isPresent()) {
            PinAttempt pinAttemptToDelete = userAttempt.get();
            this.pinAttemptRepository.delete(pinAttemptToDelete);
        }
    }
}
