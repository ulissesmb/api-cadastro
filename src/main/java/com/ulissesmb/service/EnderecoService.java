package com.ulissesmb.service;

import com.ulissesmb.core.AbstractService;
import com.ulissesmb.domain.entity.Endereco;
import com.ulissesmb.domain.seach.EnderecoSearchFilter;
import com.ulissesmb.dto.EnderecoDTO;

public interface EnderecoService extends AbstractService<Long, Endereco, EnderecoSearchFilter, EnderecoDTO> {
	

}
