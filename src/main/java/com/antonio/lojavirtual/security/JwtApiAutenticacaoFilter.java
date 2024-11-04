package com.antonio.lojavirtual.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/** FILTRA ONDE TODAS AS REQUISICOES SERÃO CAPTURADAS PARA AUTENTICAR **/
public class JwtApiAutenticacaoFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		/** ESTABECE A AUTTENTICACAO DO USER **/
		Authentication authentication = new JWTokenAutenticacaoService()
				.getAuthentication((HttpServletRequest)request, (HttpServletResponse)response);
		
		/** COLOCA O PROCESSO DE AUTENTICACAO PARA O SPRING SECURITY **/
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(request, response);
	}

}
