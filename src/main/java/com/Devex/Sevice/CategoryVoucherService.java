package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.CategoryDetails;
import com.Devex.Entity.CategoryVoucher;

public interface CategoryVoucherService {

	void deleteAll();

	void delete(CategoryVoucher entity);

	CategoryVoucher getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<CategoryVoucher> findById(Integer id);

	List<CategoryVoucher> findAllById(Iterable<Integer> ids);

	List<CategoryVoucher> findAll();

	Page<CategoryVoucher> findAll(Pageable pageable);

	List<CategoryVoucher> findAll(Sort sort);

	Optional<CategoryVoucher> findOne(Example<CategoryVoucher> example);

	List<CategoryVoucher> saveAll(List<CategoryVoucher> entities);

	CategoryVoucher save(CategoryVoucher entity);


}
