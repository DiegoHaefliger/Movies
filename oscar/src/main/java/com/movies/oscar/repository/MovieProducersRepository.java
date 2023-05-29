package com.movies.oscar.repository;

import com.movies.oscar.entity.MovieProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieProducersRepository extends JpaRepository<MovieProducer, Integer> {
}
