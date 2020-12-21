package com.ulissesmb.domain.wapper;

import com.ulissesmb.domain.interfaces.PersistentEntity;
import com.ulissesmb.dto.RequestDTO;

public interface ConverterWapper<D extends RequestDTO, E extends PersistentEntity> {

	E converter(D dto);

	D converter(E entity);
}
