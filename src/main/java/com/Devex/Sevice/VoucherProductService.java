package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Voucher;
import com.Devex.Entity.VoucherProduct;

public interface VoucherProductService {

	void deleteAll();

	void delete(VoucherProduct entity);


	void deleteById(Integer id);

	long count();

	Optional<VoucherProduct> findById(Integer id);

	List<VoucherProduct> findAllById(Iterable<Integer> ids);

	List<VoucherProduct> findAll();

	Page<VoucherProduct> findAll(Pageable pageable);

	List<VoucherProduct> findAll(Sort sort);

	Optional<VoucherProduct> findOne(Example<VoucherProduct> example);

	List<VoucherProduct> saveAll(List<VoucherProduct> entities);

	VoucherProduct save(VoucherProduct entity);

	VoucherProduct getById(Integer id);

	List<VoucherProduct> findAllByVoucher(Integer id);



}
