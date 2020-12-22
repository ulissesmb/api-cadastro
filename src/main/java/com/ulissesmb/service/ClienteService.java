package com.ulissesmb.service;

import java.util.List;

import com.ulissesmb.core.AbstractService;
import com.ulissesmb.domain.entity.Cliente;
import com.ulissesmb.domain.seach.ClienteSearchFilter;
import com.ulissesmb.dto.ClienteDTO;

public interface ClienteService extends AbstractService<Long, Cliente, ClienteSearchFilter, ClienteDTO> {
	
	List<ClienteDTO> getClienteOrderDesc() throws Exception;
	
	ClienteDTO getByIdResulDTO(Long id) throws Exception;
	
	void removeClienteEndereco(String cpf);

}
