package com.jcooldevelopment.easybank_api.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.OperationAuthorization;
import com.jcooldevelopment.easybank_api.repository.OperationAuthorizationRepository;

@Component
@EnableScheduling
public class OperationAuthorizationTask {

    private final OperationAuthorizationRepository authorizationRepository;

    public OperationAuthorizationTask(
        OperationAuthorizationRepository operationAuthorizationRepository
    ){
        this.authorizationRepository = operationAuthorizationRepository;
    }

    /**
     * Deletes expired authorization every hour o'clock every day
     */
    @Scheduled(cron = " 0 0 * * * * ")
    public void deleteExpiredAuthorizations(){
        List<OperationAuthorization> authorizations = this.authorizationRepository.findAll();
        for (OperationAuthorization authorization : authorizations){
            if(authorization.getExpiresAt().isBefore(LocalDateTime.now())){
                this.authorizationRepository.delete(authorization);
            }
        }
    }
}
