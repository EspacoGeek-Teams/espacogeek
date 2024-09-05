package com.espacogeek.geek.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;

@Repository
public interface MediaRepository<T> extends JpaRepository<MediaModel, Integer> {
    // if at some time the queries become more complex, see https://www.jooq.org/ and https://persistence.blazebit.com/.

    /**
     * That query search for Medias with the given params.
     * @param id of the media in the database, <b>priority 1st</b>.
     * @param name of the media in the database, <b>priority 2nd</b>.
     * @param alternativeTitle of the media in the database, <b>priority 3rd</b>.
     * @param category the category of media.
     * @return List of MediaModel with tge results of query operation.
     */
    @Query("SELECT m FROM MediaModel m " +
            "LEFT JOIN AlternativeTitleModel a ON a MEMBER OF m.alternativeTitles " +
            "WHERE m.mediaCategory = :category " +
            "AND m.id = :id " +
            "OR m.name LIKE CONCAT('%',:name,'%') " +
            "OR a.name LIKE CONCAT('%',:alternativeTitle,'%')")
    public List<MediaModel> findMediaByIdOrNameOrAlternativeTitleAndMediaCategory(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("alternativeTitle") String alternativeTitle,
        @Param("category") MediaCategoryModel category
    );

    /**
     * Find Media by ExternalReference and TypeReference.
     *
     * @param externalReference
     * @param typeReference
     * @return a Optional of MediaModel.
     */
    @Query("SELECT m FROM MediaModel m FULL JOIN ExternalReferenceModel e ON e MEMBER OF m.externalReference WHERE e = :reference AND e.typeReference = :typeReference")
    public Optional<MediaModel> findOneMediaByExternalReferenceAndTypeReference(@Param("reference") ExternalReferenceModel externalReference, @Param("typeReference") TypeReferenceModel typeReference);
}
