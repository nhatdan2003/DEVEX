package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.OrderDiscount;

@EnableJpaRepositories
@Repository("orderDiscountRepository")
public interface OrderDiscountRepository extends JpaRepository<OrderDiscount, Integer>{

}
