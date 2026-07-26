package com.jcooldevelopment.easybank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcooldevelopment.easybank_api.contracts.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long>{

    int countByIbanCode (String ibanCode);

    int countByBicCode (String bicCode);

    int countByLocalizationCode (String localizationCode);
}
