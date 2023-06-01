package com.movies.oscar.service;

import com.movies.oscar.dto.YearsMoreWinnersDto;
import com.movies.oscar.entity.*;
import com.movies.oscar.repository.MovieProducersRepository;
import com.movies.oscar.repository.MovieRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MovieServiceTest {

	@Mock
	private MovieRepository movieRepository;

	@Mock
	private MovieProducersRepository movieProducersRepository;

	@InjectMocks
	private MovieService movieService;

	@Nested
	class ValidarMetodosDaMovieService {

		@Test
		@DisplayName("Deve retornar uma lista de todos os filmes")
		void deveRetornarTodosOsFilmes() {
			var movie = getMovie();

			when(movieRepository.findAll()).thenReturn(List.of(movie));
			var retorno = movieService.getMovies();

			assertEquals(movie.getYear(), retorno.get(0).getAno());
			assertEquals(movie.getTitle(), retorno.get(0).getTitle());
			assertEquals(movie.getWinner(), retorno.get(0).getWinner());
			assertEquals(movie.getMovieStudio().get(0).getStudio().getId(), retorno.get(0).getStudios().get(0).getId());
			assertEquals(movie.getMovieStudio().get(0).getStudio().getName(), retorno.get(0).getStudios().get(0).getDescricao());
		}

		@Test
		@DisplayName("Deve retornar uma lista de todos os filmes lançados no ano informado")
		void deveRetornarTodosOsFilmesLancadosNoAnoInformado() {
			var movie = getMovie();

			when(movieRepository.findByYear(any())).thenReturn(List.of(movie));
			var retorno = movieService.getYear(any());

			assertEquals(movie.getYear(), retorno.get(0).getAno());
			assertEquals(movie.getTitle(), retorno.get(0).getTitle());
			assertEquals(movie.getWinner(), retorno.get(0).getWinner());
			assertEquals(movie.getMovieStudio().get(0).getStudio().getId(), retorno.get(0).getStudios().get(0).getId());
			assertEquals(movie.getMovieStudio().get(0).getStudio().getName(), retorno.get(0).getStudios().get(0).getDescricao());
		}

		@Test
		@DisplayName("Deve retornar uma lista informando o total de ganhadores de prêmios por ano")
		void deveRetornaTotalDeGanhadoresPorAno() {
			var yearsMoreWinners = getYearsMoreWinnersDto();

			when(movieRepository.findYearsWinners()).thenReturn(yearsMoreWinners);
			var retorno = movieService.getYearsWinners();

			assertEquals(yearsMoreWinners.get(0).getYear_movie(), retorno.get(0).getYear_movie());
			assertEquals(yearsMoreWinners.get(0).getWins(), retorno.get(0).getWins());

			assertEquals(yearsMoreWinners.get(1).getYear_movie(), retorno.get(1).getYear_movie());
			assertEquals(yearsMoreWinners.get(1).getWins(), retorno.get(1).getWins());

			assertEquals(yearsMoreWinners.get(2).getYear_movie(), retorno.get(2).getYear_movie());
			assertEquals(yearsMoreWinners.get(2).getWins(), retorno.get(2).getWins());
		}

		@Test
		@DisplayName("Deve retornar uma lista informando os Produtor com menor e maior intervalos entre prêmios")
		void deveRetornarProdutoresComMenorEMaiorIntervaloEntrePremios() {
			var movieProducers = getMovieProducerInterval();

			when(movieProducersRepository.findByMovieProducerWinner(true)).thenReturn(movieProducers);

			var retorno = movieService.getAwardsinterval();

			assertEquals("Jerry Weintraub", retorno.getMin().get(0).getProducer());
			assertEquals(1, retorno.getMin().get(0).getInterval());
			assertEquals(2000, retorno.getMin().get(0).getPreviousWin());
			assertEquals(2001, retorno.getMin().get(0).getFollowingWin());

			assertEquals("Roland Emmerich", retorno.getMax().get(0).getProducer());
			assertEquals(10, retorno.getMax().get(0).getInterval());
			assertEquals(1998, retorno.getMax().get(0).getPreviousWin());
			assertEquals(2008, retorno.getMax().get(0).getFollowingWin());
		}

	}

	private String getNameStudio() {
		return "Associated Film Distribution";
	}

	private String getNameProducer() {
		return "Jerry Weintraub";
	}

	private List<MovieStudio> getMovieStudio() {
		var movieStudio = new MovieStudio();

		movieStudio.setId(1);
		movieStudio.setStudio(getStudio());
		return List.of(movieStudio);
	}

	private Producer getProducer() {
		var producer = new Producer();

		producer.setId(1);
		producer.setName(getNameProducer());
		return producer;
	}

	private List<MovieProducer> getMovieProducer() {
		var movieProducer = new MovieProducer();

		movieProducer.setId(1);
		movieProducer.setProducer(getProducer());
		return List.of(movieProducer);
	}

	private Studio getStudio() {
		var studio = new Studio();

		studio.setId(1);
		studio.setName(getNameStudio());
		return studio;
	}

	private Movie getMovie() {
		var movie = new Movie();

		movie.setId(1);
		movie.setWinner(true);
		movie.setYear(2000);
		movie.setTitle("Battlefield Earth");
		movie.setMovieStudio(getMovieStudio());
		movie.setMovieProducer(getMovieProducer());

		return movie;
	}

	private List<YearsMoreWinnersDto> getYearsMoreWinnersDto() {
		var yearsMoreWinnersDto1 = new YearsMoreWinnersDto(2000, 1);
		var yearsMoreWinnersDto2 = new YearsMoreWinnersDto(2001, 1);
		var yearsMoreWinnersDto3 = new YearsMoreWinnersDto(2001, 2);

		return List.of(yearsMoreWinnersDto1, yearsMoreWinnersDto2, yearsMoreWinnersDto3);
	}

	private List<MovieProducer> getMovieProducerInterval() {

		var movie1 = new Movie();
		movie1.setId(1);
		movie1.setWinner(true);
		movie1.setYear(2000);
		movie1.setTitle("Battlefield Earth");

		var producer1 = new Producer();
		producer1.setId(1);
		producer1.setName("Jerry Weintraub");

		var movieProducer1 = new MovieProducer();
		movieProducer1.setId(1);
		movieProducer1.setProducer(producer1);
		movieProducer1.setMovieProducer(movie1);

		var movie2 = new Movie();
		movie2.setId(2);
		movie2.setWinner(true);
		movie2.setYear(2001);
		movie2.setTitle("Freddy Got Fingered");

		var movieProducer2 = new MovieProducer();
		movieProducer2.setId(2);
		movieProducer2.setProducer(producer1);
		movieProducer2.setMovieProducer(movie2);

		var movie3 = new Movie();
		movie3.setId(3);
		movie3.setWinner(true);
		movie3.setYear(1998);
		movie3.setTitle("Godzilla");

		var producer3 = new Producer();
		producer3.setId(3);
		producer3.setName("Roland Emmerich");

		var movieProducer3 = new MovieProducer();
		movieProducer3.setId(3);
		movieProducer3.setProducer(producer3);
		movieProducer3.setMovieProducer(movie3);

		var movie4 = new Movie();
		movie4.setId(4);
		movie4.setWinner(true);
		movie4.setYear(2008);
		movie4.setTitle("The Happening");

		var movieProducer4 = new MovieProducer();
		movieProducer4.setId(4);
		movieProducer4.setProducer(producer3);
		movieProducer4.setMovieProducer(movie4);

		return List.of(movieProducer1, movieProducer2, movieProducer3, movieProducer4);
	}

}