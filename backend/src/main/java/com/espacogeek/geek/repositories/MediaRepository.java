package com.espacogeek.geek.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.MediaModel;

@Repository
public interface MediaRepository extends JpaRepository<MediaModel, Integer> {
    List<MediaModel> findMediaByIdOrName(Integer id, String name);
}
