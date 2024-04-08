package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Devex.DTO.StatisticalCategoryDetailsPieDTO;
import com.Devex.Entity.Order;
import com.Devex.Entity.Seller;

import jakarta.transaction.Transactional;


public interface OrderRepository extends JpaRepository<Order, String>{

	@Query("SELECT DISTINCT o FROM Order o " +
	           "JOIN o.orderDetails od " +
	           "JOIN od.productVariant pv " +
	           "JOIN pv.product p " +
	           "JOIN p.sellerProduct s " +
	           "WHERE s.username = ?1")
	    List<Order> findOrdersBySellerUsername(String sellerUsername);
	
	@Query("SELECT o FROM Order o WHERE o.id like ?1")
	Order findOrderById(String id);
	
	@Modifying
	@Query("UPDATE Order o SET o.orderStatus.id = ?1 WHERE o.id = ?2")
	void updateIdOrderStatus(int ido, String id);
	
	@Query("SELECT MONTH(o.createdDay) as month, SUM(o.total) as total FROM Order o WHERE YEAR(o.createdDay) = :year GROUP BY MONTH(o.createdDay)")
    List<Object[]> getTotalByMonthAndYear(@Param("year") int year);
    
    @Query("SELECT o FROM Order o WHERE o.createdDay = (SELECT MAX(o2.createdDay) FROM Order o2)")
    Order findLatestOrder();
	
    @Query("SELECT DISTINCT o FROM Order o " +
	           "JOIN FETCH o.orderDetails od " +
	           "JOIN FETCH od.productVariant pv " +
	           "JOIN FETCH pv.product p " +
	           "JOIN FETCH p.sellerProduct s " +
	           "WHERE o.customerOrder.id = ?1 " +
	           "ORDER BY o.createdDay DESC")
	List<Order> findOrdersByCustomerID(String customerID);
    
    @Query("SELECT SUM(o.total) FROM Order o " +
    	    "JOIN o.orderDetails od " +
    	    "JOIN od.productVariant pv " +
    	    "JOIN pv.product p " +
    	    "JOIN p.sellerProduct s " +
    	    "WHERE s.username = :username")
    Double getTotalOrderValueForSeller(@Param("username") String username);

    @Query("SELECT count(o) FROM Order o " +
    	    "JOIN o.orderDetails od " +
    	    "JOIN od.productVariant pv " +
    	    "JOIN pv.product p " +
    	    "JOIN p.sellerProduct s " +
    	    "WHERE s.username = :username AND o.orderStatus.id = 1006")
    int getCountOrderForSeller(@Param("username") String username);
    
    @Query("SELECT COUNT(o) FROM Order o " +
		       "JOIN  o.orderDetails od " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE s.username = :username " +
		       "AND o.orderStatus.id = :statusid " +
		       "AND od.status.id = :statusid1 "+
		       "AND FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month ")
	int getTotalOrderByStatusIdAndSellerUsername(@Param("statusid") int statusid, @Param("statusid1") int statusid1, 
			@Param("username") String username, @Param("year") int year, @Param("month") int month);
    
    @Query("SELECT COUNT(o) FROM Order o " +
		       "JOIN  o.orderDetails od " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE s.username = :username " +
		       "AND od.status.id = :statusid " +
		       "AND FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month ")
	int getTotalOrderFalseAndConfirmByStatusIdAndSellerUsername(@Param("statusid") int statusid, @Param("username") String username,
			@Param("year") int year, @Param("month") int month);
    
    @Query("SELECT o FROM Order o " +
		       "JOIN  o.orderDetails od " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE s.username = :username " +
		       "AND o.orderStatus.id = :statusid " +
		       "AND od.status.id = :statusid1 "+
		       "AND FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month ")
    List<Order> getAllOrderByUsernameAndStatusIdAndYearAndMonth(@Param("statusid") int statusid, @Param("statusid1") int statusid1, @Param("username") String username,
			@Param("year") int year, @Param("month") int month);
    
    @Query("SELECT o FROM Order o " +
		       "JOIN  o.orderDetails od " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE s.username = :username " +
		       "AND od.status.id = :statusid " +
		       "AND FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month ")
	List<Order> getAllOrderFalseByUsernameAndStatusIdAndYearAndMonth(@Param("statusid") int statusid, @Param("username") String username,
			@Param("year") int year, @Param("month") int month);
    
    @Query("SELECT SUM(o.total) FROM Order o " +
		       "WHERE o.orderStatus.id = 1006 ")
	Double getTotalPriceOrder();
    
