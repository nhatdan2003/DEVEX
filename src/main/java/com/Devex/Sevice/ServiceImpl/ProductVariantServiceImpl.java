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

import com.Devex.Entity.ProductVariant;
import com.Devex.Repository.ProductVariantRepository;
import com.Devex.Sevice.ProductVariantService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("productVariantService")
public class ProductVariantServiceImpl implements ProductVariantService{
	@Autowired
	ProductVariantRepository productVariantRepository;

	@Override
	public Double findPriceByColor(String id, String color) {
		return productVariantRepository.findPriceByColor(id, color);
	}

	@Override
	public Double findPriceByColorAndSize(String id, String color, String size) {
		return productVariantRepository.findPriceByColorAndSize(id, color, size);
	}

	@Override
	public ProductVariant save(ProductVariant entity) {
		return productVariantRepository.save(entity);
	}

	@Override
	public List<ProductVariant> saveAll(List<ProductVariant> entities) {
		return productVariantRepository.saveAll(entities);
	}

	@Override
	public Optional<ProductVariant> findOne(Example<ProductVariant> example) {
		return productVariantRepository.findOne(example);
	}

	@Override
	public List<ProductVariant> findAll(Sort sort) {
		return productVariantRepository.findAll(sort);
	}

	@Override
	public Page<ProductVariant> findAll(Pageable pageable) {
		return productVariantRepository.findAll(pageable);
	}

	@Override
	public List<ProductVariant> findAll() {
		return productVariantRepository.findAll();
	}

	@Override
	public List<ProductVariant> findAllById(Iterable<Integer> ids) {
		return productVariantRepository.findAllById(ids);
	}

	@Override
	public Optional<ProductVariant> findById(Integer id) {
		return productVariantRepository.findById(id);
	}

	@Override
	public long count() {
		return productVariantRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		productVariantRepository.deleteById(id);
	}

	@Override
	public void delete(ProductVariant entity) {
		productVariantRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		productVariantRepository.deleteAll();
	}
	
	@Override
	public ProductVariant getById(Integer id) {
		return productVariantRepository.getById(id);
	}

	@Override
	public List<ProductVariant> findAllProductVariantByProductId(String id) {
		return productVariantRepository.findAllProductVariantByProductId(id);
	}

	@Override
	@Transactional
	public void updateProductVariant(Integer id, Integer quantity, Double price, Double priceSale, String size,
			String color) {
		productVariantRepository.updateProductVariant(id, quantity, price, priceSale, size, color);
	}
	
	@Override
	@Transactional
	public void addProductVariant(Integer quantity, Double price, Double priceSale, String size, String color,
			String productId) {
		productVariantRepository.addProductVariant(quantity, price, priceSale, size, color, productId);
	}

	@Override
	@Transactional
	public void deleteProductVariantByProductId(String productId) {
		productVariantRepository.deleteProductVariantByProductId(productId);
	}

	@Override
	public int findIdProductVaVariantbySizeandColor(String coler, String size, String id) {
		return productVariantRepository.findIdProductVaVariantbySizeandColor(coler, size, id);
	}

	@Override
	public int findIdProductVaVariantbySize(String coler, String id) {
		return productVariantRepository.findIdProductVaVariantbySize(coler, id);
	}

	@Transactional
	@Override
	public void updateQuantity(int quantity, int id) {
		productVariantRepository.updateQuantity(quantity, id);
	}

	@Override
	public ProductVariant findProductVariantByColorAndSizeAndIdProduct(String productId, String color, String size) {
		return productVariantRepository.findProductVariantByColorAndSizeAndIdProduct(productId, color, size);
	}

	@Override
	public ProductVariant findProductVariantByProductId(String id) {
		return productVariantRepository.findProductVariantByProductId(id);
	}

	@Override
	public void newProductVariant(Integer quantity, Double price, Double priceSale, String size, String color,
			String productId) {
		productVariantRepository.newProductVariant(quantity, price, priceSale, size, color, productId);
	}

	@Transactional
	@Override
	public void updatePriceSale(double priceSale, int id) {
		productVariantRepository.updatePriceSale(priceSale, id);
	}
	
	
}
