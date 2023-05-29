package com.movies.oscar.service;

import com.movies.oscar.dto.*;
import com.movies.oscar.entity.Movie;
import com.movies.oscar.entity.MovieProducer;
import com.movies.oscar.repository.MovieProducersRepository;
import com.movies.oscar.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private MovieProducersRepository movieProducersRepository;

	public List<MoviesDto> getMovies() {
		var movieList = movieRepository.findAll();

		return movieList.stream().map(this::mapMoviesYearDto).toList();
	}

	public List<YearsMoreWinnersDto> getYearsWinners() {

		var movieList = movieRepository.findYearsWinners();

		return movieList.stream().filter(m -> m.getYear_movie() > 0).toList();
	}

	public List<MoviesDto> getYear(Integer year) {
		var movieList = movieRepository.findByYear(year);

		return movieList.stream().map(this::mapMoviesYearDto).toList();
	}

	private MoviesDto mapMoviesYearDto(Movie movie) {
		var moviesYearDto = new MoviesDto();

		var movieProducer = (movie.getMovieProducer().stream().map(mp -> {
			return new IdDescricaoDto(mp.getProducer().getId(), mp.getProducer().getName());
		})).toList();

		var movieStudio = (movie.getMovieStudio().stream().map(ms -> {
			return new IdDescricaoDto(ms.getStudio().getId(), ms.getStudio().getName());
		})).toList();

		moviesYearDto.setProducers(movieProducer);
		moviesYearDto.setStudios(movieStudio);
		moviesYearDto.setTitle(movie.getTitle());
		moviesYearDto.setAno(movie.getYear());
		moviesYearDto.setWinner(movie.getWinner());

		return moviesYearDto;
	}

	public AwardsIntervalDto getAwardsinterval() {

		// Pesquisa todos os Produtores que ganharam prêmios, ordenando por Produtor
		var producerListMap = movieProducersRepository.findByMovieProducerWinner(true)
				.stream().collect(Collectors.groupingBy(MovieProducer::getProducer));

		// Remove os Produtores que ganharam prêmio apenas uma vez
		var producerAward = producerListMap.entrySet().stream()
				.filter(awards -> {
							return awards.getValue().stream().toList().stream().map(MovieProducer::getMovieProducer).mapToInt(Movie::getYear).min().orElse(0)
									!= awards.getValue().stream().toList().stream().map(MovieProducer::getMovieProducer).mapToInt(Movie::getYear).max().orElse(0);
						}
				)
				.map(entry -> {
					var producer = entry.getKey().getName();
					var menorAno = entry.getValue().stream().toList().stream().map(MovieProducer::getMovieProducer).mapToInt(Movie::getYear).min().orElse(0);
					var maiorAno = entry.getValue().stream().toList().stream().map(MovieProducer::getMovieProducer).mapToInt(Movie::getYear).max().orElse(0);
					return new AwardDto(producer, (maiorAno - menorAno), menorAno, maiorAno);

				}).toList();

		// Pega os Produtores com menor intervalo dos prêmios
		var qtLessAwards = producerAward.stream().mapToLong(AwardDto::getInterval).min().orElse(0);
		var moviesLessAwards = producerAward.stream().filter(m -> m.getInterval() == qtLessAwards).toList();

		// Pega os Produtores com maior intervalo dos prêmios
		var qtMoreAwards = producerAward.stream().mapToLong(AwardDto::getInterval).max().orElse(0);
		var moviesMoreAwards = producerAward.stream().filter(m -> m.getInterval() == qtMoreAwards).toList();

		// Lista com o menor intervalo
		var awardMinList = moviesLessAwards.stream()
				.map(awardDto -> new AwardDto(awardDto.getProducer(), awardDto.getInterval(), awardDto.getPreviousWin(), awardDto.getFollowingWin()))
				.toList();

		// Lista com o maior intervalo
		var awardMaxList = moviesMoreAwards.stream()
				.map(awardDto -> new AwardDto(awardDto.getProducer(), awardDto.getInterval(), awardDto.getPreviousWin(), awardDto.getFollowingWin()))
				.toList();

		var awardsInterval = new AwardsIntervalDto();
		awardsInterval.setMin(awardMinList);
		awardsInterval.setMax(awardMaxList);

		return awardsInterval;

	}

}
