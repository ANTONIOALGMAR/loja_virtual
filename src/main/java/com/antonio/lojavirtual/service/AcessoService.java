package com.antonio.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antonio.lojavirtual.model.Acesso;
import com.antonio.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	
	public Acesso save (Acesso acesso) {
		
		/* QUALQUER TIPO DE VALIDACAO */
		
		return acessoRepository.save(acesso);
	}

}
