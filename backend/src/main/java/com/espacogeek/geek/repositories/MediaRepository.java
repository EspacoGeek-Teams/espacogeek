package com.espacogeek.geek.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;

@Repository
public interface MediaRepository extends JpaRepository<MediaModel, Integer> {
    @Query("SELECT DISTINCT m, a, c, e FROM MediaModel m INNER JOIN AlternativeTitleModel a ON m.alternativeTitles = a INNER JOIN MediaCategoryModel c ON c = ?4 INNER JOIN ExternalReference e ON e.media = m WHERE m.id = ?1 OR m.name LIKE '%?2%' OR a.name LIKE %?3%")
    List<MediaModel> findSerieByIdOrNameOrAlternativeTitleAndMediaCategory(Integer id, String name, String alternativeTitle, MediaCategoryModel category);
}
