package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.CartDetail;

@EnableJpaRepositories
@Repository("cartDetailRepository")
public interface CartDetailRespository extends JpaRepository<CartDetail, Integer> {

	@Query("SELECT new com.Devex.DTO.CartDetailDTo(cd.id, cd.productCart.price, cd.productCart.priceSale, cd.productCart.priceSale, cd.cart.id, cd.quantity ,cd.productCart.quantity ,(CASE WHEN fs.amountSell IS NULL THEN 0 ELSE fs.amountSell END) AS quantitySale ,fs.amountOrder AS quantitySaleLimit, cd.productCart.product.name,"
			+ "cd.productCart.color ,cd.productCart.size,cd.productCart.product.sellerProduct.shopName ,"
			+ "cd.productCart.product.sellerProduct.username,"
			+ "cd.productCart.product.id,"
			+ "cd.productCart.id,"
			+ "cd.productCart.product.sellerProduct.avatar,"
			+ "(SELECT ip.name FROM ImageProduct ip WHERE ip.product = cd.productCart.product ORDER BY ip.id ASC LIMIT 1) AS img, cd.createdDay,fs.id) FROM CartDetail cd LEFT JOIN cd.productCart.listFlashSale fs "
			+ "WHERE cd.cart.person.username = ?1 ")
List<CartDetailDTo> findAllCartDTO(String username);





	// @Query("DELETE FROM CartDetail c WHERE
	// c.productCart.product.sellerProduct.username = ?1")
	// void deleteByShopId(String idShop);
	@Query("SELECT c FROM CartDetail c WHERE c.productCart.product.sellerProduct.username = :idShop")
	List<CartDetail> findCartDetailsByShopId(@Param("idShop") String idShop);

	@Query("SELECT o FROM CartDetail o WHERE o.productCart.id = ?1 AND o.cart.id = ?2")
	CartDetail findByIDProductAndUser(int idProd, int idCart);

}
