package com.Devex.Sevice.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Cart;
import com.Devex.Entity.CartDetail;
import com.Devex.Entity.Request;
import com.Devex.Repository.ProductRepository;
import com.Devex.Repository.RequestRespository;
import com.Devex.Sevice.RequestService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("productRequestService")
public class RequestServiceImpl implements RequestService{
	
	@Autowired
	private RequestRespository productRequestRespository;

	@Override
	public Request save(Request entity) {
		return productRequestRespository.save(entity);
	}

	@Override
	public List<Request> findAll(Sort sort) {
		return productRequestRespository.findAll(sort);
	}

	@Override
	public Page<Request> findAll(Pageable pageable) {
		return productRequestRespository.findAll(pageable);
	}

	@Override
	public List<Request> findAll() {
		return productRequestRespository.findAll();
	}

	@Override
	public List<Request> findAllById(Iterable<Integer> ids) {
		return productRequestRespository.findAllById(ids);
	}

	@Override
	public Optional<Request> findById(Integer id) {
		return productRequestRespository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return productRequestRespository.existsById(id);
	}

	@Override
	public long count() {
		return productRequestRespository.count();
	}

	@Override
	public void deleteById(Integer id) {
		productRequestRespository.deleteById(id);
	}

	@Override
	public Request getById(Integer id) {
		return productRequestRespository.getById(id);
	}

	@Override
	public void delete(Request entity) {
		productRequestRespository.delete(entity);
	}

	@Override
	public void deleteAll() {
		productRequestRespository.deleteAll();
	}

	@Override
	public List<Request> getAllRequestDecreaseByCreatedDay() {
		return productRequestRespository.getAllRequestDecreaseByCreatedDay();
	}

	@Override
	public Request findRequestById(int id) {
		return productRequestRespository.findRequestById(id);
	}

	@Transactional 
	@Override
	public void insertRequest(String entityId, Date createdDay, int status, String content) {
		productRequestRespository.insertRequest(entityId, createdDay, status, content);
	}

	@Override
	public List<Request> getAllRequestFalseDecreaseByCreatedDay() {
		return productRequestRespository.getAllRequestFalseDecreaseByCreatedDay();
	}

	@Override
	public List<Request> getAllRequestTrueDecreaseByCreatedDay() {
		return productRequestRespository.getAllRequestTrueDecreaseByCreatedDay();
	}

	@Override
	public Request findRequestByEntityId(String id) {
		return productRequestRespository.findRequestByEntityId(id);
	}

	@Override
	public List<Request> getAllRequestOrderDecreaseByCreatedDay() {
		return productRequestRespository.getAllRequestOrderDecreaseByCreatedDay();
	}

}
