package com.movies.oscar.config;

import com.movies.oscar.entity.*;
import com.movies.oscar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ImportCsv {

	private static final String pathCsv = "src/main/resources/file/movielist.csv";

	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private StudioRepository studioRepository;
	@Autowired
	private ProducersRepository producersRepository;
	@Autowired
	private MovieProducersRepository movieProducersRepository;
	@Autowired
	private MovieStudioRepository movieStudioRepository;

	@Bean
	public void importCsvMovies() throws IOException {
		deleteRegistros();
		importCsv();
	}

	private void deleteRegistros() {
		System.out.println("--- DELETA REGISTROS EXISTENTES DO BANCO ---");
		movieStudioRepository.deleteAll();
		movieProducersRepository.deleteAll();
		studioRepository.deleteAll();
		producersRepository.deleteAll();
		movieRepository.deleteAll();
	}

	@Transactional
	private void importCsv() {
		System.out.println("--- IMPORTA REGISTROS DO ARQUIVO PARA O BANCO ---");

		var arquivoCSV = new File(pathCsv);
		var movieList = new ArrayList<Movie>();
		var studioList = new ArrayList<Studio>();
		var producersList = new ArrayList<Producer>();

		List<String> producerTempList = new ArrayList<>();
		List<String> studioTempList = new ArrayList<>();

		// Na primeira vez vai salvar os registros de Studio e Producer
		try (Scanner scan = new Scanner(arquivoCSV)) {
			if (!scan.hasNextLine()) {
				return; // testa se existe o cabeçalho no arquivo
			}

			scan.nextLine();

			while (scan.hasNext()) {
				String linha = scan.nextLine();
				String[] valores = linha.split(";");
				if (valores[0].length() > 0) {
					String[] studios = replaceStudio(valores[2]);
					String[] producers = replaceProducer(valores[3]);

					for (int i = 0; i < studios.length; i++) {
						studioTempList.add(studios[i].toString().trim());
					}
					for (int i = 0; i < producers.length; i++) {
						producerTempList.add(producers[i].toString());
					}
				}
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// remove os Studios duplicados e salva no banco
		var studiosNaoDuplicado = removeRegistrosDuplicadoNaLista(studioTempList);
		for (int i = 0; i < studiosNaoDuplicado.size(); i++) {
			studioList.add(new Studio(studiosNaoDuplicado.get(i)));
		}
		var studioListRepository = studioRepository.saveAll(studioList);

		// remove os Producers duplicados e salva no banco
		var producerNaoDuplicado = removeRegistrosDuplicadoNaLista(producerTempList);
		for (int i = 0; i < producerNaoDuplicado.size(); i++) {
			producersList.add(new Producer(producerNaoDuplicado.get(i)));
		}
		var producersListRepository = producersRepository.saveAll(producersList);

		// Na segunda vez vai salvar os registros de Movies
		try (Scanner scan = new Scanner(arquivoCSV)) {
			if (!scan.hasNextLine()) {
				return; // testa se existe o cabeçalho no arquivo
			}
			scan.nextLine();

			while (scan.hasNext()) {
				String linha = scan.nextLine();
				String[] valores = linha.split(";");
				if (valores[0].length() > 0) {
					var year = Integer.parseInt(valores[0]);
					var title = valores[1];
					String[] studios = replaceStudio(valores[2]);
					String[] producers = replaceProducer(valores[3]);
					var winner = false;

					if (valores.length > 4 && valores[4] != null) {
						winner = true;
					}

					// Salva no Movie
					var movie = new Movie(year, title, winner);
					var newMovie = movieRepository.save(movie);

					// filtra os Studios e salva no Movies
					for (int studio = 0; studio < studios.length; studio++) {
						var nomeStudio = studios[studio];
						var studiofilter = studioListRepository.stream().filter(s -> s.getName().equals(nomeStudio)).findFirst().orElse(null);
						var moveStudio = new MovieStudio(newMovie, studiofilter);
						movieStudioRepository.save(moveStudio);
					}

					// filtra os Producers e salva no Movies
					for (int producer = 0; producer < producers.length; producer++) {
						var nomeProducer = producers[producer];
						var producerfilter = producersListRepository.stream().filter(s -> s.getName().equals(nomeProducer)).findFirst().orElse(null);
						var moveProcedure = new MovieProducer(newMovie, producerfilter);
						movieProducersRepository.save(moveProcedure);
					}
				}
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String[] replaceStudio(String valores) {
		return valores.replace(", ", ",").split(",");
	}

	private String[] replaceProducer(String valores) {
		return valores.replace(" and ", ",").replace(", ", ",").split(",");
	}

	private List<String> removeRegistrosDuplicadoNaLista(List<String> list) {
		Set<String> set = new LinkedHashSet<>(list);
		List<String> listWithoutDuplicates = new ArrayList<>(set);
		listWithoutDuplicates.removeAll(Arrays.asList("", null));

		return listWithoutDuplicates;
	}

}

