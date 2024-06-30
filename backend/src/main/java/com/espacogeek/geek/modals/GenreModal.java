package com.espacogeek.geek.modals;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
<<<<<<< HEAD
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
=======
import lombok.Data;

@Data
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
@Entity
@Table(name = "genres")
public class GenreModal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genre")
    private Integer id;

    @Column(name = "name_genre")
    private String name;

<<<<<<< HEAD
<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/GenreModel.java
    @ManyToMany(mappedBy = "genre")
    private List<MediaModel> media;
=======
    @ManyToMany(mappedBy = "genreModals")
    private List<MidiaModal> midiaModals;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/GenreModal.java
=======
    @ManyToMany(mappedBy = "genreModals")
    private List<MidiaModal> midiaModals;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
}
