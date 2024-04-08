package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Notification;
import com.Devex.Repository.NotificationRepository;
import com.Devex.Sevice.ImageProductService;
import com.Devex.Sevice.NotificationService;

@SessionScope
@Service("notificationService")
public class NotificationServiceImpl implements NotificationService{
	@Autowired
	NotificationRepository notificationRepository;

	@Override
	public Notification save(Notification entity) {
		return notificationRepository.save(entity);
	}

	@Override
	public List<Notification> saveAll(List<Notification> entities) {
		return notificationRepository.saveAll(entities);
	}

	@Override
	public List<Notification> findAll(Sort sort) {
		return notificationRepository.findAll(sort);
	}

	@Override
	public void flush() {
		notificationRepository.flush();
	}

	@Override
	public List<Notification> findAll() {
		return notificationRepository.findAll();
	}

	@Override
	public Optional<Notification> findById(Integer id) {
		return notificationRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return notificationRepository.existsById(id);
	}

	@Override
	public long count() {
		return notificationRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		notificationRepository.deleteById(id);
	}

	@Override
	public Notification getById(Integer id) {
		return notificationRepository.getById(id);
	}

	@Override
	public void delete(Notification entity) {
		notificationRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		notificationRepository.deleteAll();
	}
	
	
}
