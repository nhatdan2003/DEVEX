package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.TransactionHistory;
import com.Devex.Repository.TransactionHistoryRespository;
import com.Devex.Sevice.TransactionHistoryService;

@SessionScope
@Service("transactionHistoryService")
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

	@Autowired
	private TransactionHistoryRespository transactionHistoryRespository;

	@Override
	public TransactionHistory save(TransactionHistory entity) {
		return transactionHistoryRespository.save(entity);
	}

	@Override
	public List<TransactionHistory> findAll(Sort sort) {
		return transactionHistoryRespository.findAll(sort);
	}

	@Override
	public Page<TransactionHistory> findAll(Pageable pageable) {
		return transactionHistoryRespository.findAll(pageable);
	}

	@Override
	public List<TransactionHistory> findAll() {
		return transactionHistoryRespository.findAll();
	}

	@Override
	public List<TransactionHistory> findAllById(Iterable<Integer> ids) {
		return transactionHistoryRespository.findAllById(ids);
	}

	@Override
	public Optional<TransactionHistory> findById(Integer id) {
		return transactionHistoryRespository.findById(id);
	}

	@Override
	public long count() {
		return transactionHistoryRespository.count();
	}

	@Override
	public void deleteById(Integer id) {
		transactionHistoryRespository.deleteById(id);
	}

	@Override
	public void deleteAll() {
		transactionHistoryRespository.deleteAll();
	}

	public List<TransactionHistory> getTransactionByIdWallet(String idWallet) {
		return transactionHistoryRespository.getTransactionByIdWallet(idWallet);
	}

}
