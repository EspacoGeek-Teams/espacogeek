package com.espacogeek.geek.models;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_library")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLibraryModel implements Serializable {
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

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    @NotNull
    private TypeStatusModel typeStatus;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_media", nullable = false)
    private MediaModel media;
}
