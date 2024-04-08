package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Category;
import com.Devex.Entity.Product;
import com.Devex.Entity.User;

public interface UserService {

	void deleteAll();

	void delete(User entity);

	User getById(String id);

	void deleteById(String id);

	long count();

	Optional<User> findById(String id);

	<S extends User> Page<S> findAll(Example<S> example, Pageable pageable);

	List<User> findAllById(Iterable<String> ids);

	List<User> findAll();

	Page<User> findAll(Pageable pageable);

	List<User> findAll(Sort sort);

	Optional<User> findOne(Example<User> example);

	List<User> saveAll(List<User> entities);

	User save(User entity);

	User checkLogin(String username, String pass);

	User findPhone(String phone);

	User findEmail(String email);

	String processOAuthPostLogin(String fullname, String email);

	void updateUser(String Fullname, String Email, String Password, String Phone, String Gender, Boolean Active, String Username);

	int getAmountUserOfAdmin();

	User findByIdActive(String username);

}
