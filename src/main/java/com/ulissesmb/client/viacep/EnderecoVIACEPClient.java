package com.ulissesmb.client.viacep;


import com.github.gilbertotorrezan.viacep.shared.ViaCEPEndereco;

public interface EnderecoVIACEPClient {

	ViaCEPEndereco buscarCEPExterno(String cep) throws Exception;

}