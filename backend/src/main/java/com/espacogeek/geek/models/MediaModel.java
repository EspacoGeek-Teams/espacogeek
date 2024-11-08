package com.espacogeek.geek.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "medias", indexes = {@Index(name = "idx_name_media", columnList = "name_media", unique = true)})
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
    private Integer totalEpisodes;

    @Column(name = "episode_length_in_minutes")
    private Integer episodeLength;

    @Column(name = "about", length = 10000)
    private String about;

    @Column(name = "url_cover")
    private String cover;

    @Column(name = "url_banner")
    private String banner;

    @JoinColumn(name = "id_category", nullable = false)
    @ManyToOne
    @NotNull
    private MediaCategoryModel mediaCategory;

    @OneToMany(mappedBy = "media", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @NotNull
    private List<ExternalReferenceModel> externalReference;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medias_has_companies",
        joinColumns = @JoinColumn(name = "medias_id_media"),
        inverseJoinColumns = @JoinColumn(name = "companies_id_company"))
    @Fetch(FetchMode.SUBSELECT)
    private List<CompanyModel> company;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medias_has_people",
        joinColumns = @JoinColumn(name = "medias_id_media"),
        inverseJoinColumns = @JoinColumn(name = "people_id_person"))
    @Fetch(FetchMode.SUBSELECT)
    private List<PeopleModel> people;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medias_has_genres",
        joinColumns = @JoinColumn(name = "medias_id_media"),
        inverseJoinColumns = @JoinColumn(name = "genres_id_genre"))
    @Fetch(FetchMode.SUBSELECT)
    private List<GenreModel> genre;

    @Column(name = "update_at")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @OneToMany(mappedBy = "media", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private List<AlternativeTitleModel> alternativeTitles;

    @OneToMany(mappedBy = "media")
    @Fetch(FetchMode.SUBSELECT)
    private List<SeasonModel> season;
}
