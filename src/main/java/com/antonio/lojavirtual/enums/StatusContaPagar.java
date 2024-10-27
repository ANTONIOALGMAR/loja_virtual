package com.antonio.lojavirtual.enums;

public enum StatusContaPagar {
	COBRANCA("Pagar"),
	VENCIDA("Vencida"),
	ABERTA("Aberta"),
	QUITADA("Quitada"),
	NEGOCIADA("Renegociada"),
	FUNCIONARIO("Funcionário"),
	ALUGUEL("Aluguel");
	
	
	private String descricao;

	private StatusContaPagar(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return this.getDescricao();
	}
}
