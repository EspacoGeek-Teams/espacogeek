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
    // if at some time the queries become more complex, see https://www.jooq.org/
    // and https://persistence.blazebit.com/.

    /**
     * Finds media by matching name or alternative title within a specific media
     * category.
     *
     * This query searches for MediaModel entities where the name or any alternative
     * title matches the provided name or alternativeTitle parameters. It filters
     * the
     * results to only include those within the specified media category.
     *
     * @param name             The name of the media to search for.
     * @param alternativeTitle The alternative title of the media to search for.
     * @param category         The ID of the media category to filter results by.
     * @return A list of MediaModel objects that match the search criteria.
     */
    @Query("SELECT DISTINCT m FROM MediaModel m " +
            "LEFT JOIN AlternativeTitleModel a ON a MEMBER OF m.alternativeTitles " +
            "WHERE m.mediaCategory.id = :category " +
            "AND (m.name LIKE CONCAT('%',:name,'%') " +
            "OR a.name LIKE CONCAT('%',:alternativeTitle,'%'))")
    public List<MediaModel> findMediaByNameOrAlternativeTitleAndMediaCategory(
            @Param("name") String name,
            @Param("alternativeTitle") String alternativeTitle,
            @Param("category") Integer category);

    /**
     * Find Media by ExternalReference and TypeReference.
     *
     * @param externalReference
     * @param typeReference
     * @return a Optional of MediaModel.
     */
    @Query("SELECT m FROM MediaModel m JOIN ExternalReferenceModel e ON e MEMBER OF m.externalReference WHERE e.reference = :reference AND e.typeReference = :typeReference")
    public Optional<MediaModel> findOneMediaByExternalReferenceAndTypeReference(@Param("reference") String reference,
            @Param("typeReference") TypeReferenceModel typeReference);
}
