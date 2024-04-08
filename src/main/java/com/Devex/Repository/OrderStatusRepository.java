package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.OrderStatus;

@EnableJpaRepositories
@Repository("orderStatusRepository")
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer>{

	@Query("SELECT ot FROM OrderStatus ot WHERE ot.id <> 1009")
	List<OrderStatus> getListOrderStatusAdmin();
	
}
