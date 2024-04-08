package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.OrderDetails;

public interface OrderDetailService {

	void deleteAll();

	void delete(OrderDetails entity);

	OrderDetails getById(String id);

	void deleteById(String id);

	long count();

	List<OrderDetails> findAllById(Iterable<String> ids);

	List<OrderDetails> findAll();

	Page<OrderDetails> findAll(Pageable pageable);

	List<OrderDetails> findAll(Sort sort);

	Optional<OrderDetails> findOne(Example<OrderDetails> example);

	List<OrderDetails> saveAll(List<OrderDetails> entities);

	OrderDetails save(OrderDetails entity);

	List<OrderDetails> findOrderDetailsByOrderIDAndSellerUsername(String id, String username);

	void updateIdOrderDetailsStatus(int ido, String id);

	List<OrderDetails> findOrderDetailsByOrderID(String id);

	OrderDetails saveAndFlush(OrderDetails entity);

	List<Object[]> getTotalPriceByMonthAndSellerUsername(int year, int month, String username);

	List<Object[]> getTotalPriceByYearAndSellerUsername(int year, String username);

	int getTotalOrderDetailsByStatusIdAndSellerUsername(int statusid, String username);

	List<Object[]> getTop5CategoryDetailsAndAmountProductSell(int year);

	List<OrderDetails> findOrderByUsernameAndStatusID(String customerID, int statusID);

	List<OrderDetails> findOrdersByCustomerID(String customerID);

	Optional<OrderDetails> findById(String id);

	List<OrderDetails> findOrderDetailsBySellerUsername(String username);

	Double getTotalRevenueSeller(String username);

	Double getTotalPriceByProductId(String id);

	int getCountProductSellByProductId(String id);

	Object[] getStatisticalOrderoMonthPie(int year, int month, int idstatus);

	int getCountOrderDetailsStatusShopByStatusId(String username, int statusid);
}
