package com.espacogeek.geek.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "seasons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeasonModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_season")
    private Integer id;

    @Column(name = "name_season")
    private String name;

    @Column(name = "air_date")
    private Date airDate;

    @Column(name = "end_air_date")
    private Date endAirDate;

    @Column(name = "about_season", length = 10000)
    private String about;

    @Column(name = "path_cover")
    private String cover;

    @Column(name = "season_number")
    private Integer seasonNumber;

    @Column(name = "episode_count")
    private Integer episodeCount;

    @JoinColumn(name = "medias_id_medias", nullable = false)
    @NotNull
    @ManyToOne
    private MediaModel media;
}
