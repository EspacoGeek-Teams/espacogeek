package com.espacogeek.geek.modals;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
<<<<<<< HEAD
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/ExternalReferenceModel.java
@Table(name = "externals_References")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalReferenceModel implements Serializable {
=======
@Table(name = "externals_referenceies")
@Data
public class ExternalReferenceModal implements Serializable {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/ExternalReferenceModal.java
=======
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "externals_referenceies")
@Data
public class ExternalReferenceModal implements Serializable {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_external_reference")
    private Integer id;

<<<<<<< HEAD
    @Column(name = "reference")
    private String reference;

<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/ExternalReferenceModel.java
    @ManyToOne
    @JoinColumn(name = "medias_id_media")
    private MediaModel media;
=======
    @JoinColumn(name = "midias_id_midia")
    private MidiaModal midiaModal;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/ExternalReferenceModal.java

    @ManyToOne
    @JoinColumn(name = "type_reference")
<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/ExternalReferenceModel.java
    private TypeReferenceModel typeReference;
=======
    private TypeReferenceModal typeReferenceModal;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/ExternalReferenceModal.java
=======
    @Column(name = "type_reference")
    private String typeReference;

    @JoinColumn(name = "midias_id_midia")
    private MidiaModal midiaModal;

    @JoinColumn(name = "type_reference")
    private TypeReferenceModal typeReferenceModal;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
}
