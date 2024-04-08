package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.CategoryDetails;

public interface CategoryDetailService {

	void deleteAll();

	void delete(CategoryDetails entity);

	CategoryDetails getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<CategoryDetails> findById(Integer id);

	List<CategoryDetails> findAllById(Iterable<Integer> ids);

	List<CategoryDetails> findAll();

	Page<CategoryDetails> findAll(Pageable pageable);

	List<CategoryDetails> findAll(Sort sort);

	Optional<CategoryDetails> findOne(Example<CategoryDetails> example);

	List<CategoryDetails> saveAll(List<CategoryDetails> entities);

	CategoryDetails save(CategoryDetails entity);

	List<CategoryDetails> findAllCategoryDetailsById(int id);

	CategoryDetails findCategoryDetailsById(int id);

	List<CategoryDetails> findAllCategoryDetailsBySellerUsername(String username);

	CategoryDetails getCategoryDetailsNew();

	void insertCategoryDetails(String name, int categoryId);

	List<CategoryDetails> findAllCategoryDetailsNotNameLikeUnknown();

	List<CategoryDetails> findAllCategoryDetailsNotNameLikeUnknownAndCateId(int id);

	int getCountCategoryDetailsByCategoryId(int id);

	void updateCategoryDetails(String name, int id);

}
