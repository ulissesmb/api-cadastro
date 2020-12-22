package com.ulissesmb.service.impl;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.gilbertotorrezan.viacep.shared.ViaCEPEndereco;
import com.ulissesmb.client.viacep.EnderecoVIACEPClient;
import com.ulissesmb.core.impl.AbstractServiceImpl;
import com.ulissesmb.domain.entity.Endereco;
import com.ulissesmb.domain.entity.Endereco_;
import com.ulissesmb.domain.seach.EnderecoSearchFilter;
import com.ulissesmb.dto.EnderecoDTO;
import com.ulissesmb.repository.EnderecoRepository;
import com.ulissesmb.service.EnderecoService;

@Service
public class EnderecoServiceImpl
		extends AbstractServiceImpl<Long, Endereco, EnderecoSearchFilter, EnderecoDTO, EnderecoRepository>
		implements EnderecoService {
	
	@Autowired
	private EnderecoVIACEPClient enderecoVIACEPClient;

	public EnderecoServiceImpl() {
		super(EnderecoDTO.class, Endereco.class);
	}

	@Override
	public List<EnderecoDTO> filter(EnderecoSearchFilter filter) throws Exception {

			super.buildPredicates();
			super.addLike(super.getRoot().get(Endereco_.CEP), filter.getCep());
			List<Endereco> search = super.search();
			if (search.isEmpty()) {
				ViaCEPEndereco buscarCEPExerno = enderecoVIACEPClient.buscarCEPExterno(filter.getCep());
				if(buscarCEPExerno == null)
					throw new IllegalArgumentException("Cep informado não localizado no registro geral. Por favor, preencha corretamente e tente novamente.");

				 EnderecoDTO enderecoDTO = converter(save(new Endereco(null, buscarCEPExerno.getCep().replace("-" , ""), buscarCEPExerno.getLogradouro(),
						buscarCEPExerno.getComplemento(), buscarCEPExerno.getBairro(), buscarCEPExerno.getLocalidade(), buscarCEPExerno.getLocalidade(), buscarCEPExerno.getUf())));
				 return Arrays.asList(enderecoDTO);
			}

			return search.stream().map(f -> converter(f)).collect(Collectors.toList());
	}
}
