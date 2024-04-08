package com.Devex.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Devex.Entity.TransactionHistory;

import jakarta.transaction.Transactional;

@EnableJpaRepositories
@Repository("transactionHistoryRespository")
public interface TransactionHistoryRespository extends JpaRepository<TransactionHistory, Integer> {

    @Transactional
    @Query(value = " select th.* from Transaction_History th where th.[From] like :idWallet or th.[To] like :idWallet", nativeQuery = true)
    List<TransactionHistory> getTransactionByIdWallet(@Param("idWallet") String idWallet);
}
