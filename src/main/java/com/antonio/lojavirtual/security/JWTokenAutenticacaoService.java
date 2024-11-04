package com.antonio.lojavirtual.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.antonio.lojavirtual.ApplicationContextLoad;
import com.antonio.lojavirtual.model.Usuario;
import com.antonio.lojavirtual.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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
		
		/** EXE: bearer  - / asdassdasdadsdadsadadssads.asdaekekedkaekkeakkekeka.ssjsjsjdhhsdgsgdgsgdgsvv **/
		String token = TOKEN_PREFIX + " " + JWT;
		
		/** DAR A RESPOSTA PARA A TELA E PARA O CLIENTE, OUTRA API, NAVEGADOR, APLICATIVO, JAVASCRIPT, OUTRA CHAMADA JAVA **/
		response.addHeader(HEADER_STRING, token);
		
		liberacaoCors(response);
		
		/** USADO PARA VER NO POSTMAN PARA TESTE **/
		response.getWriter().write("{\"Authorization\":\"" + token + "\"}");
	}
	
	/** RETORNA O USUARIO VALIDADO COM TOKEN OU CASO NAO SEJA VALIDO RETORNA NULL**/
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
		
		String token = request.getHeader(HEADER_STRING);
		
		if (token != null) {
			
			String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();
			
			/** FAZ A VALIDACAO DO TOKEN DO USUARIO NA REQUISICAO E OBTEM O USER **/
			String user = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(tokenLimpo)
					.getBody().getSubject(); /** PEGA O USUARIO EX: ADMIN / ALEX **/
			
		if (user != null){
			
			Usuario usuario = ApplicationContextLoad
					.getApplicationContext()
					.getBean(UsuarioRepository.class).findUserByLogin(user);
			if (usuario != null) {
				
				return new UsernamePasswordAuthenticationToken(
						usuario.getLogin(), 
						usuario.getSenha(), 
						usuario.getAuthorities());
				
			}
			
		}	
		
		
		}
		
		liberacaoCors(response); 
		return null;
	}
	
	
	/** FAZENDO LIBERACAO CONTRA ERRO DE COrs NO NAVEGADOR **/
	private void liberacaoCors(HttpServletResponse response) {
		
		if(response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}
	
	
		if(response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", "*");
		}
	
	
		if(response.getHeader("Access-Control-Request-Headers") == null) {
			response.addHeader("Access-Control-Request-Headers", "*");
		}
	
	
		if(response.getHeader("Access-Control-Allow-Methods") == null) {
			response.addHeader("Access-Control-Allow-Methods", "*");
		}
	}

}
