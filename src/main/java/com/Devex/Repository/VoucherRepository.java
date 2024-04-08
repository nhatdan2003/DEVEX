package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Product;
import com.Devex.Entity.Voucher;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
@Repository("voucherRepository")
@Transactional
public interface VoucherRepository extends JpaRepository<Voucher, Integer>{
	
	@Query("Select v From Voucher v Where v.creator.username Like :shop")
	List<Voucher> findAllByShop(@Param("shop") String shopId);
	
	@Modifying
	@Query("Update Voucher v Set v.active = false Where v.id = :id")
	void disabledVoucher(@Param("id") Integer id);
	
	@Query("Select v From Voucher v join FETCH v.voucherDetails vd join FETCH vd.customerVoucherDetails cus where cus.username like :username")
	List<Voucher> findVoucherOfUser(@Param("username") String username);
	
	@Query("SELECT v FROM Voucher v ORDER BY v.id DESC LIMIT 1")
	Voucher getVoucherNew();
}
