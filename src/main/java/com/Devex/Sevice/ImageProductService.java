package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.ImageProduct;

public interface ImageProductService {

	void deleteAll();

	void delete(ImageProduct entity);

	ImageProduct getById(String id);

	void deleteById(String id);

	long count();

	Optional<ImageProduct> findById(String id);

	List<ImageProduct> findAllById(Iterable<String> ids);

	List<ImageProduct> findAll();

	Page<ImageProduct> findAll(Pageable pageable);

	List<ImageProduct> findAll(Sort sort);

	Optional<ImageProduct> findOne(Example<ImageProduct> example);

	List<ImageProduct> saveAll(List<ImageProduct> entities);

	ImageProduct save(ImageProduct entity);

	List<ImageProduct> findAllImageProductByProductId(String id);

	void insertImageProduct(String id, String name, String productId);

	void deleteImageProductByNameAndProductId(String name, String productId);

	void deleteImageProductByProductId(String productId);

	String findFirstImageProduct(String id);

}
