package com.espacogeek.geek.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "externals_referenceies")
@Data
public class ExternalReferenceModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_external_reference")
    private Integer id;

    @Column(name = "type_reference")
    private String typeReference;

    @JoinColumn(name = "Medias_id_Media")
    private MediaModel MediaModal;

    @JoinColumn(name = "type_reference")
    private TypeReferenceModel typeReferenceModal;
}
