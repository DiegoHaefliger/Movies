package com.movies.oscar.repository;

import com.movies.oscar.entity.MovieStudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieStudioRepository extends JpaRepository<MovieStudio, Integer> {
}
