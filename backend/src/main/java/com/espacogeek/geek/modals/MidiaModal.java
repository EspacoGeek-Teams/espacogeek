package com.espacogeek.geek.modals;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "midias")
@Data
public class MidiaModal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_midia")
    private Integer id;
}
