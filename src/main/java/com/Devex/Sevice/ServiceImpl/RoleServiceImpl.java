package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Role;
import com.Devex.Repository.RoleRepository;
import com.Devex.Sevice.RoleService;

@SessionScope
@Service("roleService")
public class RoleServiceImpl implements RoleService{
	@Autowired 
	RoleRepository roleRepository;

	@Override
	public Role save(Role entity) {
		return roleRepository.save(entity);
	}

	@Override
	public List<Role> saveAll(List<Role> entities) {
		return roleRepository.saveAll(entities);
	}

	@Override
	public Optional<Role> findOne(Example<Role> example) {
		return roleRepository.findOne(example);
	}

	@Override
	public List<Role> findAll(Sort sort) {
		return roleRepository.findAll(sort);
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public List<Role> findAllById(Iterable<String> ids) {
		return roleRepository.findAllById(ids);
	}

	@Override
	public Optional<Role> findById(String id) {
		return roleRepository.findById(id);
	}

	@Override
	public long count() {
		return roleRepository.count();
	}

	@Override
	public void deleteById(String id) {
		roleRepository.deleteById(id);
	}

	@Override
	public Role getById(String id) {
		return roleRepository.getById(id);
	}

	@Override
	public void delete(Role entity) {
		roleRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		roleRepository.deleteAll();
	}
	
	
}
