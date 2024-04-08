package com.Devex.Repository;

import com.Devex.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.Cart;

@EnableJpaRepositories
@Repository("cartRepository")
public interface CartRespository extends JpaRepository<Cart, Integer>{
    @Query("SELECT c FROM Cart c where c.person.username = :username")
    Cart getCartById(@Param("username") String username);
    
    @Query("SELECT COUNT(c.id) FROM Cart c " +
    		"JOIN c.cartDetails cd " +
    		"JOIN cd.productCart pc " +
    		"JOIN pc.product p " +
    		"JOIN p.sellerProduct s " +
    		"WHERE s.username = :username " +
		    "AND FUNCTION('YEAR', cd.createdDay) = :year " +
		    "AND FUNCTION('MONTH', cd.createdDay) = :month ")
    Double getCountCartByProductShopAndCreatedDay(@Param("username") String username, @Param("year") int year, @Param("month") int month);
}
