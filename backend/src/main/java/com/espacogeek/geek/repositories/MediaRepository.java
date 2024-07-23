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
    @Query("SELECT DISTINCT m, a, c, e FROM MediaModel m INNER JOIN AlternativeTitleModel a ON m.alternativeTitles title of a INNER JOIN MediaCategoryModel c ON c = :category INNER JOIN ExternalReference e ON e.media = m WHERE m.id = :id OR m.name LIKE CONCAT('%',:name,'%') OR a.name LIKE CONCAT('%',:alternativeTitle,'%')")
    List<MediaModel> findSerieByIdOrNameOrAlternativeTitleAndMediaCategory(@Param("id") Integer id, @Param("name") String name, @Param("alternativeTitle") String alternativeTitle, @Param("category") MediaCategoryModel category);
}
