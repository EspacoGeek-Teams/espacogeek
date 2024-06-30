package com.espacogeek.geek.modals;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
<<<<<<< HEAD
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
<<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/modals/MediaCategoryModel.java
@Table(name = "media_categories")
public class MediaCategoryModel implements Serializable {
========
@Table(name = "midia_categories")
public class MidiaCategoryModal implements Serializable {
>>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/MidiaCategoryModal.java
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_media_category")
=======
import lombok.Data;

@Data
@Entity
@Table(name = "midia_categories")
public class MidiaCategoryModal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_midia_category")
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
    private Integer id;

    @Column(name = "type_category")
    private String typeCategory;

<<<<<<< HEAD
<<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/modals/MediaCategoryModel.java
    @OneToMany(mappedBy = "mediaCategoryModel")
    @Transient
    private List<MediaModel> mediaModels;
========
    @OneToMany(mappedBy = "midiaCategoryModal")
    private List<MidiaModal> midiaModals;
>>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/MidiaCategoryModal.java
=======
    @OneToMany(mappedBy = "midiaCategoryModal")
    private List<MidiaModal> midiaModals;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
}
