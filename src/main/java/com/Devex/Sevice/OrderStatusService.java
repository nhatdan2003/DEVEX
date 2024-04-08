package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.OrderStatus;

public interface OrderStatusService {

	void deleteAll();

	void delete(OrderStatus entity);

	OrderStatus getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<OrderStatus> findById(Integer id);

	List<OrderStatus> findAllById(Iterable<Integer> ids);

	List<OrderStatus> findAll();

	Page<OrderStatus> findAll(Pageable pageable);

	List<OrderStatus> findAll(Sort sort);

	Optional<OrderStatus> findOne(Example<OrderStatus> example);

	List<OrderStatus> saveAll(List<OrderStatus> entities);

	OrderStatus save(OrderStatus entity);

	List<OrderStatus> getListOrderStatusAdmin();

}
