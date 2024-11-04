package com.antonio.lojavirtual.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name= "pessoa_fisica")
public class PessoaFisica extends Pessoa{

	private static final long serialVersionUID = 1L;
	
	@Column (nullable = false)
	private String cpf;
	
	@Temporal(TemporalType.DATE)
	private Date data_nascimento;
	
	@Column(nullable = false)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}
	
	
	

}
