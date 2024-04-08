package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.UserSearch;

public interface UserSearchService {

	void deleteAll();

	void delete(UserSearch entity);

	UserSearch getById(Integer id);

	void deleteById(Integer id);

	long count();

	List<UserSearch> findAllById(Iterable<Integer> ids);

	List<UserSearch> findAll();

	Page<UserSearch> findAll(Pageable pageable);

	Optional<UserSearch> findOne(Example<UserSearch> example);

	List<UserSearch> findAll(Sort sort);

	List<UserSearch> saveAll(List<UserSearch> entities);

	Optional<UserSearch> findById(Integer id);

	UserSearch save(UserSearch entity);

	void insertKeyWorks(String key);

	List<String> selectTop10();
}
