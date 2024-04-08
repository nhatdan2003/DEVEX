package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Notifications;


public interface NotificationsService {

	void deleteAll();

	void delete(Notifications entity);

	Notifications getById(Integer id);

	void deleteById(Integer id);

	long count();

	boolean existsById(Integer id);

	Optional<Notifications> findById(Integer id);

	List<Notifications> findAllById(Iterable<Integer> ids);

	List<Notifications> findAll();

	Page<Notifications> findAll(Pageable pageable);

	List<Notifications> findAll(Sort sort);

	Notifications save(Notifications entity);

	List<Notifications> getAllNotificationsByUserto(String userto);

	List<Notifications> getAllNotificationsByUserfrom(String userfrom);

	long getCountNotificationsStatusfalseAndUserto(String userto);

	long getCountNotificationsByUserto(String userto);

	List<Notifications> getTop10NotificationsByUsertoAndStatusFalse(String userto);

	List<Notifications> getTop10NotificationsByUserto(String userto);

	void updateNotificationsById(int id);
	
}
