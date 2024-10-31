package com.antonio.lojavirtual;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.antonio.lojavirtual.controller.AcessoController;
import com.antonio.lojavirtual.model.Acesso;
import com.antonio.lojavirtual.repository.AcessoRepository;

@SpringBootTest(classes = LojaVirtualApplication.class)
public class LojaVirtualApplicationTests {
	
	
	
	@Autowired
	private AcessoController acessoController;
	
	
	@Autowired
	private AcessoRepository acessoRepository;
	

	@Test
	public void testeCadastraAcesso() {
		
		// EXECUTA O METODO DE SALVAR ACESSO
		Acesso acesso = new Acesso();
		acesso.setDescricao("ROLE_ADMIN");
		
		assertEquals(true, acesso.getId() == null);
		
		/** GRAVOU NO BANCO DE DADOS **/
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		assertEquals(true, acesso.getId() > 0);
		
		
		/** VALIDAR DADOS SALVO DE FORMA CORRETA **/
		assertEquals("ROLE_ADMIN", acesso.getDescricao());
		
		
		/** TESTE DE CARREGAMENTO **/
		Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
		
		assertEquals(acesso.getId(), acesso2.getId());
		
		
		/** TESTE DE DELETE **/
		acessoRepository.deleteById(acesso2.getId());
		acessoRepository.flush(); /* RODA ESSE SQL DE DELETE NO BANCO DE DADOS */
		
		Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null);
		
		assertEquals(true, acesso3 == null);
		
		
		
		/** TESTE DE QUERY **/
		
		acesso = new Acesso();
		
		acesso.setDescricao("ROLE_ALUNO");
		
		acesso = acessoController.salvarAcesso(acesso).getBody();
		
		List<Acesso> acessos = acessoRepository.buscarAcessoDesc("ALUNO".trim().toUpperCase());
		
		assertEquals(1, acessos.size());
		
		acessoRepository.deleteById(acesso.getId());
		
		
	}

}