    @Query("SELECT FUNCTION('DAY', o.createdDay), SUM(od.price) AS totalPrice " +
		       "FROM OrderDetails od " +
		       "JOIN  od.order o " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month " +
		       "AND o.orderStatus.id = 1006 " +
		       "GROUP BY FUNCTION('DAY', o.createdDay)")
	List<Object[]> getTotalPriceOrderByMonthAndYear(@Param("year") int year, @Param("month") int month);
	
	@Query("SELECT COUNT(o) FROM Order o " +
		       "JOIN  o.orderDetails od " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE o.orderStatus.id = :statusid " +
		       "AND FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month ")
	int getCountOrderByStatusIdAndYearAndMonth(@Param("statusid") int statusid, @Param("year") int year, @Param("month") int month);
 
	@Query("SELECT COUNT(o) FROM Order o " +
		       "JOIN  o.orderDetails od " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE od.status.id = :statusid " +
		       "AND FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month ")
	int getCountOrderFalseByStatusIdAndYearAndMonth(@Param("statusid") int statusid, @Param("year") int year, @Param("month") int month);
 
	@Query("SELECT COUNT(o) FROM Order o " +
	       "JOIN  o.orderDetails od " +
	       "JOIN  od.productVariant pv " +
	       "JOIN  pv.product p " +
	       "JOIN  p.sellerProduct s " +
	       "WHERE o.orderStatus.id = 1001 " +
	       "OR o.orderStatus.id = 1002 " +
	       "OR o.orderStatus.id = 1003 " +
	       "OR o.orderStatus.id = 1004 " +
	       "OR o.orderStatus.id = 1005 " +
	       "AND FUNCTION('YEAR', o.createdDay) = :year " +
	       "AND FUNCTION('MONTH', o.createdDay) = :month ")
	int getCountOrderWaitingByStatusIdAndYearAndMonth(@Param("year") int year, @Param("month") int month);
	
	@Query("SELECT FUNCTION('MONTH', o.createdDay), SUM(od.price) AS totalPrice " +
		       "FROM OrderDetails od " +
		       "JOIN  od.order o " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND od.status.id = 1009 AND o.orderStatus.id = 1006 " +
		       "GROUP BY FUNCTION('MONTH', o.createdDay)")
	List<Object[]> getTotalPriceOrderByYear(@Param("year") int year);
	
	@Query("SELECT COUNT(o) FROM Order o " +
		       "JOIN  o.orderDetails od " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE o.orderStatus.id = :statusid " +
		       "AND FUNCTION('YEAR', o.createdDay) = :year ")
	int getCountOrderByStatusIdAndYear(@Param("statusid") int statusid, @Param("year") int year);

	@Query("SELECT COUNT(o) FROM Order o " +
		       "JOIN  o.orderDetails od " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
		       "WHERE od.status.id = :statusid " +
		       "AND FUNCTION('YEAR', o.createdDay) = :year ")
	int getCountOrderFalseByStatusIdAndYear(@Param("statusid") int statusid, @Param("year") int year);

	@Query("SELECT COUNT(o) FROM Order o " +
	       "JOIN  o.orderDetails od " +
	       "JOIN  od.productVariant pv " +
	       "JOIN  pv.product p " +
	       "JOIN  p.sellerProduct s " +
	       "WHERE o.orderStatus.id = 1001 " +
	       "OR o.orderStatus.id = 1002 " +
	       "OR o.orderStatus.id = 1003 " +
	       "OR o.orderStatus.id = 1004 " +
	       "OR o.orderStatus.id = 1005 " +
	       "AND FUNCTION('YEAR', o.createdDay) = :year ")
	int getCountOrderWaitingByStatusIdAndYear(@Param("year") int year);

	@Query("SELECT DISTINCT o FROM Order o " +
			"JOIN FETCH o.orderDetails od " +
			"JOIN FETCH od.productVariant pv " +
			"JOIN FETCH pv.product p " +
			"JOIN FETCH p.sellerProduct s " +
			"WHERE o.customerOrder.username = ?1 " +
			"And od.status.id = ?2 " +
			"ORDER BY o.createdDay DESC")
	List<Order> findOrderByUsernameAndStatusID(String customerID,int statusID);
	

	@Transactional
	@Modifying
	@Query("UPDATE Order o SET o.total = :total, o.totalShip = :totalShip WHERE o.id like :id")
	void updatePriceOrder(@Param("total") double total, @Param("totalShip") double totalShip, @Param("id")  String id);

