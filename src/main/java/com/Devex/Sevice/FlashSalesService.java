package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.FlashSale;
import com.Devex.Entity.FlashSaleTime;
import com.Devex.Repository.FlashSalesTimeRespository;

public interface FlashSalesService {

	void deleteAll();

	void deleteById(Integer id);

	List<FlashSale> findAll();

	<S extends FlashSale> S save(S entity);

	void insertFlashSale(Double discount, int amountsell, int amountorder, Boolean status, int time, int product_ID);

	void updatetFlashSale(Double discount, int amountsell, int amountorder, Boolean status, int time, int product_ID);

	FlashSale findByProductVariantId(int productVariantId);

	List<FlashSale> findAllFlashSaleNowByIdProdVariant(int productVariantId);

	Optional<FlashSale> findById(Integer id);





	



	

	
 
}
