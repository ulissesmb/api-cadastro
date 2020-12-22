package com.ulissesmb.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnderecoDTO implements RequestDTO, Serializable {

	private static final long serialVersionUID = 1437897257969686245L;

	private Long id;

	@NotNull(message = "CEP é obrigatorio.")
	@Size(min = 8, max = 8, message = "Campo CEP é obrigatorio e necessario 8 caracteres.")
	private String cep;

	private String logradouro;
	private String complemento;
	private String bairro;
	private String cidade;
	private String localidade;
	private String uf;

	public EnderecoDTO() {
	}

	public EnderecoDTO(Long id, @NotNull(message = "CEP não pode ser nulo") String cep, String logradouro,
			String complemento, String bairro, String cidade, String localidade, String uf) {
		super();
		this.id = id;
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.localidade = localidade;
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

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
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

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
