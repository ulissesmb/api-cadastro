package com.ulissesmb.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ulissesmb.core.impl.AbstractServiceImpl;
import com.ulissesmb.domain.entity.Cliente;
import com.ulissesmb.domain.entity.Cliente_;
import com.ulissesmb.domain.seach.ClienteSearchFilter;
import com.ulissesmb.dto.ClienteDTO;
import com.ulissesmb.exception.NotFoundException;
import com.ulissesmb.repository.ClienteRepository;
import com.ulissesmb.service.ClienteService;

@Service
public class ClienteServiceImpl
		extends AbstractServiceImpl<Long, Cliente, ClienteSearchFilter, ClienteDTO, ClienteRepository>
		implements ClienteService {

	
	public ClienteServiceImpl() {
		super(ClienteDTO.class, Cliente.class);
	}
	
	
	@Override
	public ClienteDTO saveConverterDTO(ClienteDTO dto) {
		
		return super.saveConverterDTO(dto);
	}

	@Override
	public List<ClienteDTO> getClienteOrderDesc() throws Exception {
		
		List<Cliente> result = getDao().findAll(Sort.by(Sort.Direction.DESC, "id"));
		if(result.isEmpty())
			throw new NotFoundException();
		
		return result.stream().map( c -> converter(c)).collect(Collectors.toList());
	}
	
	@Override
	public List<ClienteDTO> filter(ClienteSearchFilter filter) {
		super.buildPredicates();
		super.addLike(super.getRoot().get(Cliente_.NOME), filter.getNome());
		List<Cliente> result = super.search();
		if (result.isEmpty())
			throw new NotFoundException();

		return result.stream().map(f -> converter(f)).collect(Collectors.toList());
	}

}
