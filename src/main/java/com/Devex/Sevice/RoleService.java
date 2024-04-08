package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Role;

public interface RoleService {

	void deleteAll();

	void delete(Role entity);

	Role getById(String id);

	void deleteById(String id);

	long count();

	Optional<Role> findById(String id);

	List<Role> findAllById(Iterable<String> ids);

	List<Role> findAll();

	Page<Role> findAll(Pageable pageable);

	List<Role> findAll(Sort sort);

	Optional<Role> findOne(Example<Role> example);

	List<Role> saveAll(List<Role> entities);

	Role save(Role entity);

}
