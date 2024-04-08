package com.Devex.Sevice.ServiceImpl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.Devex.Entity.ImageProduct;
import com.Devex.Sevice.ImageProductService;
import com.Devex.Sevice.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Customer;
import com.Devex.Repository.CustomerRepository;
import com.Devex.Sevice.CustomerService;
import org.springframework.web.multipart.MultipartFile;

@SessionScope
@Service("customerService")
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Customer save(Customer entity) {
		return customerRepository.save(entity);
	}

	@Override
	public List<Customer> saveAll(List<Customer> entities) {
		return customerRepository.saveAll(entities);
	}

	@Override
	public Optional<Customer> findOne(Example<Customer> example) {
		return customerRepository.findOne(example);
	}

	@Override
	public List<Customer> findAll(Sort sort) {
		return customerRepository.findAll(sort);
	}

	@Override
	public Page<Customer> findAll(Pageable pageable) {
		return customerRepository.findAll(pageable);
	}

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> findAllById(Iterable<String> ids) {
		return customerRepository.findAllById(ids);
	}

	@Override
	public Optional<Customer> findById(String id) {
		return customerRepository.findById(id);
	}

	@Override
	public long count() {
		return customerRepository.count();
	}

	@Override
	public void deleteById(String id) {
		customerRepository.deleteById(id);
	}

	@Override
	public Customer getById(String id) {
		return customerRepository.getById(id);
	}

	@Override
	public void delete(Customer entity) {
		customerRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		customerRepository.deleteAll();
	}

}
