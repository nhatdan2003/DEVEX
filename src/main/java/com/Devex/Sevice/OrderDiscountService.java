package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.OrderDiscount;
import com.Devex.Entity.OrderStatus;

public interface OrderDiscountService {

	void deleteAll();

	void delete(OrderDiscount entity);

	OrderDiscount getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<OrderDiscount> findById(Integer id);

	List<OrderDiscount> findAllById(Iterable<Integer> ids);

	List<OrderDiscount> findAll();

	Page<OrderDiscount> findAll(Pageable pageable);

	List<OrderDiscount> findAll(Sort sort);

	Optional<OrderDiscount> findOne(Example<OrderDiscount> example);

	List<OrderDiscount> saveAll(List<OrderDiscount> entities);

	OrderDiscount save(OrderDiscount entity);


}
