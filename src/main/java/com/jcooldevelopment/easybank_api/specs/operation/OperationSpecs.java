package com.jcooldevelopment.easybank_api.specs.operation;

import org.springframework.data.jpa.domain.Specification;

import com.jcooldevelopment.easybank_api.contracts.entity.Account;
import com.jcooldevelopment.easybank_api.contracts.entity.Operation;

import jakarta.persistence.criteria.Join;

// https://blog.codmind.com/filtros-dinamicos-en-spring-boot-mediante/
// https://localhorse.net/article/spring-boot-consultas-personalizadas-con-jpql-y-criteria-api
// https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html
// https://medium.com/@miguel.duque7/how-to-use-spring-data-jpa-specifications-to-filter-sql-queries-with-join-tables-1e821178c76b
// Joins with specification: https://www.baeldung.com/spring-jpa-joining-tables
// Specification cannot be null error: https://stackoverflow.com/questions/60009797/jpa-specification-and-null-parameter-in-where-clause
public class OperationSpecs {

    public static Specification<Operation> findByConcept(String concept){
        return (root, query, criteriaBuilder) -> {
            if(concept.isEmpty()){
                return criteriaBuilder.conjunction(); // if concept is "" it ignores this query
            }
            return criteriaBuilder.equal(root.get("concept"), concept);
        };
    }

    public static Specification<Operation> findByType(String type){
        return (root, query, criteriaBuilder) -> {
            if(type.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("type"), type);
        };
    }

    public static Specification<Operation> findByStatus(String status){
        return (root, query, criteriaBuilder) -> {
            if(status.isEmpty()){
                return criteriaBuilder.conjunction();
            }
           return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Operation> findByOrdererIban(String ordererIban){
        return (root, query, criteriaBuilder) -> {
            if(ordererIban.isEmpty()){
                return criteriaBuilder.conjunction();
            }
            Join<Account,Operation> operationAccounts = root.join("ordererAccount");
            return criteriaBuilder.equal(operationAccounts.get("iban"), ordererIban);
        };
    }

    public static Specification<Operation> findByCounterpartIban(String counterpartIban, String bankCode){
        return (root, query, criteriaBuilder) -> {
            if(counterpartIban.isEmpty() || bankCode.isBlank()){
                return criteriaBuilder.conjunction();
            }
            // If account's bank code is not equal to our bank we use counterpart account external
            if(!counterpartIban.substring(4, 8).equals(bankCode)) {
                return criteriaBuilder.equal(root.get("counterpartExternalAccount"), counterpartIban);
            } else {
                Join<Account,Operation> operationAccounts = root.join("counterpartAccount");
                return criteriaBuilder.equal(operationAccounts.get("iban"), counterpartIban);
            }
        };
    }
    
}
