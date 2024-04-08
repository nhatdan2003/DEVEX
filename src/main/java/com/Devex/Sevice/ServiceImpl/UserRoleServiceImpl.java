package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.UserRole;
import com.Devex.Repository.UserRoleRespository;
import com.Devex.Sevice.UserRoleService;

@SessionScope
@Service("userRoleService")
public class UserRoleServiceImpl implements  UserRoleService{
	@Autowired
	UserRoleRespository userRoleRespository;

	@Override
	public UserRole save(UserRole entity) {
		return userRoleRespository.save(entity);
	}

	@Override
	public List<UserRole> findAll(Sort sort) {
		return userRoleRespository.findAll(sort);
	}

	@Override
	public Page<UserRole> findAll(Pageable pageable) {
		return userRoleRespository.findAll(pageable);
	}



	@Override
	public List<UserRole> findAllByUserName(String username) {
		return userRoleRespository.findAllByUserName(username);
	}

	@Override
	public List<UserRole> findAll() {
		return userRoleRespository.findAll();
	}

	public List<String> findAllroleByUserName(String username) {
		return userRoleRespository.findAllroleByUserName(username);
	}

	@Override
	public Optional<UserRole> findById(Integer id) {
		return userRoleRespository.findById(id);
	}

	@Override
	public long count() {
		return userRoleRespository.count();
	}

	@Override
	public void deleteById(Integer id) {
		userRoleRespository.deleteById(id);
	}

	@Override
	public UserRole getById(Integer id) {
		return userRoleRespository.getById(id);
	}

	@Override
	public void delete(UserRole entity) {
		userRoleRespository.delete(entity);
	}
	
	
}
