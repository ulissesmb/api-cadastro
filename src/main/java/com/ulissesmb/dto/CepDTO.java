package com.ulissesmb.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CepDTO implements RequestDTO {

	private static final String MSG_NOME_VALIDATOR = "O nome é obrigatorio";

	private Long id;

	@NotNull(message = MSG_NOME_VALIDATOR)
	private String cep;

	private String bairro;
	private String cidade;
	private String logradouro;
	private String complemento;
	private String localidade;
	private String numero;
	private String uf;

	public CepDTO() {
	}

	public CepDTO(@NotNull(message = "O nome é obrigatorio") String cep) {
		super();
		this.cep = cep;
	}

	public CepDTO(Long id, @NotNull(message = "O nome é obrigatorio") String cep, String bairro, String cidade,
			String logradouro, String complemento, String localidade, String numero, String uf) {
		super();
		this.id = id;
		this.cep = cep;
		this.bairro = bairro;
		this.cidade = cidade;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.localidade = localidade;
		this.numero = numero;
		this.uf = uf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

}
