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
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "types_status")
<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/TypeStatusModel.java
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TypeStatusModel implements Serializable {
=======
@Data
public class TypeStatusModal implements Serializable {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/TypeStatusModal.java
=======
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "types_status")
@Data
public class TypeStatusModal implements Serializable {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 45, message = "{validation.name.size.too_long}")
    @Column(name = "name_status")
    private String name;
<<<<<<< HEAD

    @OneToMany(mappedBy = "typeStatus")
    @Transient
    private List<UserLibraryModel> userLibrary;
=======
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
}
