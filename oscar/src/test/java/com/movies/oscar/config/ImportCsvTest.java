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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
	class ValidarMetodosDoImportCsv {

		@Test
		@DisplayName("Deve excluir todos os registros do banco de dados")
		void deveExcluirRegistrosNoBanco() {
			importCsv.deleteRegistros();

			// testa se passou pelos métodos delete dos repositórios
			verify(movieStudioRepository).deleteAll();
			verify(movieProducersRepository).deleteAll();
			verify(studioRepository).deleteAll();
			verify(producersRepository).deleteAll();
			verify(movieRepository).deleteAll();
			verify(userRepository).deleteAll();
		}

		@Test
		@DisplayName("Deve inserir usuário no banco de dados")
		void deveInserirUsuarioNoBanco() {
			importCsv.insertUser();

			// testa se passou pelo método save do repositório
			verify(userRepository).save(any());
		}

		@Test
		@DisplayName("Deve importar arquivo CSV com base na lista de filmes gerados no método (criaCsv) ")
		void deveImportarArquivoCsv() throws IOException {
			criaCsv();
			importCsv.importCsv(pathCsv);

			// TODOS OS TESTES ABAIXO FORAM VALIDADOS COM BASE NO ARQUIVO GERADO NO MÉTODO CRIACSV

			// testa quantas vezes passou no método saveAll e save
			verify(studioRepository, times(1)).saveAll(any());
			verify(producersRepository, times(1)).saveAll(any());
			verify(movieRepository, times(11)).save(any());
			verify(movieStudioRepository, times(15)).save(any());
			verify(movieProducersRepository, times(27)).save(any());
		}

		@Test
		@DisplayName("Deve validar método responsável por separar os estúdios")
		void deveRetornaEstudios() {
			var studios = "20th Century Fox, Columbia Pictures, New Line Cinema";
			var retorno = importCsv.splitStudio(studios);

			assertEquals("20th Century Fox", retorno[0]);
			assertEquals("Columbia Pictures", retorno[1]);
			assertEquals("New Line Cinema", retorno[2]);
		}

		@Test
		@DisplayName("Deve validar método responsável por separar os produtores")
		void deveRetornaProdutores() {
			var producetors = "Demian Lichtenstein, Eric Manes, Elie Samaha, Richard Spero and Andrew Stevens";
			var retorno = importCsv.splitProducer(producetors);

			assertEquals("Demian Lichtenstein", retorno[0]);
			assertEquals("Eric Manes", retorno[1]);
			assertEquals("Elie Samaha", retorno[2]);
			assertEquals("Richard Spero", retorno[3]);
			assertEquals("Andrew Stevens", retorno[4]);
		}

		@Test
		@DisplayName("Deve remover registros duplicados")
		void deveRemoverRegistrosDuplicados() {
			var listStudios = List.of("20th Century Fox", "Columbia Pictures", "New Line Cinema", "Columbia Pictures");
			var retorno = importCsv.removeRegistrosDuplicadoNaLista(listStudios);
			assertEquals(3, retorno.size());
		}
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