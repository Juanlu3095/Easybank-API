package com.jcooldevelopment.easybank_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jcooldevelopment.easybank_api.contracts.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer>{

    int countByCode(String code);
}
