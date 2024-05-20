package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.modals.ExternalReferenceModal;

public interface ExternalReferenceRepository extends JpaRepository<ExternalReferenceModal, Integer> {

}
