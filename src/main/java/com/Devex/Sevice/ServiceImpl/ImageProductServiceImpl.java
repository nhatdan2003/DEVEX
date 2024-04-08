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

import com.Devex.Entity.ImageProduct;
import com.Devex.Repository.ImageProductRepository;
import com.Devex.Sevice.ImageProductService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("imageProductService")
public class ImageProductServiceImpl implements ImageProductService{
	@Autowired
	ImageProductRepository imageProductRepository;

	@Override
	public ImageProduct save(ImageProduct entity) {
		entity.setId("1");
		return imageProductRepository.save(entity);
	}

	@Override
	public List<ImageProduct> saveAll(List<ImageProduct> entities) {
		return imageProductRepository.saveAll(entities);
	}

	@Override
	public Optional<ImageProduct> findOne(Example<ImageProduct> example) {
		return imageProductRepository.findOne(example);
	}

	@Override
	public List<ImageProduct> findAll(Sort sort) {
		return imageProductRepository.findAll(sort);
	}

	@Override
	public Page<ImageProduct> findAll(Pageable pageable) {
		return imageProductRepository.findAll(pageable);
	}

	@Override
	public List<ImageProduct> findAll() {
		return imageProductRepository.findAll();
	}

	@Override
	public List<ImageProduct> findAllById(Iterable<String> ids) {
		return imageProductRepository.findAllById(ids);
	}

	@Override
	public Optional<ImageProduct> findById(String id) {
		return imageProductRepository.findById(id);
	}

	@Override
	public long count() {
		return imageProductRepository.count();
	}

	@Override
	public void deleteById(String id) {
		imageProductRepository.deleteById(id);
	}

	@Override
	public ImageProduct getById(String id) {
		return imageProductRepository.getById(id);
	}

	@Override
	public void delete(ImageProduct entity) {
		imageProductRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		imageProductRepository.deleteAll();
	}

	@Override
	public List<ImageProduct> findAllImageProductByProductId(String id) {
		return imageProductRepository.findAllImageProductByProductId(id);
	}

	@Override
	@Transactional
	public void insertImageProduct(String id, String name, String productId) {
		imageProductRepository.insertImageProduct(id, name, productId);
	}

	@Override
	@Transactional
	public void deleteImageProductByNameAndProductId(String name, String productId) {
		imageProductRepository.deleteImageProductByNameAndProductId(name, productId);
	}

	@Override
	@Transactional
	public void deleteImageProductByProductId(String productId) {
		imageProductRepository.deleteImageProductByProductId(productId);
	}

	@Override
	public String findFirstImageProduct(String id) {
		return imageProductRepository.findFirstImageProduct(id);
	}
	
	
	
}
