package com.ulissesmb.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulissesmb.domain.seach.impl.ClienteSearchFilterImpl;
import com.ulissesmb.dto.ClienteDTO;
import com.ulissesmb.response.Response;
import com.ulissesmb.response.ResponseBuilder;
import com.ulissesmb.service.ClienteService;

@RestController
@RequestMapping(ClienteController.URI)
public class ClienteController extends ResponseBuilder<ClienteDTO>{

	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	public static final String URI = "/clientes";

	private static final String DELETED_MESSAGE = "Registro removido com sucesso!";
	private static final String UPDATED_MESSAGE = "Registro atualizado com sucesso!";
	private static final String SAVED_MESSAGE = "Registro salvo com sucesso!";

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	@Secured({ "ROLE_ADMIN", "ROLE_USUARIO" })
	public ResponseEntity<Response<ClienteDTO>> getAll() throws Exception {
		List<ClienteDTO> result = clienteService.getClienteOrderDesc();
		return 	withData(result)
				.build();
	}

	@Validated
	@PostMapping
	@Secured({ "ROLE_ADMIN", "ROLE_USUARIO" })
	public ResponseEntity<Response<ClienteDTO>> add(@Valid @RequestBody ClienteDTO dto) throws Exception {
		
		ClienteDTO response = clienteService.saveConverterDTO(dto);
		return withData(response)
				.withMessage(SAVED_MESSAGE).withHttp(HttpStatus.CREATED)
				.build();
	}

	@PutMapping
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Response<ClienteDTO>> update(@Valid @RequestBody ClienteDTO dto) throws Exception {

		dto.setNome(dto.getNome().toUpperCase());
		ClienteDTO updated = clienteService.update(dto, clienteService.getById(dto.getId()));
		log.info("Registro que esta sendo atualizando: {}", updated);

		return withData(updated).withMessage(UPDATED_MESSAGE).withHttp(HttpStatus.CREATED)
				.build();
	}

	@GetMapping(value = "{id}")
	@Secured({ "ROLE_ADMIN", "ROLE_USUARIO" })
	public ResponseEntity<Response<ClienteDTO>> get(@PathVariable Long id) throws Exception {
		return withData(clienteService.getByIdResulDTO(id)).build();
	}

	@DeleteMapping(value = "{cpf}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Response<ClienteDTO>> delete(@PathVariable String cpf) throws Exception {
		clienteService.removeClienteEndereco(cpf);
		return  withMessage(DELETED_MESSAGE).build();
	}

	@PatchMapping("filter")
	@Cacheable("brasilPrevCache")
	@Secured({ "ROLE_USUARIO", "ROLE_ADMIN" })
	public ResponseEntity<Response<ClienteDTO>> filter(@Valid @RequestBody ClienteSearchFilterImpl searchFilter)
			throws Exception {
		List<ClienteDTO> response = clienteService.filter(searchFilter);
		log.info("Registro localizado: {}", response.size());

		return withData(response).build();
	}

}
