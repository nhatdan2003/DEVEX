package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Devex.Entity.ProductBrand;

public interface ProductBrandRespository extends JpaRepository<ProductBrand, Integer>{

	@Query("SELECT pb FROM ProductBrand pb WHERE pb.name <> 'Unknown'")
	List<ProductBrand> getProductBrandNotUnknown();
	
	@Modifying
	@Query(value = "INSERT INTO Product_Brand (Name) VALUES (:name)", nativeQuery = true)
	void insertProductBrand(@Param("name") String name);

	@Modifying
	@Query(value = "UPDATE Product_Brand SET Name = :name WHERE Id = :id", nativeQuery = true)
	void updateProductBrand(@Param("name") String name, @Param("id") int id);
	
	@Query("SELECT pb FROM ProductBrand pb ORDER BY pb.id DESC LIMIT 1")
	ProductBrand getProductBrandNew();
	
	@Query("SELECT pb.name FROM ProductBrand pb WHERE pb.id = ?1")
	String findNameProductBrandById(int id);
}