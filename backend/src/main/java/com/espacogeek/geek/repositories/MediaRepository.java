package com.espacogeek.geek.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;

@Repository
public interface MediaRepository extends JpaRepository<MediaModel, Integer> {
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
     * That query search for Medias with the given params. 
     * <p>
     * <b>Only use this method when needed</b>, because this is a special method that <b>join fetch {@link MediaModel#getAlternativeTitles()}</b>. So use consider use {@link #findMediaByIdOrNameOrAlternativeTitleAndMediaCategory(Integer, String, String, MediaCategoryModel)} instead to avoid performance problems.
     * @param id of the media in the database, <b>priority 1st</b>.
     * @param name of the media in the database, <b>priority 2nd</b>.
     * @param alternativeTitle of the media in the database, <b>priority 3rd</b>.
     * @param category the category of media.
     * @return List of MediaModel with tge results of query operation.
     */
    @Query("SELECT m FROM MediaModel m " +
            "JOIN FETCH m.alternativeTitles " +
            "LEFT JOIN AlternativeTitleModel a ON a MEMBER OF m.alternativeTitles " +
            "WHERE m.mediaCategory = :category " +
            "AND m.id = :id " +
            "OR m.name LIKE CONCAT('%',:name,'%') " +
            "OR a.name LIKE CONCAT('%',:alternativeTitle,'%')")
    public List<MediaModel> findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithAlternativeTitles(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("alternativeTitle") String alternativeTitle,
        @Param("category") MediaCategoryModel category
    );

    /**
     * That query search for Medias with the given params. 
     * <p>
     * <b>Only use this method when needed</b>, because this is a special method that <b>join fetch {@link MediaModel#getSeason()}</b>. So use consider use {@link #findMediaByIdOrNameOrAlternativeTitleAndMediaCategory(Integer, String, String, MediaCategoryModel)} instead to avoid performance problems.
     * @param id of the media in the database, <b>priority 1st</b>.
     * @param name of the media in the database, <b>priority 2nd</b>.
     * @param alternativeTitle of the media in the database, <b>priority 3rd</b>.
     * @param category the category of media.
     * @return List of MediaModel with tge results of query operation.
     */
    @Query("SELECT m FROM MediaModel m " +
            "JOIN FETCH m.season " +
            "LEFT JOIN AlternativeTitleModel a ON a MEMBER OF m.alternativeTitles " +
            "WHERE m.mediaCategory = :category " +
            "AND m.id = :id " +
            "OR m.name LIKE CONCAT('%',:name,'%') " +
            "OR a.name LIKE CONCAT('%',:alternativeTitle,'%')")
    public List<MediaModel> findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithSeason(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("alternativeTitle") String alternativeTitle,
        @Param("category") MediaCategoryModel category
    );

    /**
     * That query search for Medias with the given params. 
     * <p>
     * <b>Only use this method when needed</b>, because this is a special method that <b>join fetch {@link MediaModel#getGenre()}</b>. So use consider use {@link #findMediaByIdOrNameOrAlternativeTitleAndMediaCategory(Integer, String, String, MediaCategoryModel)} instead to avoid performance problems.
     * @param id of the media in the database, <b>priority 1st</b>.
     * @param name of the media in the database, <b>priority 2nd</b>.
     * @param alternativeTitle of the media in the database, <b>priority 3rd</b>.
     * @param category the category of media.
     * @return List of MediaModel with tge results of query operation.
     */
    @Query("SELECT m FROM MediaModel m " +
            "JOIN FETCH m.genre " +
            "LEFT JOIN AlternativeTitleModel a ON a MEMBER OF m.alternativeTitles " +
            "WHERE m.mediaCategory = :category " +
            "AND m.id = :id " +
            "OR m.name LIKE CONCAT('%',:name,'%') " +
            "OR a.name LIKE CONCAT('%',:alternativeTitle,'%')")
    public List<MediaModel> findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithGenre(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("alternativeTitle") String alternativeTitle,
        @Param("category") MediaCategoryModel category
    );

    /**
     * That query search for Medias with the given params. 
     * <p>
     * <b>Only use this method when needed</b>, because this is a special method that <b>join fetch {@link MediaModel#getPeople()}</b>. So use consider use {@link #findMediaByIdOrNameOrAlternativeTitleAndMediaCategory(Integer, String, String, MediaCategoryModel)} instead to avoid performance problems.
     * @param id of the media in the database, <b>priority 1st</b>.
     * @param name of the media in the database, <b>priority 2nd</b>.
     * @param alternativeTitle of the media in the database, <b>priority 3rd</b>.
     * @param category the category of media.
     * @return List of MediaModel with tge results of query operation.
     */
    @Query("SELECT m FROM MediaModel m " +
            "JOIN FETCH m.people " +
            "LEFT JOIN AlternativeTitleModel a ON a MEMBER OF m.alternativeTitles " +
            "WHERE m.mediaCategory = :category " +
            "AND m.id = :id " +
            "OR m.name LIKE CONCAT('%',:name,'%') " +
            "OR a.name LIKE CONCAT('%',:alternativeTitle,'%')")
    public List<MediaModel> findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithPeople(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("alternativeTitle") String alternativeTitle,
        @Param("category") MediaCategoryModel category
    );

    /**
     * That query search for Medias with the given params. 
     * <p>
     * <b>Only use this method when needed</b>, because this is a special method that <b>join fetch {@link MediaModel#getCompany()}</b>. So use consider use {@link #findMediaByIdOrNameOrAlternativeTitleAndMediaCategory(Integer, String, String, MediaCategoryModel)} instead to avoid performance problems.
     * @param id of the media in the database, <b>priority 1st</b>.
     * @param name of the media in the database, <b>priority 2nd</b>.
     * @param alternativeTitle of the media in the database, <b>priority 3rd</b>.
     * @param category the category of media.
     * @return List of MediaModel with tge results of query operation.
     */
    @Query("SELECT m FROM MediaModel m " +
            "JOIN FETCH m.company " +
            "LEFT JOIN AlternativeTitleModel a ON a MEMBER OF m.alternativeTitles " +
            "WHERE m.mediaCategory = :category " +
            "AND m.id = :id " +
            "OR m.name LIKE CONCAT('%',:name,'%') " +
            "OR a.name LIKE CONCAT('%',:alternativeTitle,'%')")
    public List<MediaModel> findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithCompany(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("alternativeTitle") String alternativeTitle,
        @Param("category") MediaCategoryModel category
    );

    /**
     * That query search for Medias with the given params. 
     * <p>
     * <b>Only use this method when needed</b>, because this is a special method that <b>join fetch {@link MediaModel#getExternalReference()}</b>. So use consider use {@link #findMediaByIdOrNameOrAlternativeTitleAndMediaCategory(Integer, String, String, MediaCategoryModel)} instead to avoid performance problems.
     * @param id of the media in the database, <b>priority 1st</b>.
     * @param name of the media in the database, <b>priority 2nd</b>.
     * @param alternativeTitle of the media in the database, <b>priority 3rd</b>.
     * @param category the category of media.
     * @return List of MediaModel with tge results of query operation.
     */
    @Query("SELECT m FROM MediaModel m " +
            "JOIN FETCH m.externalReference " +
            "LEFT JOIN AlternativeTitleModel a ON a MEMBER OF m.alternativeTitles " +
            "WHERE m.mediaCategory = :category " +
            "AND m.id = :id " +
            "OR m.name LIKE CONCAT('%',:name,'%') " +
            "OR a.name LIKE CONCAT('%',:alternativeTitle,'%')")
    public List<MediaModel> findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithExternalReference(
        @Param("id") Integer id,
        @Param("name") String name,
        @Param("alternativeTitle") String alternativeTitle,
        @Param("category") MediaCategoryModel category
    );
}
