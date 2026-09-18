package com.jcooldevelopment.easybank_api.contracts.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// https://www.geeksforgeeks.org/springboot/spring-boot-crud-operations-using-redis-database/
// TTL: https://www.baeldung.com/spring-data-redis-ttl
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@RedisHash(value = "PinAttempt", timeToLive = 3600)
public class PinAttempt {

    @Id
    @Indexed // For faster retrieval of data
    private Integer id;

    // Must use @Indexed to findByUsercode in repository:
    // https://stackoverflow.com/questions/62406125/findby-in-redis-repository-with-spring
    @Indexed 
    private String usercode;
    
    private int attempts_number;

}
