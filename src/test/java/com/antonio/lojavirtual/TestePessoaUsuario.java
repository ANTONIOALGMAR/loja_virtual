package com.antonio.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.antonio.lojavirtual.controller.PessoaController;
import com.antonio.lojavirtual.model.PessoaJuridica;

import junit.framework.TestCase;

@Profile("teste")
@SpringBootTest(classes = LojaVirtualApplication.class)
public class TestePessoaUsuario extends TestCase{
	
	
	@Autowired
	private PessoaController pessoaController;
	
	@Test
	public void testCadPessoaFisica () throws ExceptionMentoriaJava {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		
		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setNome("Antonio Torres");
		pessoaJuridica.setEmail("testesalvarpj@gmail.com");
		pessoaJuridica.setTelefone("46693007");
		pessoaJuridica.setInscEstadual("989898898989989");
		pessoaJuridica.setInscMunicipal("676767676767676");
		pessoaJuridica.setNomeFantasia("carvalho informatica");
		pessoaJuridica.setRazaoSocial("antoniocarvalho");
		
		pessoaController.salvarPj(pessoaJuridica);
		
		/*
		PessoaFisica pessoaFisica = new PessoaFisica();
		
		pessoaFisica.setCpf("0597975788");
		pessoaFisica.setNome("Antonio Torres");
		pessoaFisica.setEmail("antonioalgmar@gmail.com");
		pessoaFisica.setTelefone("46693007");
		
		
		pessoaFisica.setEmpresa(pessoaFisica);*/
		
		
	}

}
