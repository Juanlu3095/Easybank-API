package com.jcooldevelopment.easybank_api.contracts.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// https://www.geeksforgeeks.org/springboot/spring-boot-crud-operations-using-redis-database/
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@RedisHash(value = "PinAttempt")
public class PinAttempt {

    @Id
    @Indexed // For faster retrieval of data
    private Integer id;

    @Indexed 
    private String usercode;
    
    private int attempts_number;

}
