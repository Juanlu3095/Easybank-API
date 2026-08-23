package com.jcooldevelopment.easybank_api.specs.operation;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import com.jcooldevelopment.easybank_api.contracts.entity.Operation;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

// https://blog.codmind.com/filtros-dinamicos-en-spring-boot-mediante/
// https://localhorse.net/article/spring-boot-consultas-personalizadas-con-jpql-y-criteria-api
public class OperationSpecs implements Specification<Operation>{

    private String concept, status, type;    

    public OperationSpecs(String concept, String status, String type) {
        this.concept = concept;
        this.status = status;
        this.type = type;
    }

    @Override
    public @Nullable Predicate toPredicate(Root<Operation> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(!concept.isEmpty()){
            predicates.add(cb.equal(root.get("concept"), concept));
        }

        if(!status.isEmpty()){
            predicates.add(cb.equal(root.get("status"), status));
        }

        if(!type.isEmpty()){
            predicates.add(cb.equal(root.get("type"), type));
        }

        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
    
}
