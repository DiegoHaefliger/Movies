package com.movies.oscar.controller;

import com.movies.oscar.dto.AwardsIntervalDto;
import com.movies.oscar.dto.MoviesDto;
import com.movies.oscar.dto.YearsMoreWinnersDto;
import com.movies.oscar.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/movie")
public class MovieController {

	@Autowired
	private MovieService movieService;

	@GetMapping(value = "")
	public List<MoviesDto> getMovies() {
		return movieService.getMovies();
	}

	@GetMapping(value = "/yearswinners")
	public List<YearsMoreWinnersDto> getYearsWinners() {
		return movieService.getYearsWinners();
	}

	@GetMapping(value = "/year/{year}")
	public List<MoviesDto> getYear(@PathVariable("year") Integer year) {
		return movieService.getYear(year);
	}

	@GetMapping(value = "/awardsinterval")
	public AwardsIntervalDto getAwardsinterval() {
		return movieService.getAwardsinterval();
	}

}
