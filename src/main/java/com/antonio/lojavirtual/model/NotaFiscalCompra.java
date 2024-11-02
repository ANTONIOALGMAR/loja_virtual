package com.antonio.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS )
@SequenceGenerator(name="seq_nota_fiscal_compra", sequenceName="seq_nota_fiscal_compra", allocationSize =1, initialValue=1)
public class NotaFiscalCompra implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_fiscal_compra")
	private Long id;
	
	@Column(nullable=false)
	private String numeroNota;
	
	@Column(nullable=false)
	private String serieNota;
	
	
	private String descricaoObs;
	
	@Column(nullable=false)
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	private BigDecimal valorDesconto = BigDecimal.ZERO;
	
	@Column(nullable=false)
	private BigDecimal valorIcms = BigDecimal.ZERO;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dataCompra;
	
	
	@ManyToOne(targetEntity = Pessoa.class )
	@JoinColumn(name = "pessoa_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private Pessoa pessoa;
	
	
	@ManyToOne
	@JoinColumn(name = "nota_fiscal_compra_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_fiscal_compra_fk"))
	private ContaPagar contaPagar;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNumeroNota() {
		return numeroNota;
	}


	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}


	public String getSerieNota() {
		return serieNota;
	}


	public void setSerieNota(String serieNota) {
		this.serieNota = serieNota;
	}


	public String getDescricaoObs() {
		return descricaoObs;
	}


	public void setDescricaoObs(String descricaoObs) {
		this.descricaoObs = descricaoObs;
	}


	public BigDecimal getValorTotal() {
		return valorTotal;
	}


	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}


	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}


	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}


	public BigDecimal getValorIcms() {
		return valorIcms;
	}


	public void setValorIcms(BigDecimal valorIcms) {
		this.valorIcms = valorIcms;
	}


	public Date getDataCompra() {
		return dataCompra;
	}


	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}


	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	public ContaPagar getContaPagar() {
		return contaPagar;
	}


	public void setContaPagar(ContaPagar contaPagar) {
		this.contaPagar = contaPagar;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotaFiscalCompra other = (NotaFiscalCompra) obj;
		return Objects.equals(id, other.id);
	}
	
	
	

}