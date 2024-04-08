package com.Devex.Sevice;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.CartDetail;
import com.Devex.Entity.Request;

public interface RequestService {
	
	void deleteAll();

	void delete(Request entity);

	Request getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<Request> findById(Integer id);

	List<Request> findAll();

	Page<Request> findAll(Pageable pageable);

	List<Request> findAll(Sort sort);

	Request save(Request entity);

	List<Request> findAllById(Iterable<Integer> ids);

	boolean existsById(Integer id);

	List<Request> getAllRequestDecreaseByCreatedDay();

	Request findRequestById(int id);

	void insertRequest(String entityId, Date createdDay, int status, String content);

	List<Request> getAllRequestTrueDecreaseByCreatedDay();

	List<Request> getAllRequestFalseDecreaseByCreatedDay();

	Request findRequestByEntityId(String id);

	List<Request> getAllRequestOrderDecreaseByCreatedDay();

}
