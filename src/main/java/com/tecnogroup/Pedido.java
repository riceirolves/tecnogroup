package com.tecnogroup;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int controle;
	private String data;
	private LocalDate dataConvertida;
	private String nome;
	private Double valor;
	private int quantidade;
	private int codigo;

	public Pedido() {
	}

	public Pedido(int controle, String data, String nome, Double valor, int quantidade, int codigo) {
		this.controle = controle;
		this.data = data;
		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
		this.codigo = codigo;
	}

	public LocalDate getDataConvertida() {
		return dataConvertida;
	}

	public void setDataConvertida(LocalDate dataConvertida) {
		this.dataConvertida = dataConvertida;
	}

	public int getControle() {
		return controle;
	}

	public void setControle(int controle) {
		this.controle = controle;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
