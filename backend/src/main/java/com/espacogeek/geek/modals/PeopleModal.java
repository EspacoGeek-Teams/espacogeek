package com.espacogeek.geek.modals;

import java.util.Set;

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
public class PeopleModal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_people")
    private String name;

    @JoinColumn(name = "type_person")
    private TypePerson typePerson;

    @ManyToMany(mappedBy = "peopleModals")
    private Set<MidiaModal> midiaModal;
}
