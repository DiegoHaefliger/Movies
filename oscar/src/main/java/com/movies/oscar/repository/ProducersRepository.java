package com.movies.oscar.repository;

import com.movies.oscar.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducersRepository extends JpaRepository<Producer, Integer> {

}
