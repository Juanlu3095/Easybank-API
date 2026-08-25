package com.jcooldevelopment.easybank_api.specs.operation;

import org.springframework.data.jpa.domain.Specification;

import com.jcooldevelopment.easybank_api.contracts.entity.Operation;

// https://blog.codmind.com/filtros-dinamicos-en-spring-boot-mediante/
// https://localhorse.net/article/spring-boot-consultas-personalizadas-con-jpql-y-criteria-api
// https://medium.com/@miguel.duque7/how-to-use-spring-data-jpa-specifications-to-filter-sql-queries-with-join-tables-1e821178c76b
// Specification cannot be null error: https://stackoverflow.com/questions/60009797/jpa-specification-and-null-parameter-in-where-clause
public class OperationSpecs {

    public static Specification<Operation> findByConcept(String concept){
        return (root, query, criteriaBuilder) -> {
            if(concept.isEmpty()){
                return criteriaBuilder.conjunction();
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
    
}
