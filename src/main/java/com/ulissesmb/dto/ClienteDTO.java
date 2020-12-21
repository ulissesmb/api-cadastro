package com.ulissesmb.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDTO implements RequestDTO {

	private static final String MSG_NOME_NOT_NULL = "O nome é obrigatorio";
	private static final String MSG_SIZE_NOME = "O Nome tem que conter de 3 a 30 caracteres";

	private static final String MSG_CPF_NOT_NULL = "O CPF é obrigatorio";
	private static final String MSG_VALIDATOR_SIZE_CPF = "O Cadastro de Pessoa Fisica(CPF) tem que conter de 11 caracteres";

	private Long id;

	@NotNull(message = MSG_NOME_NOT_NULL)
	@Size(min = 3, max = 30, message = MSG_SIZE_NOME)
	private String cpf;

	@NotNull(message = MSG_CPF_NOT_NULL)
	@Size(min = 11, max = 11, message = MSG_VALIDATOR_SIZE_CPF)
	private String nome;

	private EnderecoDTO endereco;

	public ClienteDTO() {
		super();
	}

	public ClienteDTO(Long id,
			@NotNull(message = "O nome é obrigatorio") @Size(min = 3, max = 30, message = "O Nome tem que conter de 3 a 30 caracteres") String cpf,
			@NotNull(message = "O CPF é obrigatorio") @Size(min = 11, max = 11, message = "O Cadastro de Pessoa Fisica(CPF) tem que conter de 11 caracteres") String nome,
			EnderecoDTO endereco) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public EnderecoDTO getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoDTO endereco) {
		this.endereco = endereco;
	}

}
