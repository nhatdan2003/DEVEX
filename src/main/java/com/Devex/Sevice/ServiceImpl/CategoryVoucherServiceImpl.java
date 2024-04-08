package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.CategoryVoucher;
import com.Devex.Repository.CategoryVoucherRepository;
import com.Devex.Sevice.CategoryVoucherService;

@SessionScope
@Service("categoryVoucherService")
public class CategoryVoucherServiceImpl implements CategoryVoucherService{
	@Autowired
	CategoryVoucherRepository categoryVoucherRepository;

	@Override
	public CategoryVoucher save(CategoryVoucher entity) {
		return categoryVoucherRepository.save(entity);
	}

	@Override
	public  List<CategoryVoucher> saveAll(List<CategoryVoucher> entities) {
		return categoryVoucherRepository.saveAll(entities);
	}

	@Override
	public Optional<CategoryVoucher> findOne(Example<CategoryVoucher> example) {
		return categoryVoucherRepository.findOne(example);
	}

	@Override
	public List<CategoryVoucher> findAll(Sort sort) {
		return categoryVoucherRepository.findAll(sort);
	}

	@Override
	public Page<CategoryVoucher> findAll(Pageable pageable) {
		return categoryVoucherRepository.findAll(pageable);
	}

	@Override
	public List<CategoryVoucher> findAll() {
		return categoryVoucherRepository.findAll();
	}

	@Override
	public List<CategoryVoucher> findAllById(Iterable<Integer> ids) {
		return categoryVoucherRepository.findAllById(ids);
	}

	@Override
	public Optional<CategoryVoucher> findById(Integer id) {
		return categoryVoucherRepository.findById(id);
	}

	@Override
	public long count() {
		return categoryVoucherRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		categoryVoucherRepository.deleteById(id);
	}

	@Override
	public CategoryVoucher getById(Integer id) {
		return categoryVoucherRepository.getById(id);
	}

	@Override
	public void delete(CategoryVoucher entity) {
		categoryVoucherRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		categoryVoucherRepository.deleteAll();
	}


	
	
}
