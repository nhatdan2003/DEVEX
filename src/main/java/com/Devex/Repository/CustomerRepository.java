package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Customer;


@EnableJpaRepositories
@Repository("customerRepository")
public interface CustomerRepository extends JpaRepository<Customer, String>{
	
	 @Query("SELECT DISTINCT c.id " +
	            "FROM Customer u " +
	            "JOIN u.orders o " +
	            "JOIN o.orderDetails od " +
	            "JOIN od.productVariant pv " +
	            "JOIN pv.product p " +
	            "JOIN p.categoryDetails cd " +
	            "JOIN cd.category c " +
	            "WHERE u.username = :userName " +
	            "GROUP BY c.id")
	    List<Integer> recomendationSystem(@Param("userName") String userName);
}
