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
import lombok.Data;

@Data
@Entity
@Table(name = "midia_categories")
public class MidiaCategoryModal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_midia_category")
    private Integer id;

    @Column(name = "type_category")
    private String typeCategory;

    @OneToMany(mappedBy = "midiaCategoryModal")
    private List<MidiaModal> midiaModals;
}
