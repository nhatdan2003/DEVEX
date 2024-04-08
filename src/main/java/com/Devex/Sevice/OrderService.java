package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.DTO.StatisticalCategoryDetailsPieDTO;
import com.Devex.Entity.Order;

public interface OrderService {

	void deleteAll();

	void delete(Order entity);

	Order getById(String id);

	void deleteById(String id);

	long count();

	Optional<Order> findById(String id);

	List<Order> findAllById(Iterable<String> ids);

	List<Order> findAll();

	Page<Order> findAll(Pageable pageable);

	List<Order> findAll(Sort sort);

	Optional<Order> findOne(Example<Order> example);

	List<Order> saveAll(List<Order> entities);

	Order save(Order entity);

	List<Order> findOrdersBySellerUsername(String sellerUsername);

	Order findOrderById(String id);

	void updateIdOrderStatus(int ido, String id);

	Order saveAndFlush(Order entity);
	List<Order> findOrdersByCustomerID(String customerID);

	Order findLatestOrder();

	Double getTotalOrderValueForSeller(String username);

	int getCountOrderForSeller(String username);

	int getTotalOrderByStatusIdAndSellerUsername(int statusid, int statusid1, String username, int year, int month);

	int getTotalOrderFalseAndConfirmByStatusIdAndSellerUsername(int statusid, String username, int year, int month);

	List<Order> getAllOrderByUsernameAndStatusIdAndYearAndMonth(int statusid, int statusid1, String username, int year,
			int month);

	List<Order> getAllOrderFalseByUsernameAndStatusIdAndYearAndMonth(int statusid, String username, int year, int month);

	Double getTotalPriceOrder();

	List<Object[]> getTotalPriceOrderByMonthAndYear(int year, int month);

	int getCountOrderByStatusIdAndYearAndMonth(int statusid, int year, int month);

	int getCountOrderFalseByStatusIdAndYearAndMonth(int statusid, int year, int month);

	int getCountOrderWaitingByStatusIdAndYearAndMonth(int year, int month);

	List<Object[]> getTotalPriceOrderByYear(int year);

	int getCountOrderByStatusIdAndYear(int statusid, int year);

	int getCountOrderWaitingByStatusIdAndYear(int year);

	int getCountOrderFalseByStatusIdAndYear(int statusid, int year);

	List<Order> findOrderByUsernameAndStatusID(String customerID,int statusID);

	void updatePriceOrder(double total, double totalShip, String id);
  
	int getCountOrderByCustomerUsername(String username);

	List<Order> findAllOrderByIdAndUsernameContainingKeyword(String username, String keyword);

	List<Order> findOrderByOrderStatusId(int statusid);

	List<Order> findOrderByIdOrCustomer(String keyword);

	List<Order> findOrderByOrderStatusIdAndIdOrCustomer(int statusid, String keyword);

	List<Object[]> getStatisticalorderMonthPie(int year, int month);

	List<Object[]> getStatisticalorderYearPie(int year);

	Double getCountOrderByYearAndMonthAndProductShop(int year, int month, String username);

	List<Order> findAllOrderSortDown();
}
