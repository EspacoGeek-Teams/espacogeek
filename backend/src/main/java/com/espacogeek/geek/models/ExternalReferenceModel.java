package com.espacogeek.geek.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "externals_References")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExternalReferenceModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_external_reference")
    private Integer idExternalReference;

    @Column(name = "reference")
    private String reference;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "medias_id_media", nullable = false)
    private MediaModel media;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "type_reference", nullable = false)
    private TypeReferenceModel typeReference;
}
