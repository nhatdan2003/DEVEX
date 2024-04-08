package com.Devex.Repository;

import java.util.List;

import com.Devex.DTO.SellerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.DTO.ShopDTO;
import com.Devex.Entity.Seller;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
@Repository("sellerRepository")
public interface SellerRepository extends JpaRepository<Seller, String>{

	Seller findFirstByUsername(String username);
//	@Query("SELECT s FROM Seller s WHERE s.username = :username")
//	Seller findFirstByUsername(@Param("username") String username);
	
	@Query("SELECT new com.Devex.DTO.SellerDTO(s.username, s.fullname, s.shopName, s.avatar, s.createDay, s.activeShop) FROM Seller s where s.username = :username")
	SellerDTO findSeller(@Param("username") String username);

	@Query("SELECT new com.Devex.DTO.SellerDTO(s.username, s.fullname, s.shopName, s.avatar, s.createDay, s.activeShop) FROM Seller s")
	List<SellerDTO> findAllSeller();
	
	@Query("SELECT new com.Devex.DTO.ShopDTO(s.username, s.shopName, s.fullname, s.address, s.phoneAddress, s.mall, s.description) FROM Seller s")
	List<ShopDTO> findAllId();

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO Sellers (Username, Shopname, Address, Phoneaddress, Mall, Activeshop, Description) VALUES (?,?,?,?,?,?,?)", nativeQuery = true)
	void insertSeller(String Username,String Shopname,String Address,String Phoneaddress,Boolean Mall, Boolean Activeshop, String Description);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE Sellers SET Shopname = ?, Address = ?, Phoneaddress = ?, Mall = ?, Activeshop = ?, Description = ? WHERE Username = ?", nativeQuery = true)
	void updateSeller(String Shopname, String Address, String Phoneaddress, Boolean Mall, Boolean Activeshop, String Description, String Username);
	
	@Query("SELECT s FROM Seller s WHERE LOWER(s.shopName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Seller> findByShopNameContainingKeyword(@Param("keyword") String keyword);
	
	@Query("SELECT new com.Devex.DTO.SellerDTO(s.username, s.fullname, s.shopName, s.avatar, s.createDay, s.activeShop) FROM Seller s WHERE LOWER(s.username) LIKE LOWER(CONCAT('%', :keyword, '%')) or LOWER(s.shopName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<SellerDTO> findByShopNameAndUsernameContainingKeyword(@Param("keyword") String keyword);
	
	@Modifying
	@Query(value = "UPDATE Sellers SET Activeshop = :active WHERE Username = :username", nativeQuery = true)
	void updateActiveSellerByUsername(@RequestParam("active") boolean active, @RequestParam("username") String username);
	
	@Query("SELECT s FROM Seller s ORDER BY s.createDay DESC")
	List<Seller> findAllSellerSortUp();
	
	@Query("SELECT new com.Devex.DTO.SellerDTO(s.username, s.fullname, s.shopName, s.avatar, s.createDay, s.activeShop) FROM Seller s ORDER BY s.createDay DESC")
	List<SellerDTO> findAllSellerDTOSortUp();
}
