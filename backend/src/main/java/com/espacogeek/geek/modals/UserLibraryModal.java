package com.espacogeek.geek.modals;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Entity
@Table(name = "users_library")
@Data
public class UserLibraryModal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user_library")
    private Integer id;

    @Column(name = "progress")
    @Max(4)
    private Integer progress;

    @Column(name = "added_at")
    @CreationTimestamp
    private Date addedAt;

    @Column(name = "id_user")
    private UserModal userModal;

    @Column(name = "status")
    private TypeStatusModal typeStatusModal;
}
