package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.CategoryDetails;

@EnableJpaRepositories
@Repository("categoryDetailRepository")
public interface CategoryDetailRepository extends JpaRepository<CategoryDetails, Integer>{

	@Query("SELECT cad FROM CategoryDetails cad WHERE cad.category.id = ?1")
	List<CategoryDetails> findAllCategoryDetailsById(int id);
	
	@Query("SELECT cad FROM CategoryDetails cad WHERE cad.id = ?1")
	CategoryDetails findCategoryDetailsById(int id);
	
	@Query("SELECT cad FROM CategoryDetails cad JOIN cad.products p WHERE p.sellerProduct.username = :username")
	List<CategoryDetails> findAllCategoryDetailsBySellerUsername(@Param("username") String username);

	@Query("SELECT cad FROM CategoryDetails cad ORDER BY cad.id DESC LIMIT 1")
	CategoryDetails getCategoryDetailsNew();
	
	@Modifying
	@Query(value = "INSERT INTO Category_Details (Name, Category_ID) VALUES (:name, :categoryId)", nativeQuery = true)
	void insertCategoryDetails(@Param("name") String name, @Param("categoryId") int categoryId);
	
	@Query("SELECT cad FROM FROM CategoryDetails cad WHERE cad.Name <> 'Unknown'")
	List<CategoryDetails> findAllCategoryDetailsNotNameLikeUnknown();

	@Query("SELECT cad FROM CategoryDetails cad WHERE cad.Name <> 'Unknown' AND cad.category.id = ?1")
	List<CategoryDetails> findAllCategoryDetailsNotNameLikeUnknownAndCateId(int id);
	
	@Query("SELECT COUNT(cad) FROM CategoryDetails cad WHERE cad.category.id = ?1")
	int getCountCategoryDetailsByCategoryId(int id);
	
	@Modifying
	@Query("UPDATE CategoryDetails SET Name = :name WHERE id = :id")
	void updateCategoryDetails(@Param("name") String name, @Param("id") int id);
}
