package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.MediaCategoryModel;

@Repository
public interface MediaCategoryRepository extends JpaRepository<MediaCategoryModel, Integer> {

}
