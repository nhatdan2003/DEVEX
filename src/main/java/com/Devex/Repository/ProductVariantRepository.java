package com.Devex.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.User;

@EnableJpaRepositories
@Repository("productVariantRepository")
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {

	@Query("SELECT p FROM ProductVariant p WHERE p.product.id LIKE %?1%")
	List<ProductVariant> findAllProductVariantByProductId(String id);

	
	@Query("SELECT pv.price FROM ProductVariant pv JOIN pv.product p WHERE p.id = :id AND pv.color = :color")
	Double findPriceByColor(String id,String color);
	
	@Query("SELECT pv.priceSale FROM ProductVariant pv JOIN pv.product p WHERE p.id = :id AND pv.color = :color AND pv.size = :size")
	Double findPriceByColorAndSize(String id,String color,String size);
		
//	List<ProductVariant> findAllProductVariantByProductId(String id);
	
	@Modifying
	@Query("UPDATE ProductVariant pv SET pv.quantity = :quantity, pv.price = :price, pv.priceSale = :priceSale, pv.size = :size, pv.color = :color WHERE pv.id = :id")
	void updateProductVariant(@Param("id") Integer id, @Param("quantity") Integer quantity,
			@Param("price") Double price, @Param("priceSale") Double priceSale, @Param("size") String size,
			@Param("color") String color);

	@Modifying
	@Query(value = "INSERT INTO Product_Variant (quantity, price, priceSale, size, color, Product_ID) VALUES (:quantity, :price, :priceSale, :size, :color, :productId)", nativeQuery = true)
	void addProductVariant(@Param("quantity") Integer quantity, @Param("price") Double price,
			@Param("priceSale") Double priceSale, @Param("size") String size, @Param("color") String color,
			@Param("productId") String productId);

	@Modifying
	@Query(value = "INSERT INTO Product_Variant (quantity, price, priceSale, size, color, Product_ID) VALUES (?, ?, ?, ?, ?, ?)", nativeQuery = true)
	void newProductVariant( Integer quantity, Double price,
			 Double priceSale,  String size,String color,
			 String productId);
	
	@Modifying
	@Query(value = "DELETE FROM Product_Variant WHERE Product_ID = :productId", nativeQuery = true)
	void deleteProductVariantByProductId(@Param("productId") String productId);
	
	@Query("SELECT pv.id FROM ProductVariant pv WHERE pv.color = ?1 and pv.size = ?2 and pv.product.id = ?3  ")
	int findIdProductVaVariantbySizeandColor(String coler,String size,String id);
	
	@Query("SELECT pv.id FROM ProductVariant pv WHERE pv.color = ?1 and pv.product.id = ?2")
	int findIdProductVaVariantbySize(String coler,String id);
	
	@Modifying
	@Query("UPDATE ProductVariant p SET p.quantity = ?1 WHERE p.id = ?2")
	void updateQuantity(int quantity, int id);
	
	@Modifying
	@Query("UPDATE ProductVariant p SET p.priceSale = ?1 WHERE p.id = ?2")
	void updatePriceSale(double priceSale, int id);
	
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.product.id = ?1 AND pv.color = ?2 AND pv.size = ?3")
	ProductVariant findProductVariantByColorAndSizeAndIdProduct(String productId, String color, String size);
	
	@Query("SELECT pv FROM ProductVariant pv WHERE pv.product.id = ?1 ORDER BY pv.id ASC LIMIT 1")
	ProductVariant findProductVariantByProductId(String id);

}
