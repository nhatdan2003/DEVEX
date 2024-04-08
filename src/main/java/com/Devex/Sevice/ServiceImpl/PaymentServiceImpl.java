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

import com.Devex.Entity.Payment;
import com.Devex.Repository.PaymentRepository;
import com.Devex.Sevice.PaymentService;

@SessionScope
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService{
	@Autowired
	PaymentRepository paymentRepository;

	@Override
	public Payment save(Payment entity) {
		return paymentRepository.save(entity);
	}

	@Override
	public List<Payment> saveAll(List<Payment> entities) {
		return paymentRepository.saveAll(entities);
	}

	@Override
	public Optional<Payment> findOne(Example<Payment> example) {
		return paymentRepository.findOne(example);
	}

	@Override
	public List<Payment> findAll(Sort sort) {
		return paymentRepository.findAll(sort);
	}

	@Override
	public Page<Payment> findAll(Pageable pageable) {
		return paymentRepository.findAll(pageable);
	}

	@Override
	public List<Payment> findAll() {
		return paymentRepository.findAll();
	}

	@Override
	public List<Payment> findAllById(Iterable<Integer> ids) {
		return paymentRepository.findAllById(ids);
	}

	@Override
	public Optional<Payment> findById(Integer id) {
		return paymentRepository.findById(id);
	}

	@Override
	public long count() {
		return paymentRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		paymentRepository.deleteById(id);
	}

	@Override
	public void delete(Payment entity) {
		paymentRepository.delete(entity);
	}

	@Override
	public Payment getById(Integer id) {
		return paymentRepository.getById(id);
	}

	@Override
	public void deleteAll() {
		paymentRepository.deleteAll();
	}
	
	

}
