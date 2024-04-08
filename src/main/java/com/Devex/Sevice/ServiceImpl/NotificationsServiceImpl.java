package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Notifications;
import com.Devex.Repository.NotificationsRespository;
import com.Devex.Sevice.NotificationsService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("notificationsService")
public class NotificationsServiceImpl implements NotificationsService{

	@Autowired
	private NotificationsRespository notificationsRespository;
	
	@Override
	public Notifications save(Notifications entity) {
		return notificationsRespository.save(entity);
	}

	@Override
	public List<Notifications> findAll(Sort sort) {
		return notificationsRespository.findAll(sort);
	}

	@Override
	public Page<Notifications> findAll(Pageable pageable) {
		return notificationsRespository.findAll(pageable);
	}

	@Override
	public List<Notifications> findAll() {
		return notificationsRespository.findAll();
	}

	@Override
	public List<Notifications> findAllById(Iterable<Integer> ids) {
		return notificationsRespository.findAllById(ids);
	}

	@Override
	public Optional<Notifications> findById(Integer id) {
		return notificationsRespository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return notificationsRespository.existsById(id);
	}

	@Override
	public long count() {
		return notificationsRespository.count();
	}

	@Override
	public void deleteById(Integer id) {
		notificationsRespository.deleteById(id);
	}

	@Override
	public Notifications getById(Integer id) {
		return notificationsRespository.getById(id);
	}

	@Override
	public void delete(Notifications entity) {
		notificationsRespository.delete(entity);
	}

	@Override
	public void deleteAll() {
		notificationsRespository.deleteAll();
	}

	@Override
	public List<Notifications> getAllNotificationsByUserto(String userto) {
		return notificationsRespository.getAllNotificationsByUserto(userto);
	}

	@Override
	public List<Notifications> getAllNotificationsByUserfrom(String userfrom) {
		return notificationsRespository.getAllNotificationsByUserfrom(userfrom);
	}

	@Override
	public long getCountNotificationsStatusfalseAndUserto(String userto) {
		return notificationsRespository.getCountNotificationsStatusfalseAndUserto(userto);
	}

	@Override
	public long getCountNotificationsByUserto(String userto) {
		return notificationsRespository.getCountNotificationsByUserto(userto);
	}

	@Override
	public List<Notifications> getTop10NotificationsByUsertoAndStatusFalse(String userto) {
		return notificationsRespository.getTop10NotificationsByUsertoAndStatusFalse(userto);
	}

	@Override
	public List<Notifications> getTop10NotificationsByUserto(String userto) {
		return notificationsRespository.getTop10NotificationsByUserto(userto);
	}

	@Transactional
	@Override
	public void updateNotificationsById(int id) {
		notificationsRespository.updateNotificationsById(id);
	}
	
}
