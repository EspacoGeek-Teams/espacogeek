package com.espacogeek.geek.modals;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
<<<<<<< HEAD
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "people")
<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/PeopleModel.java
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PeopleModel implements Serializable {
=======
@Data
public class PeopleModal implements Serializable {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/PeopleModal.java
=======
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "people")
@Data
public class PeopleModal implements Serializable {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Integer id;

    @Column(name = "name_person")
    private String name;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "type_person")
    private TypePerson typePerson;

<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/PeopleModel.java
    @ManyToMany(mappedBy = "people")
    private List<MediaModel> media;
=======
    @ManyToMany(mappedBy = "peopleModals")
    private List<MidiaModal> midiaModal;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/PeopleModal.java
=======
    @JoinColumn(name = "type_person")
    private TypePerson typePerson;

    @ManyToMany(mappedBy = "peopleModals")
    private List<MidiaModal> midiaModal;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
}
