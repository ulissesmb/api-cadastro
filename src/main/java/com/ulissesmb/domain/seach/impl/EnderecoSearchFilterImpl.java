package com.ulissesmb.domain.seach.impl;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.ulissesmb.domain.seach.EnderecoSearchFilter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnderecoSearchFilterImpl implements EnderecoSearchFilter {

	@NotNull(message = "O CEP é obrigatorio")
	private String cep;

	public EnderecoSearchFilterImpl() {
	}

	public EnderecoSearchFilterImpl(@NotNull(message = "O nome é obrigatorio") String cep) {
		this.cep = cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String getCep() {
		return this.cep;
	}

}
