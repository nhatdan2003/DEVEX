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

import com.Devex.Entity.UserSearch;
import com.Devex.Entity.UserSearch;
import com.Devex.Repository.UserSearchRespository;
import com.Devex.Sevice.UserSearchService;

@SessionScope
@Service("userSearchService")
public class UserSearchServiceImpl implements UserSearchService {

	@Autowired
	private UserSearchRespository userSearchRespository;

	@Override
	public UserSearch save(UserSearch entity) {
		return userSearchRespository.save(entity);
	}

	@Override
	public Optional<UserSearch> findById(Integer id) {
		return userSearchRespository.findById(id);
	}

	@Override
	public List<UserSearch> saveAll(List<UserSearch> entities) {
		return userSearchRespository.saveAll(entities);
	}

	@Override
	public List<UserSearch> findAll(Sort sort) {
		return userSearchRespository.findAll(sort);
	}

	@Override
	public Optional<UserSearch> findOne(Example<UserSearch> example) {
		return userSearchRespository.findOne(example);
	}

	@Override
	public Page<UserSearch> findAll(Pageable pageable) {
		return userSearchRespository.findAll(pageable);
	}

	@Override
	public List<UserSearch> findAll() {
		return userSearchRespository.findAll();
	}

	@Override
	public List<UserSearch> findAllById(Iterable<Integer> ids) {
		return userSearchRespository.findAllById(ids);
	}

	@Override
	public long count() {
		return userSearchRespository.count();
	}

	@Override
	public void deleteById(Integer id) {
		userSearchRespository.deleteById(id);
	}

	@Override
	public UserSearch getById(Integer id) {
		return userSearchRespository.getById(id);
	}

	@Override
	public void delete(UserSearch entity) {
		userSearchRespository.delete(entity);
	}

	@Override
	public void deleteAll() {
		userSearchRespository.deleteAll();
	}

	public void insertKeyWorks(String key) {
		userSearchRespository.insertKeyWorks(key);
	}

	@Override
	public List<String> selectTop10() {
		return userSearchRespository.selectTop10();
	}

}
