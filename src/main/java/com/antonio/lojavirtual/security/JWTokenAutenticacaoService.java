package com.antonio.lojavirtual.security;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletResponse;

/** CRIAR AUTENTICACAO E RETORNAR A AUTENTICACAO JWT **/
@Service
@Component
public class JWTokenAutenticacaoService {
	
	/** TOKEN DE VALIDADE DE 11 DIAS **/
	private static final long EXPIRATION_TIME = 959990000;
	
	/** CHAVE DE SENHA PARA JUNTAR COM JWT **/
	private static final String SECRET = "ss/-*-*sds565dsd-s/d-s*dsds";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HEADER_STRING = "Authorization";
	
	/** GERA O TOKEN E DA RESPOSTA PARA O CLIENTE COM JWT**/
	public void addAutentication (HttpServletResponse response, String username) throws Exception{
		
		/** MONTAGEM DO TOKEN **/
		String JWT = Jwts.builder().  /** CHAMA O GERADOR DE TOKEN **/
				setSubject(username).  /** ADICIONA O USER **/
				setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /** TEMPO DE EXPIRACAO **/
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		
		
		String token = TOKEN_PREFIX + " " + JWT;
		
		/** DAR A RESPOSTA PARA A TELA E PARA O CLIENTE, OUTRA API, NAVEGADOR, APLICATIVO, JAVASCRIPT, OUTRA CHAMADA JAVA **/
		response.addHeader(HEADER_STRING, token);
		
		
		/** USADO PARA VER NO POSTMAN PARA TESTE **/
		response.getWriter().write("{\"Authorization\":\"" + token + "\"}");
	}

}
