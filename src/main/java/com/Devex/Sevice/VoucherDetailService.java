package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.VoucherDetails;

public interface VoucherDetailService {

	void deleteAll();

	void delete(VoucherDetails entity);

	VoucherDetails getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<VoucherDetails> findById(Integer id);

	List<VoucherDetails> findAllById(Iterable<Integer> ids);

	List<VoucherDetails> findAll();

	Page<VoucherDetails> findAll(Pageable pageable);

	List<VoucherDetails> findAll(Sort sort);

	Optional<VoucherDetails> findOne(Example<VoucherDetails> example);

	List<VoucherDetails> saveAll(List<VoucherDetails>entities);

	VoucherDetails save(VoucherDetails entity);

	List<VoucherDetails> findAllByUsername(String username);

	void appliedVoucher(String username, Integer voucher);

}
