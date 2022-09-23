package br.com.alura.forum.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.forum.modelo.Curso;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class CursoRepositoryTest {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	void deveriaRetornarCursoAtravesDoNomeInformado() {
		String nomeCurso = "Spring Boot";
		
		Curso novoCurso = new Curso();
		novoCurso.setNome(nomeCurso);
		novoCurso.setCategoria("Programação");
		em.persist(novoCurso);
		
		Curso curso = cursoRepository.findByNome(nomeCurso);
		System.out.println(curso.toString());
		assertNotNull(curso);
		assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	void naoDeveriaRetornarCursoQuandoNomeNaoExisteNaBase() {
		String nomeCurso = "JPA";
		Curso curso = cursoRepository.findByNome(nomeCurso);
		assertNull(curso);
	}

}
