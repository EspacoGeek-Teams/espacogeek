package com.espacogeek.geek.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "type_reference")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypeReferenceModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_reference")
    private Integer id;

    @Column(name = "name_reference")
    private String nameReference;

    @OneToMany(mappedBy = "typeReferenceModel")
    private List<ExternalReferenceModel> externalReferenceModels;
}
