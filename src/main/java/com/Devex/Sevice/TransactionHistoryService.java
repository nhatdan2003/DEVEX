package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.TransactionHistory;

public interface TransactionHistoryService {

	void deleteAll();

	void deleteById(Integer id);

	long count();

	Optional<TransactionHistory> findById(Integer id);

	List<TransactionHistory> findAllById(Iterable<Integer> ids);

	List<TransactionHistory> findAll();

	Page<TransactionHistory> findAll(Pageable pageable);

	List<TransactionHistory> findAll(Sort sort);

	TransactionHistory save(TransactionHistory entity);

	List<TransactionHistory> getTransactionByIdWallet(String idWallet);
}
