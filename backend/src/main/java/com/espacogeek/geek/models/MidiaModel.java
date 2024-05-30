package com.espacogeek.geek.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "midias")
@Data
public class MidiaModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private MidiaCategoryModel midiaCategoryModal;

    @ManyToMany
    @JoinTable(
        name = "midias_has_companies",
        joinColumns = @JoinColumn(name = "midias_id_midia"),
        inverseJoinColumns = @JoinColumn(name = "companies_id_company"))
    private List<CompanyModel> companyModals;

    @ManyToMany
    @JoinTable(
        name = "midias_has_people",
        joinColumns = @JoinColumn(name = "midias_id_midia"),
        inverseJoinColumns = @JoinColumn(name = "people_id_person"))
    private List<PeopleModel> peopleModals;

    @ManyToMany
    @JoinTable(
        name = "midias_has_genres",
        joinColumns = @JoinColumn(name = "midias_id_midia"),
        inverseJoinColumns = @JoinColumn(name = "genres_id_genre"))
    private List<GenreModel> genreModals;
}
