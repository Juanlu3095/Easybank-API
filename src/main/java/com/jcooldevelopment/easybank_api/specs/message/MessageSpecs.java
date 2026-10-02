package com.jcooldevelopment.easybank_api.specs.message;

import org.springframework.data.jpa.domain.Specification;

import com.jcooldevelopment.easybank_api.contracts.entity.Message;

public class MessageSpecs {
    
    public static Specification<Message> findByName(String name){
        return (root, query, criteriaBuilder) -> {
            if(name == null || name.isEmpty()){
                return criteriaBuilder.conjunction(); // if name is "" it ignores this query
            }
            // https://www.baeldung.com/rest-api-search-language-spring-data-specifications
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Message> findBySurname(String surname){
        return (root, query, criteriaBuilder) -> {
            if(surname == null || surname.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
        };
    }

    public static Specification<Message> findByEmail(String email){
        return (root, query, criteriaBuilder) -> {
            if(email == null || email.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("email"), email);
        };
    }

    public static Specification<Message> findByPhone(String phone){
        return (root, query, criteriaBuilder) -> {
            if(phone == null || phone.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("phone"), phone);
        };
    }

    public static Specification<Message> findByMessage(String message){
        return (root, query, criteriaBuilder) -> {
            if(message == null || message.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("message"), "%" + message + "%");
        };
    }
}
