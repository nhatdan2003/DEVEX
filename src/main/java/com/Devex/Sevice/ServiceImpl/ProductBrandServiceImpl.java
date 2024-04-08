package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.ProductBrand;
import com.Devex.Repository.ProductBrandRespository;
import com.Devex.Sevice.ProductBrandService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("productbrandservice")
public class ProductBrandServiceImpl implements ProductBrandService{

	@Autowired
	ProductBrandRespository brandRespository;
	
	@Override
	public void deleteAll() {
		brandRespository.deleteAll();
	}

	@Override
	public void delete(ProductBrand entity) {
		brandRespository.delete(entity);
	}

	@Override
	public ProductBrand getById(Integer id) {
		return brandRespository.getById(id);
	}

	@Override
	public void deleteById(Integer id) {
		brandRespository.deleteById(id);
	}

	@Override
	public long count() {
		return brandRespository.count();
	}

	@Override
	public Optional<ProductBrand> findById(Integer id) {
		// TODO Auto-generated method stub
		return brandRespository.findById(id);
	}

	@Override
	public List<ProductBrand> findAll() {
		return brandRespository.findAll();
	}

	@Override
	public Page<ProductBrand> findAll(Pageable pageable) {
		return brandRespository.findAll(pageable);
	}

	@Override
	public List<ProductBrand> findAll(Sort sort) {
		return brandRespository.findAll(sort);
	}

	@Override
	public ProductBrand save(ProductBrand entity) {
		return brandRespository.save(entity);
	}

	@Override
	public List<ProductBrand> getProductBrandNotUnknown() {
		return brandRespository.getProductBrandNotUnknown();
	}

	@Transactional
	@Override
	public void insertProductBrand(String name) {
		brandRespository.insertProductBrand(name);
	}

	@Transactional
	@Override
	public void updateProductBrand(String name, int id) {
		brandRespository.updateProductBrand(name, id);
	}

	@Override
	public ProductBrand getProductBrandNew() {
		return brandRespository.getProductBrandNew();
	}

	@Override
	public String findNameProductBrandById(int id) {
		return brandRespository.findNameProductBrandById(id);
	}

}
