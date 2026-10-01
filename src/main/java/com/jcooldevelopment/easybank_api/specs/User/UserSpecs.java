package com.jcooldevelopment.easybank_api.specs.User;

import org.springframework.data.jpa.domain.Specification;

import com.jcooldevelopment.easybank_api.contracts.entity.User;

public class UserSpecs {
    
    public static Specification<User> findByName(String name){
        return (root, query, criteriaBuilder) -> {
            if(name == null || name.isEmpty()){
                return criteriaBuilder.conjunction(); // if name is "" it ignores this query
            }
            // https://www.baeldung.com/rest-api-search-language-spring-data-specifications
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<User> findBySurname(String surname){
        return (root, query, criteriaBuilder) -> {
            if(surname == null || surname.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("surname"), "%" + surname + "%");
        };
    }

    public static Specification<User> findByDni(String dni){
        return (root, query, criteriaBuilder) -> {
            if(dni == null || dni.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("dni"), dni);
        };
    }

    public static Specification<User> findByEmail(String email){
        return (root, query, criteriaBuilder) -> {
            if(email == null || email.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("email"), email);
        };
    }

    public static Specification<User> findByPhone(String phone){
        return (root, query, criteriaBuilder) -> {
            if(phone == null || phone.isEmpty()){
                return criteriaBuilder.conjunction(); // if phone is "" it ignores this query
            }
            return criteriaBuilder.equal(root.get("phone"), phone);
        };
    }

    public static Specification<User> findByRole(String role){
        return (root, query, criteriaBuilder) -> {
            if(role == null || role.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("role"), role);
        };
    }

    public static Specification<User> findByStatus(String status){
        return (root, query, criteriaBuilder) -> {
            if(status == null || status.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }
}
