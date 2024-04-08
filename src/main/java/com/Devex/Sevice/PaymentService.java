package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Payment;

public interface PaymentService {

	void deleteAll();

	Payment getById(Integer id);

	void delete(Payment entity);

	void deleteById(Integer id);

	long count();

	Optional<Payment> findById(Integer id);

	List<Payment> findAllById(Iterable<Integer> ids);

	List<Payment> findAll();

	Page<Payment> findAll(Pageable pageable);

	List<Payment> findAll(Sort sort);

	Optional<Payment> findOne(Example<Payment> example);

	List<Payment> saveAll(List<Payment> entities);

	Payment save(Payment entity);

}
