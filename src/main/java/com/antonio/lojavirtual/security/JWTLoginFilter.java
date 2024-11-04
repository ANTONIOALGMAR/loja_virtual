package com.antonio.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.antonio.lojavirtual.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter{

	/** CONFIGURANDO O GERENCIADOR DE AUTENTICACAO **/
	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		
		/** OBRIGA A AUTENTICA A URL **/
		super(new AntPathRequestMatcher(url));
		
		/** GERENCIADOR DE AUTENTICACAO **/
		setAuthenticationManager(authenticationManager);
	}
	

	
	/** RETORNA O USUARIO AO PROCESSAR A AUTENTICACAO **/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		/** OBTEM O USUARIO **/
		Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
		
		/** RETORNA O USER LOGIN E SENHA **/
		return getAuthenticationManager().
				authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		try {
			new JWTokenAutenticacaoService().addAutentication(response, authResult.getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
