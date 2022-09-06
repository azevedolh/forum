package br.com.alura.forum.dto;

public class TokenDto {

	private String tipo;
	private String token;

	public TokenDto(String tipo, String token) {
		this.tipo = tipo;
		this.token = token;
	}

	public TokenDto() {
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
