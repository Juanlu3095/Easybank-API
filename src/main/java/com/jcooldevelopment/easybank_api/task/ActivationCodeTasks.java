package com.jcooldevelopment.easybank_api.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jcooldevelopment.easybank_api.contracts.entity.ActivationCode;
import com.jcooldevelopment.easybank_api.repository.ActivationCodeRepository;

// https://www.aluracursos.com/blog/programando-tareas-con-scheduled-de-spring
// https://medium.com/@kandaanusha/job-scheduling-in-java-2fc16196db1d
@Component
@EnableScheduling // Allows programmed tasks
public class ActivationCodeTasks {
    private final ActivationCodeRepository activationCodeRepository;

    public ActivationCodeTasks(ActivationCodeRepository activationCodeRepository) {
        this.activationCodeRepository = activationCodeRepository;
    }

    /**
    * Programmed task to delete those activation codes which are expired.
    * @author Juanlu3095
    */
    @Scheduled(cron = " 0 0 * * * *")
    public void deleteExpiredActivationCodes () {
        List<ActivationCode> codes = this.activationCodeRepository.findAll();
        for (ActivationCode code : codes) {
            if(code.getExpires_at().isBefore(LocalDateTime.now())) {
                this.activationCodeRepository.delete(code);
            }
        }
    }
}
