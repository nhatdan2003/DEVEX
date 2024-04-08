package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Voucher;

public interface VoucherService {

	void deleteAll();

	void delete(Voucher entity);

	Voucher getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<Voucher> findById(Integer id);

	List<Voucher> findAllById(Iterable<Integer> ids);

	List<Voucher> findAll();

	Page<Voucher> findAll(Pageable pageable);

	List<Voucher> findAll(Sort sort);

	Optional<Voucher> findOne(Example<Voucher> example);

	List<Voucher> saveAll(List<Voucher> entities);

	Voucher save(Voucher entity);

	List<Voucher> findAllByShop(String shopId);

	void disabledVoucher(Integer id);

	List<Voucher> findVoucherOfUser(String username);

	Voucher getVoucherNew();

}
