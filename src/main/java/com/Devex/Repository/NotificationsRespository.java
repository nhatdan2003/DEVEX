package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Notifications;

@EnableJpaRepositories
@Repository("NotificationsRespository")
public interface NotificationsRespository extends JpaRepository<Notifications , Integer>{

	@Query("SELECT n FROM Notifications n WHERE n.userTo = ?1 ORDER BY n.createdDay DESC")
	List<Notifications> getAllNotificationsByUserto(String userto);
	
	@Query("SELECT n FROM Notifications n WHERE n.userFrom = ?1 AND n.userTo = NULL ORDER BY n.createdDay")
	List<Notifications> getAllNotificationsByUserfrom(String userfrom);
	
	@Query("SELECT COUNT(n) FROM Notifications n WHERE n.userTo = ?1 AND n.status = false")
	long getCountNotificationsStatusfalseAndUserto(String userto);
	
	@Query("SELECT n FROM Notifications n WHERE n.userTo = ?1 AND n.status = false ORDER BY n.createdDay DESC LIMIT 10")
	List<Notifications> getTop10NotificationsByUsertoAndStatusFalse(String userto);
	
	@Query("SELECT n FROM Notifications n WHERE n.userTo = ?1 ORDER BY n.status, n.createdDay DESC LIMIT 10")
	List<Notifications> getTop10NotificationsByUserto(String userto);
	
	@Query("SELECT COUNT(n) FROM Notifications n WHERE n.userTo = ?1")
	long getCountNotificationsByUserto(String userto);
	
	@Modifying
	@Query("UPDATE Notifications n SET n.status = true WHERE n.id = ?1")
	void updateNotificationsById(int id);
}
