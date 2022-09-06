package br.com.alura.forum.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;

public class DetalheTopicoDto {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	private StatusTopico status;
	private String autor;
	private String curso;
	private List<RespostaDto> respostas;

	public DetalheTopicoDto(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
		this.status = topico.getStatus();
		this.autor = topico.getAutor().getNome();
		this.curso = topico.getCurso().getNome();
		this.respostas = RespostaDto.converter(topico.getRespostas());
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public String getAutor() {
		return autor;
	}

	public String getCurso() {
		return curso;
	}

	public List<RespostaDto> getRespostas() {
		return respostas;
	}

}
