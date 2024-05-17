package com.espacogeek.geek.modals;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "midias")
@Data
public class MidiaModal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_midia")
    private Integer id;

    @Column(name = "name_midia")
    private String name;

    @Column(name = "episode_count")
    private Integer episode;

    @Column(name = "episode_lenght_in_minutes")
    private Integer episodeLenght;

    @Column(name = "about")
    private String about;

    @Column(name = "path_cover")
    private String pathCover;

    //TODO: category relationship
}
