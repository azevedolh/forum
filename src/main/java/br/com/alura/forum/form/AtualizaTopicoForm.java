package br.com.alura.forum.form;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

public class AtualizaTopicoForm {

	@NotBlank
	private String titulo;

	@NotBlank
	private String mensagem;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Topico atualizar(Long id, TopicoRepository topicoRepository) {
		Optional<Topico> optional = topicoRepository.findById(id);

		if (!optional.isPresent()) {
			return null;
		}

		Topico topico = optional.get();
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		topicoRepository.save(topico);
		return topico;
	}

}
