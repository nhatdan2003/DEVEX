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

import com.Devex.Entity.OrderDiscount;
import com.Devex.Repository.OrderDiscountRepository;
import com.Devex.Sevice.OrderDiscountService;

@SessionScope
@Service("orderDiscountService")
public class OrderDiscountServiceImpl implements OrderDiscountService{
	@Autowired
	OrderDiscountRepository orderDiscountRepository;

	@Override
	public OrderDiscount save(OrderDiscount entity) {
		return orderDiscountRepository.save(entity);
	}

	@Override
	public List<OrderDiscount> saveAll(List<OrderDiscount> entities) {
		return orderDiscountRepository.saveAll(entities);
	}

	@Override
	public Optional<OrderDiscount> findOne(Example<OrderDiscount> example) {
		return orderDiscountRepository.findOne(example);
	}

	@Override
	public List<OrderDiscount> findAll(Sort sort) {
		return orderDiscountRepository.findAll(sort);
	}

	@Override
	public Page<OrderDiscount> findAll(Pageable pageable) {
		return orderDiscountRepository.findAll(pageable);
	}

	@Override
	public List<OrderDiscount> findAll() {
		return orderDiscountRepository.findAll();
	}

	@Override
	public List<OrderDiscount> findAllById(Iterable<Integer> ids) {
		return orderDiscountRepository.findAllById(ids);
	}

	@Override
	public Optional<OrderDiscount> findById(Integer id) {
		return orderDiscountRepository.findById(id);
	}

	@Override
	public long count() {
		return orderDiscountRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		orderDiscountRepository.deleteById(id);
	}

	@Override
	public OrderDiscount getById(Integer id) {
		return orderDiscountRepository.getById(id);
	}

	@Override
	public void delete(OrderDiscount entity) {
		orderDiscountRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		orderDiscountRepository.deleteAll();
	}
	
	
}
