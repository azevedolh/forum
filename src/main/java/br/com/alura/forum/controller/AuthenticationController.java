package br.com.alura.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.dto.TokenDto;
import br.com.alura.forum.form.AuthForm;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid AuthForm authForm) {
		UsernamePasswordAuthenticationToken autenticacao = authForm.converter();
		
		try {
			Authentication authentication = authManager.authenticate(autenticacao);
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.ok(new TokenDto("Bearer", token));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
