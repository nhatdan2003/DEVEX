package com.Devex.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.FlashSaleTime;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
@Repository("flashSalesTimeRepository")
public interface FlashSalesTimeRespository extends JpaRepository<FlashSaleTime, Integer>{
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO Flashsale_Time (Firsttime, Lasttime) VALUES (?,?)", nativeQuery = true)
	void insertFlashSaleTime(LocalDateTime firstTime,LocalDateTime lastTime);
	
	@Modifying
	@Transactional
	@Query("SELECT f FROM FlashSaleTime f WHERE YEAR(f.firstTime) = :year AND MONTH(f.firstTime) = :month AND DAY(f.firstTime) = :day")
	List<FlashSaleTime> findFlashSaleTimesByDate(@Param("year") int year, @Param("month") int month, @Param("day") int day);
	
	

	@Transactional
	@Query(value = "SELECT * FROM Flashsale_Time t WHERE t.Firsttime <= GETDATE() AND t.Lasttime > GETDATE()", nativeQuery = true)
	FlashSaleTime findFlashSaleTimesByTimeNow();
	
	@Transactional
	@Query(value = "SELECT * FROM Flashsale_Time t WHERE t.Lasttime < GETDATE()", nativeQuery = true)
	List<FlashSaleTime> findFlashSaleTimesByTimePast();



}
