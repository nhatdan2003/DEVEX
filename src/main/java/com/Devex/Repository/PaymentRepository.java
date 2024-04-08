package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Payment;

@EnableJpaRepositories
@Repository("paymentRepository")
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
