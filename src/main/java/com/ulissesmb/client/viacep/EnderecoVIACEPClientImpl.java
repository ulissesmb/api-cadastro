package com.ulissesmb.client.viacep;



import org.springframework.stereotype.Component;

import com.github.gilbertotorrezan.viacep.se.ViaCEPClient;
import com.github.gilbertotorrezan.viacep.shared.ViaCEPEndereco;

@Component
public class EnderecoVIACEPClientImpl implements EnderecoVIACEPClient {

	@Override
	public ViaCEPEndereco buscarCEPExterno(String cep) throws Exception {
		return new ViaCEPClient().getEndereco(cep);
	}

}