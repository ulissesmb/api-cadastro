package com.ulissesmb.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.ulissesmb.domain.entity.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long>{

	List<Cliente> findAll(Sort by);
	
}
