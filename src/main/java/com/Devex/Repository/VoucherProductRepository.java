package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.VoucherProduct;

@EnableJpaRepositories
@Repository("voucherProductRepository")
public interface VoucherProductRepository extends JpaRepository<VoucherProduct, Integer>{
	@Query("Select v From VoucherProduct v Where v.voucher.id = :id")
	List<VoucherProduct> findAllByVoucher(@Param("id") Integer id);
	
}
