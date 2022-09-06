package br.com.alura.forum.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.Resposta;

public class RespostaDto {

	private Long id;
	private String mensagem;
	private LocalDateTime dataCriacao;

	public RespostaDto(Resposta resposta) {
		this.id = resposta.getId();
		this.mensagem = resposta.getMensagem();
		this.dataCriacao = resposta.getDataCriacao();
	}

	public Long getId() {
		return id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public static List<RespostaDto> converter(List<Resposta> respostas) {
		return respostas.stream().map(RespostaDto::new).collect(Collectors.toList());
	}

}
