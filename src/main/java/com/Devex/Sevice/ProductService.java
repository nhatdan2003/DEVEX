package com.Devex.Sevice;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import com.Devex.DTO.ProductDTO;
import com.Devex.DTO.infoProductDTO;
import com.Devex.Entity.Product;

import jakarta.transaction.Transactional;


public interface ProductService {

	void deleteAll();

	void delete(Product entity);

	Product getById(String id);

	void deleteById(String id);

	long count();

	List<Product> findAllById(Iterable<String> ids);

	List<Product> findAll();
	
	Page<Product> findAll(Pageable pageable);
	Product findProductById(String id);

	Optional<Product> findOne(Example<Product> example);

	List<Product> findAll(Sort sort);

	List<Product> saveAll(List<Product> entities);

	Optional<Product> findById(String id);

	Product save(Product entity);

	List<Product> findProductBySellerUsername(String sellerUsername);
	
	List<Product> findProductsByCategoryId(int cID);
	
	List<Product> findByKeywordName(String keyword);
	
	long countByKeywordName(String keywords);

	List<Product> findAllProductById(String id);

	Product findByIdProduct(String id);

	void updateProduct(String id, String name, int brand, String description, Date createdDay, Boolean active, String sellerId,
			int categoryDetailsId);

	void updateProductIsDeleteById(boolean isdelete, String id);

	List<Product> findProductBySellerUsernameAndIsdeleteProduct(String sellerUsername);

	List<Product> findProductBySellerUsernameAndIsdeleteTrueAndActiveTrueProduct(String sellerUsername);

	long getProductCount();

	void insertProduct(String id, String name, int brand, String description, Date createdDay, Boolean active, Boolean isdelete,
			String shopId, Integer categoryId);

	Product findLatestProductBySellerUsername(String username);

	int getCountProductBySellerUsername(String username);

	List<Product> findProductsByFlashSaleTimeAndStatus(int flashSaleTimeId, Boolean flashSaleStatus);

//	List<Product> findByPlaceOfSale(String keyWordName, String keyWordAddress);

	
	int getCountProductSellBySellerUsername(String username, int statusorderid, int statusorderdetailsid);

	List<Product> getListProductByCategoryDetailsIdAndYear(int cateid, int year);
	

	void updateProductCategoryByIdCategory(int cateid, String id);

	int getCountProductByCategoryId(int id);

	List<Product> findProductsByCategoryDetailsId(int categoryId);

	int getCountProductByProductBrandId(int id);

	List<Product> findAllProductByProductBrandId(int id);

	void updateProductProductBrandByIdProductBrand(int cateid, String id);

	void updateProductActiveById(boolean active, String id);

	List<Product> findProductByShopUsername(String sellerUsername);

	String findByidProductproductVariants(int id);

	List<Product> findAllProductByUsernameContainingKeyword(String keyword, String username);

	void updateViewProduct(String id, long view);

	int getCountProductQuantityZero(String sellerUsername);

	int getCountProductActive(String username, boolean active);

	Double getCountViewCountProductShop(String username);

	int getCommentCount(String pdID);

	Double getStarAverage(String pdID);
}
