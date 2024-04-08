package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.ImageProduct;

@EnableJpaRepositories
@Repository("imageProductRepository")
public interface ImageProductRepository extends JpaRepository<ImageProduct, String>{

	@Query("SELECT p FROM ImageProduct p WHERE p.product.id LIKE ?1")
	List<ImageProduct> findAllImageProductByProductId(String id);
	
	@Modifying
	@Query(value = "INSERT INTO Image_Product (ID, Name, Product_ID) VALUES (:id, :name, :productId)", nativeQuery = true)
	void insertImageProduct(@Param("id") String id, @Param("name") String name, @Param("productId") String productId);

	@Modifying
	@Query(value = "DELETE FROM Image_Product WHERE Name = :name AND Product_ID = :productId", nativeQuery = true)
	void deleteImageProductByNameAndProductId(@Param("name") String name, @Param("productId") String productId);

	@Modifying
	@Query(value = "DELETE FROM Image_Product WHERE Product_ID = :productId", nativeQuery = true)
	void deleteImageProductByProductId(@Param("productId") String productId);

	@Query("SELECT ip.name FROM ImageProduct ip WHERE ip.product.id LIKE ?1 ORDER BY ip.id ASC LIMIT 1")
    String findFirstImageProduct(String id);
}
