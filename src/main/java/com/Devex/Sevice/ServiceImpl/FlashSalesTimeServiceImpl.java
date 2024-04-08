package com.Devex.Sevice.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.FlashSaleTime;
import com.Devex.Repository.FlashSalesTimeRespository;
import com.Devex.Sevice.FlashSalesTimeService;

@SessionScope
@Service("flashSalesTimeService")
public class FlashSalesTimeServiceImpl implements FlashSalesTimeService{

	@Autowired 
	 FlashSalesTimeRespository flashSalesTimeRespository;

	@Override
	public <S extends FlashSaleTime> S save(S entity) {
		return flashSalesTimeRespository.save(entity);
	}

	@Override
	public void flush() {
		flashSalesTimeRespository.flush();
	}

	@Override
	public List<FlashSaleTime> findAll() {
		return flashSalesTimeRespository.findAll();
	}

	@Override
	public long count() {
		return flashSalesTimeRespository.count();
	}

	@Override
	public void deleteById(Integer id) {
		flashSalesTimeRespository.deleteById(id);
	}

	@Override
	public FlashSaleTime getById(Integer id) {
		return flashSalesTimeRespository.getById(id);
	}

	@Override
	public void insertFlashSaleTime(LocalDateTime firstTime, LocalDateTime lastTime) {
		flashSalesTimeRespository.insertFlashSaleTime(firstTime, lastTime);
	}

	@Override
	public List<FlashSaleTime> findAll(Sort sort) {
		return flashSalesTimeRespository.findAll(sort);
	}

	@Override
	public List<FlashSaleTime> findFlashSaleTimesByDate(int year, int month, int day) {
		return flashSalesTimeRespository.findFlashSaleTimesByDate(year, month, day);
	}

	@Override
	public FlashSaleTime findFlashSaleTimesByTimeNow() {
		return flashSalesTimeRespository.findFlashSaleTimesByTimeNow();
	}

	public List<FlashSaleTime> findFlashSaleTimesByTimePast() {
		return flashSalesTimeRespository.findFlashSaleTimesByTimePast();
	}

	


	



	

	

	

	
}
