package com.movies.oscar.service;

import com.movies.oscar.dto.AwardsIntervalDto;
import com.movies.oscar.dto.MoviesYearDto;
import com.movies.oscar.dto.YearsMoreWinnersDto;
import com.movies.oscar.entity.Movie;
import com.movies.oscar.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	public List<YearsMoreWinnersDto> getYearsWinners() {

		var movieList = movieRepository.findYearsWinners();

		return movieList.stream().filter(m -> m.getYear_movie() > 0).collect(Collectors.toList());
	}

	public List<MoviesYearDto> getYear(Integer year) {
		var movieList = movieRepository.findByYear(year);

		return movieList.stream().map(this::mapMoviesYearDto).collect(
				Collectors.toList());
	}

	private MoviesYearDto mapMoviesYearDto(Movie movie) {
		var moviesYearDto = new MoviesYearDto();
//		moviesYearDto.setProducers(movie.getProducers());
		moviesYearDto.setTitle(movie.getTitle());
//		moviesYearDto.setStudios(movie.getStudios());
		moviesYearDto.setWinner(movie.getWinner());

		return moviesYearDto;
	}

	public List<AwardsIntervalDto> getAwardsinterval() {
		var movieList = movieRepository.findYearsWinners();

		var moviesLessAwards = movieList.stream().filter(m -> m.getWins() == 0).collect(Collectors.toList());
		var qtMaxAwards = movieList.stream().mapToLong(YearsMoreWinnersDto::getWins).max().orElse(0);
		var moviesMoreAwards = movieList.stream().filter(m -> m.getWins() == qtMaxAwards).collect(Collectors.toList());

		return null;

	}

}
