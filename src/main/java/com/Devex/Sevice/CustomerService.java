package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Customer;

public interface CustomerService {

	void deleteAll();

	void delete(Customer entity);

	Customer getById(String id);

	void deleteById(String id);

	long count();

	Optional<Customer> findById(String id);

	List<Customer> findAllById(Iterable<String> ids);

	List<Customer> findAll();

	Page<Customer> findAll(Pageable pageable);

	List<Customer> findAll(Sort sort);

	Optional<Customer> findOne(Example<Customer> example);

	List<Customer> saveAll(List<Customer> entities);

	Customer save(Customer entity);

}
