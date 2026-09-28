package com.jcooldevelopment.easybank_api.specs.incidence;

import org.springframework.data.jpa.domain.Specification;

import com.jcooldevelopment.easybank_api.contracts.entity.Incidence;
import com.jcooldevelopment.easybank_api.contracts.entity.IncidenceType;
import com.jcooldevelopment.easybank_api.contracts.entity.User;

import jakarta.persistence.criteria.Join;

public class IncidenceSpecs {
    
    public static Specification<Incidence> findByMessage(String message){
        return (root, query, criteriaBuilder) -> {
            if(message == null || message.isEmpty()){
                return criteriaBuilder.conjunction(); // if message is "" it ignores this query
            }
            return criteriaBuilder.equal(root.get("message"), message); 
            // attributeName in root.get is the column to use for search
        };
    }

    public static Specification<Incidence> findByStatus(String status){
        return (root, query, criteriaBuilder) -> {
            if(status == null || status.isEmpty()){
                return criteriaBuilder.conjunction(); // if status is "" it ignores this query
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Incidence> findByUserName(String userName){
        return (root, query, criteriaBuilder) -> {
            if(userName == null || userName.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            Join<User,Incidence> incidenceUser = root.join("user_id");
            return criteriaBuilder.equal(incidenceUser.get("name"), userName);
        };
    }

    public static Specification<Incidence> findByUserSurname(String userSurname){
        return (root, query, criteriaBuilder) -> {
            if(userSurname == null || userSurname.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            Join<User,Incidence> incidenceUser = root.join("user_id");
            return criteriaBuilder.equal(incidenceUser.get("surname"), userSurname);
        };
    }

    public static Specification<Incidence> findByUserEmail(String userEmail){
        return (root, query, criteriaBuilder) -> {
            if(userEmail == null || userEmail.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            Join<User,Incidence> incidenceUser = root.join("user_id");
            return criteriaBuilder.equal(incidenceUser.get("email"), userEmail);
        };
    }

    public static Specification<Incidence> findByUserDni(String userDni){
        return (root, query, criteriaBuilder) -> {
            if(userDni == null || userDni.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            Join<User,Incidence> incidenceUser = root.join("user_id");
            return criteriaBuilder.equal(incidenceUser.get("dni"), userDni);
        };
    }

    public static Specification<Incidence> findByIncidenceType(String incidenceType){
        return (root, query, criteriaBuilder) -> {
            if(incidenceType == null || incidenceType.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            Join<IncidenceType,Incidence> incidenceIncidenceType = root.join("incidence_type");
            return criteriaBuilder.equal(incidenceIncidenceType.get("name"), incidenceType);
        };
    }
}
