package com.espacogeek.geek.modals;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "externals_referenceies")
@Data
public class ExternalReferenceModal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_external_reference")
    private Integer id;

    @Column(name = "type_reference")
    private String typeReference;

    @Column(name = "midias_id_midia")
    private MidiaModal midiaModal;
}
