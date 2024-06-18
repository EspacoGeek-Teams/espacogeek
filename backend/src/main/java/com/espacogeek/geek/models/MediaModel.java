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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_media")
    private Integer id;

    @Column(name = "name_media")
    private String name;

    @Column(name = "episode_count")
    private Integer episode;

    @Column(name = "episode_length_in_minutes")
    private Integer episodeLength;

    @Column(name = "about")
    private String about;

    @Column(name = "url_cover")
    private String cover;

    @Column(name = "url_banner")
    private String banner;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private MediaCategoryModel mediaCategoryModel;

    @OneToMany(mappedBy = "mediaModal")
    private List<ExternalReferenceModel> externalReferenceModel;

    @ManyToMany
    @JoinTable(
        name = "medias_has_companies",
        joinColumns = @JoinColumn(name = "medias_id_media"),
        inverseJoinColumns = @JoinColumn(name = "companies_id_company"))
    private List<CompanyModel> companyModels;
    
    @ManyToMany
    @JoinTable(
        name = "medias_has_people",
        joinColumns = @JoinColumn(name = "medias_id_media"),
        inverseJoinColumns = @JoinColumn(name = "people_id_person"))
    private List<PeopleModel> peopleModels;

    @ManyToMany
    @JoinTable(
        name = "medias_has_genres",
        joinColumns = @JoinColumn(name = "medias_id_media"),
        inverseJoinColumns = @JoinColumn(name = "genres_id_genre"))
    private List<GenreModel> genreModels;
}
