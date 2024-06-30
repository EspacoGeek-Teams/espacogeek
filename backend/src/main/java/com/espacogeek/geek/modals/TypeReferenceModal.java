package com.espacogeek.geek.modals;

import java.io.Serializable;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
<<<<<<< HEAD
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "type_reference")
<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/TypeReferenceModel.java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypeReferenceModel implements Serializable {
=======
@Data
public class TypeReferenceModal implements Serializable {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/TypeReferenceModal.java
=======
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "type_reference")
@Data
public class TypeReferenceModal implements Serializable {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_reference")
    private Integer id;

    @Column(name = "name_reference")
    private String nameReference;
<<<<<<< HEAD

    @OneToMany(mappedBy = "typeReference")
    @Transient
    private List<ExternalReferenceModel> externalReference;
=======
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
}
