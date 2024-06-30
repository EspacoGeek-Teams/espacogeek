package com.espacogeek.geek.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
<<<<<<< HEAD
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.TypeReferenceModel;

@Repository
// * @AbigailGeovana <T> serve para generalizar o repositorio assim consigo usar "org.springframework.data.domain.Example<T>" para utilizar objetos na pesquisa
public interface ExternalReferenceRepository<T> extends JpaRepository<ExternalReferenceModel, Integer> {
    Optional<ExternalReferenceModel> findByReferenceAndTypeReference (String reference, TypeReferenceModel typeReference);
    
    // Optional<ExternalReferenceModel> findByReference (String reference);
=======
import com.espacogeek.geek.modals.ExternalReferenceModal;
=======
import com.espacogeek.geek.modals.ExternalReferenceModal;

public interface ExternalReferenceRepository extends JpaRepository<ExternalReferenceModal, Integer> {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)

public interface ExternalReferenceRepository extends JpaRepository<ExternalReferenceModal, Integer> {

>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
}
