package com.Devex.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Follow;

@EnableJpaRepositories
@Repository("followRepository")
public interface FollowRepository extends JpaRepository<Follow, Integer>{

	@Query("SELECT COUNT(f) FROM Follow f WHERE f.seller.username like ?1")
	int getCountFollowBySellerUsername(String username);
	
	@Query("SELECT COUNT(f) FROM Follow f WHERE f.customer.username like ?1")
	int getCountFollowByCustomerUsername(String username);
	
	@Modifying
	@Query(value = "INSERT INTO Follow (Userfollow_ID, Shop_ID, Createdday) VALUES (?1, ?2, ?3)", nativeQuery = true)
	void insertFollow(String userId, String sellerId, Date creadtedday);
	
	@Query("SELECT f FROM Follow f WHERE f.customer.username like ?1 AND f.seller.username like ?2")
	Follow getFollowByUsernameCustomerAndSeller(String userId, String shopId);
	
	@Modifying
	@Query("DELETE FROM Follow f WHERE f.customer.username = :userId AND f.seller.username = :shopId")
    void deleteByCustomerAndSeller(@Param("userId") String userId, @Param("shopId") String shopId);
	
	@Query("SELECT f.customer.username FROM Follow f WHERE f.seller.username like ?1")
	List<String> getAllUserFollowShop(String username);
}
