package com.espacogeek.geek.modals;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
<<<<<<< HEAD
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_library")
<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/UserLibraryModel.java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLibraryModel implements Serializable {
=======
@Data
public class UserLibraryModal implements Serializable {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/UserLibraryModal.java
=======
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Entity
@Table(name = "users_library")
@Data
public class UserLibraryModal implements Serializable {
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_library")
    private Integer id;

    @Column(name = "progress")
    @Max(4)
    private Integer progress;

    @Column(name = "added_at")
    @CreationTimestamp
    private Date addedAt;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "id_user")
<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/UserLibraryModel.java
    private UserModel user;
=======
    @Transient
    private UserModal userModal;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/UserLibraryModal.java

    @ManyToOne
    @JoinColumn(name = "status")
<<<<<<< HEAD:backend/src/main/java/com/espacogeek/geek/models/UserLibraryModel.java
    private TypeStatusModel typeStatus;

    @ManyToOne
    @JoinColumn(name = "id_media")
    private MediaModel media;
=======
    private TypeStatusModal typeStatusModal;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia):backend/src/main/java/com/espacogeek/geek/modals/UserLibraryModal.java
=======
    @JoinColumn(name = "id_user")
    @Transient
    private UserModal userModal;

    @JoinColumn(name = "status")
    private TypeStatusModal typeStatusModal;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
}
