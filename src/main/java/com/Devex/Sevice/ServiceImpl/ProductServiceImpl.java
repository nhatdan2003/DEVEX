package com.Devex.Sevice.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.DTO.ProductDTO;
import com.Devex.DTO.infoProductDTO;
import com.Devex.Entity.Product;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.ProductService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("productService")
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductRepository productRepository;

	@Override
	public Product save(Product entity) {
		entity.setId("1");
		return productRepository.save(entity);
	}

	@Override
	public Product findProductById(String id) {
		return productRepository.findProductById(id);
	}

	@Override
	public Optional<Product> findById(String id) {
		return productRepository.findById(id);
	}

	@Override
	public List<Product> saveAll(List<Product> entities) {
		return productRepository.saveAll(entities);
	}

	@Override
	public List<Product> findAll(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public Optional<Product> findOne(Example<Product> example) {
		return productRepository.findOne(example);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> findAllById(Iterable<String> ids) {
		return productRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public void deleteById(String id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product getById(String id) {
		return productRepository.getById(id);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

	@Override

	public List<Product> findProductBySellerUsername(String sellerUsername) {
		return productRepository.findProductBySellerUsername(sellerUsername);
	}

	@Override
	public List<Product> findByKeywordName(String keyword) {

		return productRepository.findByKeywordName(keyword);
	}

	@Override
	public long countByKeywordName(String keywords) {
		return 0;
	}

	@Override
	public List<Product> findProductsByCategoryId(int cID) {
		return productRepository.findProductsByCategoryId(cID);
	}

	public List<Product> findAllProductById(String id) {
		return productRepository.findAllProductById(id);
	}

	@Override
	public Product findByIdProduct(String id) {
		return productRepository.findByIdProduct(id);
	}

	@Override
	@Transactional
	public void updateProduct(String id, String name, int brand, String description, Date createdDay, Boolean active,
			String sellerId, int categoryDetailsId) {
		productRepository.updateProduct(id, name, brand, description, createdDay, active, sellerId, categoryDetailsId);
	}

	@Override
	@Transactional
	public void updateProductIsDeleteById(boolean isdelete, String id) {
		productRepository.updateProductIsDeleteById(isdelete, id);
	}

	@Override
	public List<Product> findProductBySellerUsernameAndIsdeleteProduct(String sellerUsername) {
		return productRepository.findProductBySellerUsernameAndIsdeleteProduct(sellerUsername);
	}

	@Override
	public List<Product> findProductBySellerUsernameAndIsdeleteTrueAndActiveTrueProduct(String sellerUsername) {
		return productRepository.findProductBySellerUsernameAndIsdeleteTrueAndActiveTrueProduct(sellerUsername);
	}

	@Override
	public long getProductCount() {
        return productRepository.count();
    }

	@Override
	@Transactional
	public void insertProduct(String id, String name, int brand, String description, Date createdDay, Boolean active,
			Boolean isdelete, String shopId, Integer categoryId) {
		productRepository.insertProduct(id, name, brand, description, createdDay, active, isdelete, shopId, categoryId);
	}

	@Override
	public Product findLatestProductBySellerUsername(String username) {
		return productRepository.findLatestProductBySellerUsername(username);
	}

	@Override
	public int getCountProductBySellerUsername(String username) {
		return productRepository.getCountProductBySellerUsername(username);
	}

	@Override
	public List<Product> findProductsByFlashSaleTimeAndStatus(int flashSaleTimeId, Boolean flashSaleStatus) {
		return productRepository.findProductsByFlashSaleTimeAndStatus(flashSaleTimeId, flashSaleStatus);
	}

	public int getCountProductSellBySellerUsername(String username, int statusorderid, int statusorderdetailsid) {
		return productRepository.getCountProductSellBySellerUsername(username, statusorderid, statusorderdetailsid);
	}

	@Override
	public List<Product> getListProductByCategoryDetailsIdAndYear(int cateid, int year) {
		return productRepository.getListProductByCategoryDetailsIdAndYear(cateid, year);
	}
	

	@Transactional
	@Override
	public void updateProductCategoryByIdCategory(int cateid, String id) {
		productRepository.updateProductCategoryByIdCategory(cateid, id);
	}

	@Override
	public int getCountProductByCategoryId(int id) {
		return productRepository.getCountProductByCategoryId(id);
	}

	@Override
	public List<Product> findProductsByCategoryDetailsId(int categoryId) {
		return productRepository.findProductsByCategoryDetailsId(categoryId);
	}

	@Override
	public int getCountProductByProductBrandId(int id) {
		return productRepository.getCountProductByProductBrandId(id);
	}

	@Override
	public List<Product> findAllProductByProductBrandId(int id) {
		return productRepository.findAllProductByProductBrandId(id);
	}

	@Transactional
	@Override
	public void updateProductProductBrandByIdProductBrand(int cateid, String id) {
		productRepository.updateProductProductBrandByIdProductBrand(cateid, id);
	}

	@Transactional
	@Override
	public void updateProductActiveById(boolean active, String id) {
		productRepository.updateProductActiveById(active, id);
	}

	@Override
	public List<Product> findProductByShopUsername(String sellerUsername) {
		return productRepository.findProductByShopUsername(sellerUsername);
	}
	
	@Override
	public String findByidProductproductVariants(int id) {
	
		return productRepository.findProductIdByProductVariantId(id);
	}

	@Override
	public List<Product> findAllProductByUsernameContainingKeyword(String keyword, String username) {
		return productRepository.findAllProductByUsernameContainingKeyword(keyword, username);
	}

	@Transactional
	@Override
	public void updateViewProduct(String id, long view) {
		productRepository.updateViewProduct(id, view);
	}

	@Override
	public int getCountProductQuantityZero(String sellerUsername) {
		return productRepository.getCountProductQuantityZero(sellerUsername);
	}

	@Override
	public int getCountProductActive(String username, boolean active) {
		return productRepository.getCountProductActive(username, active);
	}
 
	@Override
	public Double getCountViewCountProductShop(String username) {
		return productRepository.getCountViewCountProductShop(username);
	}

	@Override
	public int getCommentCount(String pdID) {

		return productRepository.getCommentCount(pdID);
	}

	@Override
	public Double getStarAverage(String pdID) {
		if(productRepository.getStarAverage(pdID) != null){
			return productRepository.getStarAverage(pdID);
		}
		double v = 0.0;
		return v;
	}


} 
