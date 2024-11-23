package com.antonio.lojavirtual.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.antonio.lojavirtual.ApplicationContextLoad;
import com.antonio.lojavirtual.model.Usuario;
import com.antonio.lojavirtual.repository.UsuarioRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/*Criar a autenticação e retornar também a autenticação JWT*/
@Service
@Component
public class JWTTokenAutenticacaoService {

    /* Token de validade de 11 dias */
    private static final long EXPIRATION_TIME = 959990000;
    
    /* Chave de senha para assinar o JWT */
    private static final String SECRET = "ss/-*-*sds565dsd-s/d-s*dsds";
    
    private static final String TOKEN_PREFIX = "Bearer";
    
    private static final String HEADER_STRING = "Authorization";
    
    /* Gera o token e retorna na resposta para o cliente com JWT */
    public void addAuthentication(HttpServletResponse response, String username) throws IOException {
        
    	
    	
        /* Montagem do Token */
        String JWT = Jwts.builder()
                .setSubject(username) 
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact(); 
        
        String token = TOKEN_PREFIX + " " + JWT;
        
        System.out.println("Token JWT gerado: " + token);

        
        /* Adiciona o token no cabeçalho da resposta */
        response.addHeader(HEADER_STRING, token);
        
        liberacaoCors(response);
        
        /* Exibe o token no corpo da resposta para verificação no Postman */
        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
        response.flushBuffer();  // Garante o envio imediato da resposta
    }

    
    
    /* Retorna o usuário autenticado com o token, ou null se inválido */
    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String token = request.getHeader(HEADER_STRING);
        
     // Log do token recebido
        System.out.println("Token recebido no cabeçalho: " + token);

        
        try {
            if (token != null && token.startsWith(TOKEN_PREFIX + " ")) {
                
                String tokenLimpo = token.replace(TOKEN_PREFIX + " ", "").trim();
                
                System.out.println("TokenLimpo: " + tokenLimpo); // ver o token antes da validacao
                
                /* Valida o token e obtem o usuário */
                String user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(tokenLimpo)
                        .getBody().getSubject();
                
                
             // Log do usuário autenticado
                System.out.println("Usuário autenticado a partir do token: " + user);

                
                if (user != null) {
                    Usuario usuario = ApplicationContextLoad
                            .getApplicationContext()
                            .getBean(UsuarioRepository.class)
                            .findUserByLogin(user);
                    
                    if (usuario != null) {
                    	
                    	// Log se o usuário foi encontrado no banco de dados
                        System.out.println("Usuário encontrado no banco de dados: " + usuario.getLogin());
                        
                        return new UsernamePasswordAuthenticationToken(
                                usuario.getLogin(),
                                usuario.getSenha(), 
                                usuario.getAuthorities());
                        
                    }else {
                    	System.out.println("Usuário não encontrado no banco de dados.");
                    }
                }
            }else {
            	System.out.println("Token ausente ou não começa com o prefixo correto.");
            }
        } catch (SignatureException e) {
            response.getWriter().write("Token inválido.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } catch (ExpiredJwtException e) {
            response.getWriter().write("Token expirado, efetue o login novamente.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } finally {
            liberacaoCors(response);
        }
        
        return null;
    }
    
   /* 
    /* Configuração para permitir o CORS */
//    private void liberacaoCors(HttpServletResponse response) {
//        
//        if (response.getHeader("Access-Control-Allow-Origin") == null) {
//            response.addHeader("Access-Control-Allow-Origin", "*");
//        }
//        
//        if (response.getHeader("Access-Control-Allow-Headers") == null) {
//            response.addHeader("Access-Control-Allow-Headers", "*");
//        }
//        
//        if (response.getHeader("Access-Control-Request-Headers") == null) {
//            response.addHeader("Access-Control-Request-Headers", "*");
//        }
//        
//        if (response.getHeader("Access-Control-Allow-Methods") == null) {
//            response.addHeader("Access-Control-Allow-Methods", "*");
//        }
//    }
    
    
    private void liberacaoCors(HttpServletResponse response) {
        
        // Permitir apenas domínios específicos, como "https://seusite.com"
        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "https://seusite.com");
        }
        
        // Permitir apenas cabeçalhos específicos
        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        }
        
        // Manter apenas os métodos necessários para o funcionamento da API
        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        }
        
        // Configurar apenas se necessário
        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "Authorization, Content-Type");
        }
    }
}
