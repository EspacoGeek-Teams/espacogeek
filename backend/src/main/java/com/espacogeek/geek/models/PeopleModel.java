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
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "people")
@Data
public class PeopleModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Integer id;

    @Column(name = "name_person")
    private String name;

    @JoinColumn(name = "type_person")
    private TypePersonModel typePerson;

    @ManyToMany(mappedBy = "peopleModels")
    private List<MediaModel> mediaModel;
}
