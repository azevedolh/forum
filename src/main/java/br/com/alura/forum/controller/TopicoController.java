package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.dto.DetalheTopicoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.AtualizaTopicoForm;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topico")
public class TopicoController {

	@Autowired
	TopicoRepository topicoRepository;

	@Autowired
	CursoRepository cursoRepository;

	@GetMapping()
	@Cacheable("listarTopicos")
	public ResponseEntity<Page<TopicoDto>> listarTopicos(@RequestParam(required = false) String nomeCurso,
			@PageableDefault(page = 0, size = 10, direction = Direction.ASC, sort = "id") Pageable paginacao) {

		Page<Topico> topicos;

		if (nomeCurso == null) {
			topicos = topicoRepository.findAll(paginacao);
		} else {
			topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
		}

		return ResponseEntity.ok().body(TopicoDto.converter(topicos));
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "listarTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> incluirTopico(@RequestBody @Valid TopicoForm topicoForm,
			UriComponentsBuilder uriBuilder) {
		Topico topico = topicoForm.converter(cursoRepository);
		topicoRepository.save(topico);
		URI uri = uriBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetalheTopicoDto> detalharTopico(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);

		if (!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new DetalheTopicoDto(topico.get()));
	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listarTopicos", allEntries = true)
	public ResponseEntity<TopicoDto> alterarTopico(@RequestBody @Valid AtualizaTopicoForm atualizaTopicoForm,
			@PathVariable Long id) {

		Topico topico = atualizaTopicoForm.atualizar(id, topicoRepository);

		if (topico == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new TopicoDto(topico));
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listarTopicos", allEntries = true)
	public ResponseEntity<?> excluirTopico(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);

		if (!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		topicoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
