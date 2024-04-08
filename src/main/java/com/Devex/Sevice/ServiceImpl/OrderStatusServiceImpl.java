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

import com.Devex.Entity.OrderStatus;
import com.Devex.Repository.OrderStatusRepository;
import com.Devex.Sevice.OrderStatusService;

@SessionScope
@Service("orderStatusService")
public class OrderStatusServiceImpl implements OrderStatusService{
	@Autowired
	OrderStatusRepository orderStatusRepository;

	@Override
	public OrderStatus save(OrderStatus entity) {
		return orderStatusRepository.save(entity);
	}

	@Override
	public List<OrderStatus> saveAll(List<OrderStatus> entities) {
		return orderStatusRepository.saveAll(entities);
	}

	@Override
	public Optional<OrderStatus> findOne(Example<OrderStatus> example) {
		return orderStatusRepository.findOne(example);
	}

	@Override
	public List<OrderStatus> findAll(Sort sort) {
		return orderStatusRepository.findAll(sort);
	}

	@Override
	public Page<OrderStatus> findAll(Pageable pageable) {
		return orderStatusRepository.findAll(pageable);
	}

	@Override
	public List<OrderStatus> findAll() {
		return orderStatusRepository.findAll();
	}

	@Override
	public List<OrderStatus> findAllById(Iterable<Integer> ids) {
		return orderStatusRepository.findAllById(ids);
	}

	@Override
	public Optional<OrderStatus> findById(Integer id) {
		return orderStatusRepository.findById(id);
	}

	@Override
	public long count() {
		return orderStatusRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		orderStatusRepository.deleteById(id);
	}

	@Override
	public OrderStatus getById(Integer id) {
		return orderStatusRepository.getById(id);
	}

	@Override
	public void delete(OrderStatus entity) {
		orderStatusRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		orderStatusRepository.deleteAll();
	}

	@Override
	public List<OrderStatus> getListOrderStatusAdmin() {
		return orderStatusRepository.getListOrderStatusAdmin();
	}
	
	
}
