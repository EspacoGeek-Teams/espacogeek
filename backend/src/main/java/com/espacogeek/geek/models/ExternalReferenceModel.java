package com.espacogeek.geek.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "externals_References")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalReferenceModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_external_reference")
    private Integer id;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    @JoinColumn(name = "medias_id_media")
    private MediaModel mediaModal;

    @ManyToOne
    @JoinColumn(name = "type_reference")
    private TypeReferenceModel typeReferenceModel;
}
