package com.jcooldevelopment.easybank_api.contracts.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@RedisHash(value = "PasswordAttempt", timeToLive = 3600)
public class PasswordAttempt {

    @Id 
    @Indexed 
    private Integer id;

    @Indexed 
    private String usercode;

    private int attempts_number;
}
