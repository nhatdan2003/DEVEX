package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Category;

public interface CategoryService {

	void deleteAll();

	void delete(Category entity);

	Category getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<Category> findById(Integer id);

	List<Category> findAllById(Iterable<Integer> ids);

	List<Category> findAll();

	Page<Category> findAll(Pageable pageable);

	List<Category> findAll(Sort sort);

	Optional<Category> findOne(Example<Category> example);

	List<Category> saveAll(List<Category> entities);

	Category save(Category entity);

	Category findByProductId(String productId);

	Category getCategoryNew();

	void insertCategory(String name);

	List<Category> findAllCategoryNotNameLikeUnknown();

	void updateCategory(String name, int id);

}
