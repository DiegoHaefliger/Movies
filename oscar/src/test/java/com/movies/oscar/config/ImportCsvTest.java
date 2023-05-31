package com.movies.oscar.config;

import com.movies.oscar.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ImportCsvTest {

	private static final String pathCsv = "src/main/resources/file/test.csv";

	@Mock
	private MovieRepository movieRepository;
	@Mock
	private StudioRepository studioRepository;
	@Mock
	private ProducersRepository producersRepository;
	@Mock
	private MovieProducersRepository movieProducersRepository;
	@Mock
	private MovieStudioRepository movieStudioRepository;
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private ImportCsv importCsv;

	@Nested
	class ValidarMetodosDoRepository {

		@Test
		@DisplayName("Deve excluir todos os registros do banco de dados")
		void deveExcluirRegistrosNoBanco() {
			importCsv.deleteRegistros();

			verify(movieStudioRepository).deleteAll();
			verify(movieProducersRepository).deleteAll();
			verify(studioRepository).deleteAll();
			verify(producersRepository).deleteAll();
			verify(movieRepository).deleteAll();
			verify(userRepository).deleteAll();

		}

		@Test
		@DisplayName("Deve inserir usuárui no banco de dados")
		void deveInserirUsuarioNoBanco() {
			importCsv.insertUser();

			verify(userRepository).save(any());
		}

		@Test
		void deve() throws IOException {
			criaCsv();
			importCsv.importCsv(pathCsv);
		}

		private void criaCsv() throws IOException {
			var separador = ";";

			try {
				FileWriter writer = new FileWriter(pathCsv);

				List<String> linhas = new ArrayList<String>();
				linhas.add("year" + separador + "title" + separador + "studios" + separador + "producers" + separador + "winner");
				linhas.add("2000" + separador + "Little Nicky" + separador + "New Line Cinema" + separador + "Jack Giarraputo and Robert Simonds");
				linhas.add("2000" + separador + "Battlefield Earth" + separador + "Warner Bros., Franchise Pictures" + separador +
						"Jonathan D. Krane, Elie " + "Samaha and John Travolta" + separador + "yes");
				linhas.add("2000" + separador + "Book of Shadows: Blair Witch 2" + separador + "Artisan Entertainment" + separador + "Bill Carraro");
				linhas.add("2000" + separador + "The Flintstones in Viva Rock Vegas" + separador + "Universal Studios" + separador + "Bruce Cohen");
				linhas.add("2000" + separador + "Little Nicky" + separador + "New Line Cinema" + separador + "Jack Giarraputo and Robert Simonds");
				linhas.add("2000" + separador + "The Next Best Thing" + separador + "Paramount Pictures" + separador +
						"Leslie Dixon, Linne Radmin and Tom " + "Rosenberg");
				linhas.add("2001" + separador + "Freddy Got Fingered" + separador + "20th Century Fox" + separador +
						"Larry Brezner, Howard Lapides and Lauren " + "Lloyd" + separador + "yes");
				linhas.add("2001" + separador + "Driven" + separador + "Warner Bros., Franchise Pictures" + separador +
						"Renny Harlin, Elie Samaha and Sylvester " + "Stallone");
				linhas.add(
						"2001" + separador + "Glitter" + separador + "20th Century Fox, Columbia Pictures" + separador + "Laurence Mark and E. Bennett Walsh");
				linhas.add("2001" + separador + "Pearl Harbor" + separador + "Touchstone Pictures" + separador + "Michael Bay and Jerry Bruckheimer");
				linhas.add("2001" + separador + "3000 Miles to Graceland" + separador + "Warner Bros., Franchise Pictures" + separador +
						"Demian Lichtenstein, Eric Manes, Elie Samaha, Richard Spero and Andrew Stevens" + separador + "yes");

				writer.write(unicaLinha(linhas));

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private String unicaLinha(List<String> linha) {
			var unicaLinha = "";
			for (String l : linha) {
				unicaLinha += l + "\n";
			}
			return unicaLinha;
		}

	}

	@Test
	void removeRegistrosDuplicadoNaLista() {
	}

}