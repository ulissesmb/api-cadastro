package com.ulissesmb.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulissesmb.domain.entity.Cliente;
import com.ulissesmb.dto.ClienteDTO;
import com.ulissesmb.response.Response;
import com.ulissesmb.response.ResponseBuilder;
import com.ulissesmb.response._Error;
import com.ulissesmb.service.ClienteService;

@RestController
@RequestMapping(ClienteController.URI)
public class ClienteController extends ResponseBuilder<ClienteDTO> {

	private static final Logger LOG = LoggerFactory.getLogger(ClienteController.class);

	public static final String URI = "/clientes";

	private static final String DELETED_MESSAGE = "Registro removido com sucesso!";
	private static final String UPDATED_MESSAGE = "Registro atualizado com sucesso!";
	private static final String SAVED_MESSAGE = "Registro salvo com sucesso!";

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	@Secured({ "ROLE_ADMIN", "ROLE_USUARIO" })
	public ResponseEntity<Response<ClienteDTO>> getAll() throws Exception {
		LOG.info("Carregar registros");
		List<ClienteDTO> result = clienteService.getClienteOrderDesc();
		return withData(result).build();
	}

	@Validated
	@PostMapping
	@Secured({ "ROLE_ADMIN", "ROLE_USUARIO" })
	public ResponseEntity<Response<ClienteDTO>> add(@Valid @RequestBody ClienteDTO dto) throws Exception {
		LOG.info("Cadastrar registro");
		ClienteDTO response = clienteService.saveConverterDTO(dto);
		return withData(response).withMessage(SAVED_MESSAGE).withHttp(HttpStatus.CREATED).build();
	}

	@PutMapping(value = "{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Response<ClienteDTO>> update(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto)
			throws Exception {
		LOG.info("Atualizar registro");

		if (Objects.isNull(id)) {
			return withError(new _Error("9999999", "ID é obrigatorio para atualizar o regisro.")).build();
		}

		Cliente resultCliente = clienteService.getById(id);
		if (Objects.nonNull(resultCliente)) {
			clienteService.clienteEnderecoAtualizar(resultCliente, dto);
		}
		return withMessage(UPDATED_MESSAGE).build();
	}

	@GetMapping(value = "{id}")
	@Secured({ "ROLE_ADMIN", "ROLE_USUARIO" })
	public ResponseEntity<Response<ClienteDTO>> get(@PathVariable Long id) throws Exception {
		LOG.info("Consultar registro pelo ID");
		return withData(clienteService.getByIdResulDTO(id)).build();
	}

	@DeleteMapping(value = "{cpf}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Response<ClienteDTO>> delete(@PathVariable String cpf) throws Exception {
		clienteService.removeClienteEndereco(cpf);
		return withMessage(DELETED_MESSAGE).build();
	}

}
