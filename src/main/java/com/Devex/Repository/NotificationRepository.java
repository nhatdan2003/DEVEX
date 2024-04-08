package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Notification;

@EnableJpaRepositories
@Repository("notificationRepository")
public interface NotificationRepository extends JpaRepository<Notification, Integer>{
	
}
