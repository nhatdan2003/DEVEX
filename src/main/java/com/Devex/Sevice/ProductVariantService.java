package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.ProductVariant;

import jakarta.transaction.Transactional;

public interface ProductVariantService {

	ProductVariant getById(Integer id);

	void deleteAll();

	void delete(ProductVariant entity);

	void deleteById(Integer id);

	long count();

	Optional<ProductVariant> findById(Integer id);

	List<ProductVariant> findAllById(Iterable<Integer> ids);

	List<ProductVariant> findAll();

	Page<ProductVariant> findAll(Pageable pageable);

	List<ProductVariant> findAll(Sort sort);

	Optional<ProductVariant> findOne(Example<ProductVariant> example);

	List<ProductVariant> saveAll(List<ProductVariant> entities);

	ProductVariant save(ProductVariant entity);

	List<ProductVariant> findAllProductVariantByProductId(String id);

	Double findPriceByColorAndSize(String id, String color, String size);

	Double findPriceByColor(String id, String color);
	
	void updateProductVariant(Integer id, Integer quantity, Double price, Double priceSale, String size, String color);

	void addProductVariant(Integer quantity, Double price, Double priceSale, String size, String color, String productId);

	void deleteProductVariantByProductId(String productId);

	int findIdProductVaVariantbySizeandColor(String coler, String size, String id);

	int findIdProductVaVariantbySize(String coler, String id);

	void updateQuantity(int quantity, int id);

	ProductVariant findProductVariantByColorAndSizeAndIdProduct(String productId, String color, String size);

	ProductVariant findProductVariantByProductId(String id);

	void newProductVariant(Integer quantity, Double price, Double priceSale, String size, String color, String productId);

	void updatePriceSale(double priceSale, int id);

}
