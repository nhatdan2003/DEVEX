package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.FlashSale;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
@Repository("flashSalesRepository")
public interface FlashSalesRespository extends JpaRepository<FlashSale, Integer>{
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO Flashsale (Discount, Amountsell, Amountorder, Status, Time,Product_ID) VALUES (?,?,?,?,?,?)", nativeQuery = true)
	void insertFlashSale(Double discount,int amountsell, int amountorder , Boolean status, int time,int product_ID );
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE Flashsale SET Discount = ?, Amountsell = ?, Amountorder = ?, Status = ?, Time = ? WHERE Product_ID = ?", nativeQuery = true)
	void updatetFlashSale(Double discount,int amountsell, int amountorder , Boolean status, int time,int product_ID );

	@Query("SELECT fs FROM FlashSale fs WHERE fs.productVariant.id = ?1")
    FlashSale findByProductVariantId( int productVariantId);
	
	@Query("SELECT fs FROM FlashSale fs WHERE fs.amountSell > 0 AND fs.status = true AND fs.productVariant.id = ?1")
    List<FlashSale> findAllFlashSaleNowByIdProdVariant(int productVariantId);
	
	
}
