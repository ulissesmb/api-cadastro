package com.ulissesmb.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.CollectionUtils;

import com.ulissesmb.core.AbstractService;
import com.ulissesmb.domain.interfaces.PersistentEntity;
import com.ulissesmb.domain.seach.SearchFilter;
import com.ulissesmb.dto.RequestDTO;
import com.ulissesmb.exception.BusinessException;
import com.ulissesmb.exception.NotFoundException;

public abstract class AbstractServiceImpl<ID extends Number, E extends PersistentEntity, SEARCH extends SearchFilter, DTO extends RequestDTO, DAO extends CrudRepository<E, ID>>
		implements AbstractService<ID, E, SEARCH, DTO> {

	private List<Predicate> predicates;

	private CriteriaBuilder cb;

	private CriteriaQuery<E> query;

	private Root<E> root;

	private Class<E> entityClass;

	private Class<DTO> dtoClass;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private DAO dao;

	public AbstractServiceImpl(Class<DTO> dtoClass, Class<E> entityClass) {
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
	}

	@Override
	public E converter(DTO dto) {

		if (dto == null) {
			throw new BusinessException("WD99999", "Entidade não pode esta vazia");
		}

		try {
			Class<E> clazz = (Class<E>) this.entityClass;
			E newInstance = clazz.getDeclaredConstructor().newInstance();
			BeanUtils.copyProperties(dto, newInstance);
			return (E) newInstance;
		} catch (Exception e) {
			throw new BusinessException("RS0001","Erro para relizar o converter");
		}
	}

	@Override
	public DTO converter(E entity) {
		if (entity == null) {
			throw new BusinessException("WD99999", "Entidade não pode esta vazia");
		}

		try {
			Class<DTO> clazz = (Class<DTO>) this.dtoClass;
			DTO newInstance = clazz.getDeclaredConstructor().newInstance();
			BeanUtils.copyProperties(entity, newInstance);
			return (DTO) newInstance;
		} catch (Exception e) {
			throw new BusinessException("RS0001","Erro para relizar o converter");
		}
	}

	protected void add(Predicate predicate) {
		if (predicates == null)
			predicates = new ArrayList<>();
		if (predicate != null)
			predicates.add(predicate);
	}

	protected <X> void addEqual(Expression<X> expression, X value) {
		if (expression != null && value != null && getCb() != null)
			add(getCb().equal(expression, value));
	}

	@SuppressWarnings("unchecked")
	protected <X> void addLike(Expression<X> expression, X value) {
		if (expression != null && value != null && getCb() != null)
			add(getCb().like(getCb().lower((Expression<String>) expression), "%" + value + "%"));
	}

	protected Root<E> buildPredicates() {
		this.root = buildCriteria(this.entityClass).from(this.entityClass);
		return this.getRoot();
	}

	protected CriteriaQuery<E> buildCriteria(Class<E> entityClass) {
		this.cb = getEm().getCriteriaBuilder();
		this.query = getCb().createQuery(entityClass);
		return this.query;
	}

	public List<E> search() {
		if (predicates.isEmpty())
			throw new NotFoundException();

		CriteriaQuery<E> select = this.query.select(this.getRoot());
		predicates.forEach(expressao -> select.where(expressao));
		return getEm().createQuery(select).getResultList();
	}

	@Override
	public List<E> getAll() throws NotFoundException {
		List<E> entities = (List<E>) getDao().findAll();
		if (CollectionUtils.isEmpty(entities)) 
			throw new NotFoundException();
		return entities;
	}

	@Override
	public E getById(ID id) throws Exception {
		Optional<E> entity = dao.findById(id);
		return entity.orElseThrow(() -> new NotFoundException());
	}

	@Override
	public void delete(E entity) {
		dao.delete(entity);
	}

	@Override
	public void deleteById(ID id) {
		dao.deleteById(id);
	}

	@Override
	public E save(E entity) {
		return dao.save(entity);
	}
	
	@Override
	public DTO update(DTO dto, E entity) {
		if (entity == null || dto == null) {
			throw new BusinessException("WD99999", "Entidade não pode esta vazia");
		}
		BeanUtils.copyProperties(dto, entity);
		return converter(save(entity));
	}

	@Override
	public DTO saveConverterDTO(DTO dto) {
		return converter(getDao().save(converter(dto)));
	}

	public List<DTO> getAllConverterDTO() throws Exception {
		List<E> entities = (List<E>) getDao().findAll();
		if (CollectionUtils.isEmpty(entities))
			throw new NotFoundException();

		List<DTO> dtos = entities
				.stream()
				.map((e) -> converter(e))
				.collect(Collectors.toList());
		
		Collections.reverse(dtos);
		return dtos;
	}

	public DTO getByIdConverterDTO(ID id) throws Exception {
		Optional<E> entity = getDao().findById(id);
		if (!entity.isPresent())
			throw new NotFoundException();
		return converter(entity.get());
	}

	public DAO getDao() {
		return dao;
	}

	public EntityManager getEm() {
		return em;
	}

	public CriteriaBuilder getCb() {
		return cb;
	}

	public Root<E> getRoot() {
		return root;
	}

	public Class<DTO> getDtoClass() {
		return dtoClass;
	}

}
