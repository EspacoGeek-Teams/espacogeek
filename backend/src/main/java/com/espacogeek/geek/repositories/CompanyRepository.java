package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.modals.CompanyModal;

public interface CompanyRepository extends JpaRepository<CompanyModal, Integer>{

}
