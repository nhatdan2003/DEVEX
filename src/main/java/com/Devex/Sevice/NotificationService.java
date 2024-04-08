package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;

import com.Devex.Entity.Notification;

public interface NotificationService {

	void deleteAll();

	void delete(Notification entity);

	Notification getById(Integer id);

	void deleteById(Integer id);

	long count();

	boolean existsById(Integer id);

	Optional<Notification> findById(Integer id);

	List<Notification> findAll();

	void flush();

	List<Notification> findAll(Sort sort);

	List<Notification> saveAll(List<Notification> entities);

	Notification save(Notification entity);

}
