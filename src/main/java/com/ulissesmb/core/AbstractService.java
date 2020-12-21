package com.ulissesmb.core;

import java.util.List;

import com.ulissesmb.domain.interfaces.PersistentEntity;
import com.ulissesmb.domain.seach.SearchFilter;
import com.ulissesmb.domain.wapper.ConverterWapper;
import com.ulissesmb.dto.RequestDTO;

public interface AbstractService<ID extends Number, E extends PersistentEntity, SEARCH extends SearchFilter, DTO extends RequestDTO>
		extends ConverterWapper<DTO, E> {

	List<E> getAll() throws Exception;

	E getById(ID id) throws Exception;

	List<DTO> getAllConverterDTO() throws Exception;

	DTO getByIdConverterDTO(ID id) throws Exception;
	
	DTO saveConverterDTO(DTO dto);

	void delete(E entity);

	void deleteById(ID id);

	E save(E entity);
	
	DTO update(DTO dto, E entity);

	abstract List<DTO> filter(SEARCH searchFilter) throws Exception;
}
