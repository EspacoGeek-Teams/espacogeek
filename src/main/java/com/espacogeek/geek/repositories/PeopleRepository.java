package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.models.PeopleModel;

public interface PeopleRepository extends JpaRepository<PeopleModel, Integer> {

}
