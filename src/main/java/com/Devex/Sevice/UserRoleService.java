package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.UserRole;

public interface UserRoleService {

	void delete(UserRole entity);

	UserRole getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<UserRole> findById(Integer id);

	List<UserRole> findAll();

	Page<UserRole> findAll(Pageable pageable);

	List<UserRole> findAll(Sort sort);

	UserRole save(UserRole entity);


	List<String> findAllroleByUserName(String username);

	List<UserRole> findAllByUserName(String username);

}
