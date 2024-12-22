package com.antonio.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.antonio.lojavirtual.controller.PessoaController;
import com.antonio.lojavirtual.enums.TipoEndereco;
import com.antonio.lojavirtual.model.Endereco;
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
		
		Endereco endereco1 = new Endereco();
		endereco1.setBairro("Jd Dias");
		endereco1.setCep("55555-555");
		endereco1.setComplemento("casa cinza");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setNumero("333");
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setRuaLogradouro("av sao joao sexto");
		endereco1.setTipoEndereco(TipoEndereco.COBRANCA);
		endereco1.setUf("sp");
		endereco1.setCidade("sao paulo");
		
		Endereco endereco2 = new Endereco();
		endereco2.setBairro("Jd Maraca");
		endereco2.setCep("66666-555");
		endereco2.setComplemento("casa 2");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setNumero("334");
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setRuaLogradouro("av pio XII");
		endereco2.setTipoEndereco(TipoEndereco.ENTREGA);
		endereco2.setUf("sp");
		endereco2.setCidade("sao paulo");
		
		pessoaJuridica.getEnderecos().add(endereco2);
		pessoaJuridica.getEnderecos().add(endereco1);
		
		pessoaJuridica = pessoaController.salvarPj(pessoaJuridica).getBody();
		
		assertEquals(true, pessoaJuridica.getId() > 0);
		
		for (Endereco endereco : pessoaJuridica.getEnderecos()) {
			
			assertEquals(true, endereco.getId() > 0);
		}
		
		assertEquals(2, pessoaJuridica.getEnderecos().size());
		
	}

}
