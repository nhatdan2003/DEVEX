package com.Devex.Sevice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.FlashSaleTime;
import com.Devex.Repository.FlashSalesTimeRespository;

public interface FlashSalesTimeService {

	FlashSaleTime getById(Integer id);

	void deleteById(Integer id);

	long count();

	List<FlashSaleTime> findAll();

	void flush();

	<S extends FlashSaleTime> S save(S entity);

	void insertFlashSaleTime(LocalDateTime firstTime, LocalDateTime lastTime);

	List<FlashSaleTime> findAll(Sort sort);

	List<FlashSaleTime> findFlashSaleTimesByDate(int year, int month, int day);

	FlashSaleTime findFlashSaleTimesByTimeNow();

	List<FlashSaleTime> findFlashSaleTimesByTimePast();



	


	


	



	
 
}
