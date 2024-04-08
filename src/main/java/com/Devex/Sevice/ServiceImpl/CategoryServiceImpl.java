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

import com.Devex.Entity.Category;
import com.Devex.Repository.CategoryRepository;
import com.Devex.Sevice.CategoryService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category save(Category entity) {
		return categoryRepository.save(entity);
	}

	@Override
	public List<Category> saveAll(List<Category> entities) {
		return categoryRepository.saveAll(entities);
	}

	@Override
	public Optional<Category> findOne(Example<Category> example) {
		return categoryRepository.findOne(example);
	}

	@Override
	public List<Category> findAll(Sort sort) {
		return categoryRepository.findAll(sort);
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public List<Category> findAllById(Iterable<Integer> ids) {
		return categoryRepository.findAllById(ids);
	}

	@Override
	public Optional<Category> findById(Integer id) {
		return categoryRepository.findById(id);
	}

	@Override
	public long count() {
		return categoryRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public Category getById(Integer id) {
		return categoryRepository.getById(id);
	}

	@Override
	public void delete(Category entity) {
		categoryRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		categoryRepository.deleteAll();
	}

	@Override
	public Category findByProductId(String productId) {
		return categoryRepository.findByProductId(productId);
	}

	@Override
	public Category getCategoryNew() {
		return categoryRepository.getCategoryNew();
	}

	@Transactional
	@Override
	public void insertCategory(String name) {
		categoryRepository.insertCategory(name);
	}

	@Override
	public List<Category> findAllCategoryNotNameLikeUnknown() {
		return categoryRepository.findAllCategoryNotNameLikeUnknown();
	}

	@Transactional
	@Override
	public void updateCategory(String name, int id) {
		categoryRepository.updateCategory(name, id);
	}
	
	
}
