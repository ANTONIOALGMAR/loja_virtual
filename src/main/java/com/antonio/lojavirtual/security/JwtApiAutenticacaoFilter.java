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

/** Filtro para capturar e autenticar todas as requisições **/
public class JwtApiAutenticacaoFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
        	
        	// Log para início do filtro
            System.out.println("Iniciando o filtro JWT");
        	
        	
            /** Estabelece a autenticação do usuário **/
            Authentication authentication = new JWTTokenAutenticacaoService()
                    .getAuthentication(httpRequest, httpResponse);
            
         // Log após obter a autenticação
            System.out.println("Autenticação obtida: " + authentication);
            
            
            /** Define a autenticação no contexto do Spring Security **/
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            chain.doFilter(request, response);
            
        } catch (Exception e) {
        	e.printStackTrace();
        	 // Log de erro
            System.out.println("Erro de autenticação no filtro: " + e.getMessage());
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Erro de autenticação: " + e.getMessage());
            return;
        }
    }
}

