package com.espacogeek.geek.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "people")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PeopleModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Integer id;

    @Column(name = "name_person")
    private String name;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "type_person", nullable = false)
    private TypePerson typePerson;

    @ManyToMany(mappedBy = "people")
    private List<MediaModel> media;
}
