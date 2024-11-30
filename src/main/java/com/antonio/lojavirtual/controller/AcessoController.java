package com.antonio.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.antonio.lojavirtual.ExceptionMentoriaJava;
import com.antonio.lojavirtual.model.Acesso;
import com.antonio.lojavirtual.repository.AcessoRepository;
import com.antonio.lojavirtual.service.AcessoService;

// @CrossOrigin(origins = "www.https://jdevtreinamentos.com.br")
@Controller
@RestController
public class AcessoController {
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@ResponseBody /*** PODER DAR UM RETORNO DA API ***/
	@PostMapping("/salvarAcesso") /*** MAPEANDO A URL PARA RECEBER JSON 
	 * @throws ExceptionMentoriaJava ***/
	public ResponseEntity <Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoriaJava {  /*** RECEBE O JSON E CONVERTE PARA OBJETO ***/
		
		if (acesso.getId() == null) {
			
			List<Acesso> acessos = acessoRepository.buscarAcessoDesc(acesso.getDescricao().toUpperCase());
			
			if (!acessos.isEmpty()) {
				throw new ExceptionMentoriaJava("Já existe acesseo  com a descrição: " + acesso.getDescricao());
			}
		}
		
		Acesso acessoSalvo = acessoService.save(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
		
	}
	
	@ResponseBody /*** PODER DAR UM RETORNO DA API ***/
	@PostMapping("/deleteAcesso") /*** MAPEANDO A URL PARA RECEBER JSON ***/
	public ResponseEntity <?> deleteAcesso(@RequestBody Acesso acesso) {  /*** RECEBE O JSON E CONVERTE PARA OBJETO ***/
		
		acessoRepository.deleteById(acesso.getId());
		
		return new ResponseEntity("Acesso Removido",HttpStatus.OK);
		
	}
	
	// @Secured({ "ROLE_GERENTE", "ROLE_ADMIN" }) /** COM ESSA ANOTAÇÃO SÓ ESSES PODERÃO EXECUTAR O DELETE **/
	@ResponseBody 
	@DeleteMapping("/deleteAcessoPorId/{id}") 
	public ResponseEntity <?> deleteAcessoPorId(@PathVariable("id") Long id) {  
		
		acessoRepository.deleteById(id);
		
		return new ResponseEntity("Acesso Removido",HttpStatus.OK);
		
	}
	
	@ResponseBody 
	@GetMapping("/obterAcesso/{id}") 
	public ResponseEntity <Acesso> obterAcesso(@PathVariable("id") Long id) throws ExceptionMentoriaJava {  
		
		Acesso acesso = acessoRepository.findById(id).orElse(null);
		
		if (acesso == null) {
			throw new ExceptionMentoriaJava("Não encontrou Acesso com codigo: " + id);
		}
		
		return new ResponseEntity<Acesso>(acesso,HttpStatus.OK);
		
	}
	
	@ResponseBody 
	@GetMapping("/buscarPorDesc/{desc}") 
	public ResponseEntity <List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc) {  
		
		List<Acesso> acesso = acessoRepository.buscarAcessoDesc(desc.toUpperCase());

		
		return new ResponseEntity <List<Acesso>>(acesso,HttpStatus.OK);
		
	}

}
