package com.Devex.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.CategoryVoucher;

@EnableJpaRepositories
@Repository("categoryVoucherRepository")
public interface CategoryVoucherRepository extends JpaRepository<CategoryVoucher, Integer>{


}