	@Query("SELECT count(o) FROM Order o WHERE o.customerOrder.username = :username")
    int getCountOrderByCustomerUsername(@Param("username") String username);

	@Query("SELECT o FROM Order o " +
		       "JOIN  o.orderDetails od " +
		       "JOIN  od.productVariant pv " +
		       "JOIN  pv.product p " +
		       "JOIN  p.sellerProduct s " +
			   "WHERE s.username = :username AND LOWER(o.id) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Order> findAllOrderByIdAndUsernameContainingKeyword(@Param("username") String username, @Param("keyword") String keyword);
	
	@Query("SELECT o FROM Order o WHERE o.orderStatus.id = :statusid ORDER BY o.createdDay DESC")
	List<Order> findOrderByOrderStatusId(@Param("statusid") int statusid);
	
	@Query("SELECT o FROM Order o WHERE o.id LIKE %:keyword% OR o.customerOrder.fullname LIKE %:keyword% ORDER BY o.createdDay DESC")
    List<Order> findOrderByIdOrCustomer(@Param("keyword") String keyword);
	
	@Query("SELECT o FROM Order o WHERE o.orderStatus.id = :statusid AND o.id LIKE %:keyword% OR o.orderStatus.id = :statusid AND o.customerOrder.fullname LIKE %:keyword% ORDER BY o.createdDay DESC")
	List<Order> findOrderByOrderStatusIdAndIdOrCustomer(@Param("statusid") int statusid, @Param("keyword") String keyword);

	@Query("SELECT COUNT(o) " +
		       "FROM OrderDetails od " +
		       "JOIN od.order o " +
		       "JOIN od.productVariant pv " +
		       "JOIN pv.product p " +
		       "JOIN p.categoryDetails cd " +
		       "JOIN o.orderStatus os " +
		       "WHERE FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month " +
		       "AND o.orderStatus.id = :idstatus ")
	int getCountOrderByYearAndMonthAndIdStatusMonthPie(@Param("year") int year, @Param("month") int month, @Param("idstatus") int idstatus);

	@Query(value = "DECLARE @Status_IDs TABLE (ID INT); "
			+ "INSERT INTO @Status_IDs VALUES (1002), (1004), (1006), (1007), (1008); "
			+ "SELECT ot.ID, ot.[Name], ISNULL(COUNT(o.id), 0) "
			+ "FROM @Status_IDs s "
			+ "LEFT JOIN Order_Status ot ON ot.ID = s.ID "
			+ "LEFT JOIN Orders o ON ot.ID = o.Status_ID AND MONTH(o.Createdday) = :month AND YEAR(o.Createdday) = :year "
			+ "GROUP BY ot.ID, ot.[Name] ", nativeQuery = true)
	List<Object[]> getStatisticalorderMonthPie(@Param("year") int year, @Param("month") int month);
	
	@Query(value = "DECLARE @Status_IDs TABLE (ID INT); "
			+ "INSERT INTO @Status_IDs VALUES (1002), (1004), (1006), (1007), (1008); "
			+ "SELECT ot.ID, ot.[Name], ISNULL(COUNT(o.id), 0) "
			+ "FROM @Status_IDs s "
			+ "LEFT JOIN Order_Status ot ON ot.ID = s.ID "
			+ "LEFT JOIN Orders o ON ot.ID = o.Status_ID AND YEAR(o.Createdday) = :year "
			+ "GROUP BY ot.ID, ot.[Name] ", nativeQuery = true)
	List<Object[]> getStatisticalorderYearPie(@Param("year") int year);
	
	@Query("SELECT COUNT(o) " +
		       "FROM OrderDetails od " +
		       "JOIN od.order o " +
		       "JOIN od.productVariant pv " +
		       "JOIN pv.product p " +
		       "JOIN p.sellerProduct s " +
		       "JOIN p.categoryDetails cd " +
		       "JOIN o.orderStatus os " +
		       "WHERE FUNCTION('YEAR', o.createdDay) = :year " +
		       "AND FUNCTION('MONTH', o.createdDay) = :month " +
		       "AND s.username = :username ")
	Double getCountOrderByYearAndMonthAndProductShop(@Param("year") int year, @Param("month") int month, @Param("username") String username);

	@Query("SELECT o FROM Order o ORDER BY o.createdDay DESC")
	List<Order> findAllOrderSortDown();
}
