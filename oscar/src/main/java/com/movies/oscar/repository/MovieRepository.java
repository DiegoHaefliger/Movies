package com.movies.oscar.repository;

import com.movies.oscar.dto.YearsMoreWinnersDto;
import com.movies.oscar.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	@Query(value = "SELECT new com.movies.oscar.dto.YearsMoreWinnersDto(" +
			"m.year, SUM(CASE WHEN m.winner = true THEN 1 ELSE 0 END) as wins) FROM Movie m GROUP BY m.year")
	List<YearsMoreWinnersDto> findYearsWinners();

	@Query(value = "SELECT new com.movies.oscar.dto.YearsMoreWinnersDto(" +
			"m.year, SUM(CASE WHEN m.winner = true THEN 1 ELSE 0 END) as wins) FROM Movie m GROUP BY m.year")
	List<YearsMoreWinnersDto> findYearsMoreWinners();

	 List<Movie> findByYear(Integer years);

}
