package com.ulissesmb.domain.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ulissesmb.domain.interfaces.PersistentEntity;

@Entity
@Table(name = "TB_CLIENTE")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente implements PersistentEntity, Serializable {

	private static final long serialVersionUID = 3525056461980422999L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cpf;
	private String nome;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="endereco_id", foreignKey=@ForeignKey(name="fk_cliente_endereco"))
	private Endereco endereco;

	public Cliente() {
	}

	public Cliente(Long id,String cpf,String nome, Endereco endereco) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cliente [id=").append(id).append(", cpf=").append(cpf).append(", nome=").append(nome)
				.append(", endereco=").append(endereco).append("]");
		return builder.toString();
	}
	

}
