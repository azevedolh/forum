package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	public AuthenticationTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperarToken(request);
		
		System.out.println("token recuperado do request: " + token);

		Boolean tokenValido = tokenService.validarTokenJwt(token);
		
		System.out.println("tokenValido: " + tokenValido);
		
		if (tokenValido) {
			validarTokenNoSpring(token);
		}

		filterChain.doFilter(request, response);
	}

	private void validarTokenNoSpring(String token) {
		Long usuarioId = tokenService.recuperarUsuario(token);
		Usuario usuario = usuarioRepository.findById(usuarioId).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recuperarToken(HttpServletRequest request) {

		String authorization = request.getHeader("Authorization");

		if (authorization == null || authorization.isEmpty() || !authorization.startsWith("Bearer ")) {
			return null;
		}

		return authorization.substring(7, authorization.length());
	}

}
