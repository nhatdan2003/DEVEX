package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.ProductBrand;


public interface ProductBrandService {

	void deleteAll();

	void delete(ProductBrand entity);

	ProductBrand getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<ProductBrand> findById(Integer id);

	List<ProductBrand> findAll();

	Page<ProductBrand> findAll(Pageable pageable);

	List<ProductBrand> findAll(Sort sort);

	ProductBrand save(ProductBrand entity);

	List<ProductBrand> getProductBrandNotUnknown();

	void insertProductBrand(String name);

	void updateProductBrand(String name, int id);

	ProductBrand getProductBrandNew();

	String findNameProductBrandById(int id);
	
}
