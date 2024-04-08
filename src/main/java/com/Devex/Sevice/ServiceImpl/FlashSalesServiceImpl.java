package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.FlashSale;
import com.Devex.Entity.FlashSaleTime;
import com.Devex.Repository.FlashSalesRespository;
import com.Devex.Repository.FlashSalesTimeRespository;
import com.Devex.Sevice.FlashSalesService;
import com.Devex.Sevice.FlashSalesTimeService;

@SessionScope
@Service("flashSalesService")
public class FlashSalesServiceImpl implements FlashSalesService{

	@Autowired 
	 FlashSalesRespository flashSalesRespository;

	@Override
	public <S extends FlashSale> S save(S entity) {
		return flashSalesRespository.save(entity);
	}

	@Override
	public List<FlashSale> findAll() {
		return flashSalesRespository.findAll();
	}

	@Override
	public Optional<FlashSale> findById(Integer id) {
		return flashSalesRespository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		flashSalesRespository.deleteById(id);
	}

	@Override
	public void deleteAll() {
		flashSalesRespository.deleteAll();
	}

	@Override
	public void insertFlashSale(Double discount, int amountsell, int amountorder, Boolean status, int time,
			int product_ID) {
		flashSalesRespository.insertFlashSale(discount, amountsell, amountorder, status, time, product_ID);
	}

	@Override
	public void updatetFlashSale(Double discount, int amountsell, int amountorder, Boolean status, int time,
			int product_ID) {
		flashSalesRespository.updatetFlashSale(discount, amountsell, amountorder, status, time, product_ID);
	}

	@Override
	public FlashSale findByProductVariantId(int productVariantId) {
		return flashSalesRespository.findByProductVariantId(productVariantId);
	}

	@Override
	public List<FlashSale> findAllFlashSaleNowByIdProdVariant(int productVariantId) {
		return flashSalesRespository.findAllFlashSaleNowByIdProdVariant(productVariantId);
	}

	

	

	
	

	

}
