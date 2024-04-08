package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Category;

@EnableJpaRepositories
@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("SELECT ca FROM Category ca " +
		       "JOIN ca.categoryDetails cad " +
		       "JOIN cad.products p " +
		       "WHERE p.id = :productId")
		Category findByProductId(@Param("productId") String productId);
	
	@Query("SELECT ca FROM Category ca ORDER BY ca.id DESC LIMIT 1")
	Category getCategoryNew();
	
	@Modifying
	@Query(value = "INSERT INTO Category (Name) VALUES (:name)", nativeQuery = true)
	void insertCategory(@Param("name") String name);
	
	@Query("SELECT ca FROM Category ca WHERE ca.Name <> 'Unknown'")
	List<Category> findAllCategoryNotNameLikeUnknown();
	
	@Modifying
	@Query("UPDATE Category SET Name = :name WHERE Id = :id")
	void updateCategory(@Param("name") String name, @Param("id") int id);
	
}
