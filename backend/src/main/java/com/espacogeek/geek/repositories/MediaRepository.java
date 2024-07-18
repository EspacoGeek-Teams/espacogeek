package com.espacogeek.geek.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;

@Repository
public interface MediaRepository extends JpaRepository<MediaModel, Integer> {
    // SELECT DISTINCT * FROM medias m INNER JOIN alternative_titles a ON m.id_media = a.id_midia INNER JOIN media_categories c ON c.id_media_category = 1 WHERE m.id_media = 159191 OR m.name_media LIKE "%Stranger Things%" OR a.name_title LIKE "%Coisas Estranhas%";
    // https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.query-methods.at-query
    List<MediaModel> findSerieByIdOrNameOrAlternativeTitleAndMediaCategory(Integer id, String name, String alternativeTitle, MediaCategoryModel category);
}
