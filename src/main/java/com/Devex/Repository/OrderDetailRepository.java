package com.Devex.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.OrderDetails;

@EnableJpaRepositories
@Repository("orderDetailRepository")
public interface OrderDetailRepository extends JpaRepository<OrderDetails, String>{

	@Query("SELECT DISTINCT o FROM OrderDetails o " +
	           "JOIN FETCH o.order od " +
	           "JOIN FETCH o.productVariant pv " +
	           "JOIN FETCH pv.product p " +
	           "JOIN FETCH p.sellerProduct s " +
	           "WHERE od.id = ?1 AND s.username = ?2")
	List<OrderDetails> findOrderDetailsByOrderIDAndSellerUsername(String id, String username);
	
	@Query("SELECT DISTINCT o FROM OrderDetails o " +
	           "JOIN FETCH o.order od " +
	           "JOIN FETCH o.productVariant pv " +
	           "JOIN FETCH pv.product p " +
	           "WHERE od.id like ?1")
	List<OrderDetails> findOrderDetailsByOrderID(String id);
	
	@Modifying
	@Query(value = "UPDATE Order_Details SET Status_ID = :statusId WHERE ID = :id", nativeQuery = true)
	void updateIdOrderDetailsStatus(@Param("statusId") int statusId, @Param("id") String id);
	
	@Query("SELECT FUNCTION('DAY', o.createdDay), SUM(od.price) AS totalPrice " +
		       "FROM OrderDetails od " +
		       "JOIN  od.order o " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE s.username = :username " +
		       "AND FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month " +
		       "AND od.status.id = 1009 AND o.orderStatus.id = 1006 " +
		       "GROUP BY FUNCTION('DAY', o.createdDay)")
	List<Object[]> getTotalPriceByMonthAndSellerUsername(@Param("year") int year,
		                                                     @Param("month") int month,
		                                                     @Param("username") String username);
	
	@Query("SELECT FUNCTION('MONTH', o.createdDay), SUM(od.price) AS totalPrice " +
		       "FROM OrderDetails od " +
		       "JOIN  od.order o " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE s.username = :username " +
		       "AND FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND od.status.id = 1009 AND o.orderStatus.id = 1006 " +
		       "GROUP BY FUNCTION('MONTH', o.createdDay)")
	List<Object[]> getTotalPriceByYearAndSellerUsername(@Param("year") int year, @Param("username") String username);
	
	@Query("SELECT SUM(od) FROM OrderDetails od " +
		       "JOIN  od.order o " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE s.username = :username " +
		       "AND od.status.id = :statusid")
	int getTotalOrderDetailsByStatusIdAndSellerUsername(@Param("statusid") int statusid, @Param("username") String username);
	
	@Query("SELECT cd.id, cd.Name, COUNT(od.productVariant.id) AS productCount " +
		       "FROM OrderDetails od " +
		       "JOIN od.order o " +
		       "JOIN od.productVariant pv " +
		       "JOIN pv.product p " +
		       "JOIN p.categoryDetails cd " +
		       "WHERE FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND od.status.id = 1009 AND o.orderStatus.id = 1006 " +
		       "GROUP BY cd.Name, cd.id " +
		       "ORDER BY productCount DESC " +
		       "LIMIT 5")
	List<Object[]> getTop5CategoryDetailsAndAmountProductSell(@Param("year") int year);

	

	@Query("SELECT DISTINCT od FROM OrderDetails od " +
			"JOIN FETCH od.order o " +
			"JOIN FETCH od.productVariant pv " +
			"JOIN FETCH pv.product p " +
			"JOIN FETCH p.sellerProduct s " +
			"WHERE o.customerOrder.username = ?1 " +
			"And od.status.id = ?2 " +
			"Order BY o.createdDay DESC")
	List<OrderDetails> findOrderByUsernameAndStatusID(String customerID, int statusID);

	@Query("SELECT DISTINCT od FROM OrderDetails od " +
			"JOIN FETCH od.order o " +
			"JOIN FETCH od.productVariant pv " +
			"JOIN FETCH pv.product p " +
			"WHERE o.customerOrder.username = ?1 " +
			"Order BY o.createdDay DESC")
	List<OrderDetails> findOrdersByCustomerID(String customerID);

	@Query("SELECT DISTINCT o FROM OrderDetails o " +
			"WHERE o.id = ?1")
	Optional<OrderDetails> findById(String id);
	
	@Query("SELECT DISTINCT o FROM OrderDetails o " +
	           "JOIN FETCH o.order od " +
	           "JOIN FETCH o.productVariant pv " +
	           "JOIN FETCH pv.product p " +
	           "JOIN FETCH p.sellerProduct s " +
	           "WHERE s.username = ?1")
	List<OrderDetails> findOrderDetailsBySellerUsername(String username);
	
	@Query("SELECT SUM(o.price) FROM OrderDetails o " +
	           "JOIN o.order od " +
	           "JOIN o.productVariant pv " +
	           "JOIN pv.product p " +
	           "JOIN p.sellerProduct s " +
	           "WHERE s.username = ?1 AND od.orderStatus.id = 1006")
	Double getTotalRevenueSeller(String username);
	
	@Query("SELECT SUM(od.price) FROM OrderDetails od WHERE od.productVariant.product.id = ?1 AND od.order.orderStatus.id = 1006")
	Double getTotalPriceByProductId(String id);
	
	@Query("SELECT COUNT(od.price) FROM OrderDetails od WHERE od.productVariant.product.id = ?1 AND od.order.orderStatus.id = 1006")
	int getCountProductSellByProductId(String id);
	
	@Query("SELECT o.orderStatus.id, o.orderStatus.name, COUNT(o.id) AS orderCount " +
		       "FROM OrderDetails od " +
		       "JOIN od.order o " +
		       "JOIN od.productVariant pv " +
		       "JOIN pv.product p " +
		       "JOIN p.categoryDetails cd " +
		       "JOIN o.orderStatus os " +
		       "WHERE FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month " +
		       "AND o.orderStatus.id = :idstatus " +
		       "GROUP BY o.orderStatus.id, o.orderStatus.name ")
	Object[] getStatisticalOrderoMonthPie(@Param("year") int year, @Param("month") int month, @Param("idstatus") int idstatus);
	
	@Query("SELECT COUNT(o.id) FROM OrderDetails o " +
	           "JOIN o.order od " +
	           "JOIN o.productVariant pv " +
	           "JOIN pv.product p " +
	           "JOIN p.sellerProduct s " +
	           "WHERE s.username = ?1 AND o.status.id = ?2")
	int getCountOrderDetailsStatusShopByStatusId(String username, int statusid);
}