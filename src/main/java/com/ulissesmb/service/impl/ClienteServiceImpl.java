package com.ulissesmb.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ulissesmb.core.impl.AbstractServiceImpl;
import com.ulissesmb.domain.entity.Cliente;
import com.ulissesmb.domain.entity.Cliente_;
import com.ulissesmb.domain.seach.ClienteSearchFilter;
import com.ulissesmb.domain.seach.impl.EnderecoSearchFilterImpl;
import com.ulissesmb.dto.ClienteDTO;
import com.ulissesmb.dto.EnderecoDTO;
import com.ulissesmb.exception.BusinessException;
import com.ulissesmb.exception.NotFoundException;
import com.ulissesmb.repository.ClienteRepository;
import com.ulissesmb.service.ClienteService;
import com.ulissesmb.service.EnderecoService;

@Service
public class ClienteServiceImpl
		extends AbstractServiceImpl<Long, Cliente, ClienteSearchFilter, ClienteDTO, ClienteRepository>
		implements ClienteService {
	
	@Autowired
	private EnderecoService enderecoService;

	
	public ClienteServiceImpl() {
		super(ClienteDTO.class, Cliente.class);
	}
	
	
	@Override
	public ClienteDTO saveConverterDTO(ClienteDTO dto) {

		try {

			if (Objects.isNull(dto.getId()) || dto.getId() == 0) {
				dto.setId(null);
			}

			String nome = dto.getNome();
			dto.setNome(nome.toUpperCase());

			EnderecoDTO enderecoDTO = getEnderecoByCep(dto.getEndereco().getCep());
			
			Cliente cliente = converter(dto);
			cliente.setEndereco(enderecoService.getById(enderecoDTO.getId()));
			
			Cliente saved = getDao().save(cliente);
			ClienteDTO clienteDTO = converter(saved);
			clienteDTO.setEndereco(enderecoDTO);

			return clienteDTO;

		} catch (Exception e) {
			throw new BusinessException("9999", "Erro Inesperado "+ e.getMessage());
		}
	}
	
	private EnderecoDTO getEnderecoByCep(String cep) throws Exception {
		List<EnderecoDTO> resultEnd = enderecoService.filter(new EnderecoSearchFilterImpl(cep));
		return (!resultEnd.isEmpty()) ? resultEnd.get(0) : null;
	}

	@Override
	public List<ClienteDTO> getClienteOrderDesc() throws Exception {
		
		List<Cliente> result = getDao().findAll(Sort.by(Sort.Direction.DESC, "id"));
		if(result.isEmpty())
			throw new NotFoundException();
		
		List<ClienteDTO> response = result.stream().map(c -> transformar(c))
				.collect(Collectors.toList());
		
		return response;
	}
	
	private ClienteDTO transformar(Cliente cliente) {
		ClienteDTO clienteDTO = converter(cliente);
		EnderecoDTO enderecoDTO = enderecoService.converter(cliente.getEndereco());
		clienteDTO.setEndereco(enderecoDTO);
		return clienteDTO;
	}
	
	@Override
	public ClienteDTO getByIdResulDTO(Long id) throws Exception {
		Cliente cliente = getDao().findById(id).orElseThrow(NotFoundException::new);
		EnderecoDTO enderecoDTO = enderecoService.converter(cliente.getEndereco());
		ClienteDTO clienteDTO = converter(cliente);
		clienteDTO.setEndereco(enderecoDTO);
		return clienteDTO;
		
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


	@Override
	public void removeClienteEndereco(String cpf) {
		super.buildPredicates();
		super.addLike(super.getRoot().get(Cliente_.CPF), cpf);
		List<Cliente> result = super.search();
		if (result.isEmpty())
			throw new NotFoundException();
		
		result.forEach(c -> getDao().delete(c));
	}


	@Override
	public void clienteEnderecoAtualizar(Cliente cliente, ClienteDTO clienteDTO) {
		
		EnderecoDTO enderecoDTO;
		try {
			
			enderecoDTO = getEnderecoByCep(clienteDTO.getEndereco().getCep());
			clienteDTO.setId(cliente.getId());
			clienteDTO.setNome(clienteDTO.getNome().toUpperCase());
			BeanUtils.copyProperties(clienteDTO, cliente);
			cliente.setEndereco(enderecoService.getById(enderecoDTO.getId()));
			
			getDao().save(cliente);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
