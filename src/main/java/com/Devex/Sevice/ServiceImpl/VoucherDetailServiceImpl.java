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

import com.Devex.Entity.VoucherDetails;
import com.Devex.Repository.VoucherDetailRepository;
import com.Devex.Sevice.VoucherDetailService;

@SessionScope
@Service("voucherDetailService")
public class VoucherDetailServiceImpl implements VoucherDetailService{
	@Autowired
	VoucherDetailRepository voucherDetailRepository;

	@Override
	public VoucherDetails save(VoucherDetails entity) {
		return voucherDetailRepository.save(entity);
	}

	@Override
	public List<VoucherDetails> findAllByUsername(String username) {
		return voucherDetailRepository.findAllByUsername(username);
	}

	@Override
	public List<VoucherDetails> saveAll(List<VoucherDetails>entities) {
		return voucherDetailRepository.saveAll(entities);
	}

	@Override
	public  Optional<VoucherDetails> findOne(Example<VoucherDetails> example) {
		return voucherDetailRepository.findOne(example);
	}

	@Override
	public List<VoucherDetails> findAll(Sort sort) {
		return voucherDetailRepository.findAll(sort);
	}

	@Override
	public Page<VoucherDetails> findAll(Pageable pageable) {
		return voucherDetailRepository.findAll(pageable);
	}

	@Override
	public List<VoucherDetails> findAll() {
		return voucherDetailRepository.findAll();
	}

	@Override
	public List<VoucherDetails> findAllById(Iterable<Integer> ids) {
		return voucherDetailRepository.findAllById(ids);
	}

	@Override
	public Optional<VoucherDetails> findById(Integer id) {
		return voucherDetailRepository.findById(id);
	}

	@Override
	public long count() {
		return voucherDetailRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		voucherDetailRepository.deleteById(id);
	}

	@Override
	public VoucherDetails getById(Integer id) {
		return voucherDetailRepository.getById(id);
	}

	@Override
	public void delete(VoucherDetails entity) {
		voucherDetailRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		voucherDetailRepository.deleteAll();
	}

	@Override
	public void appliedVoucher(String username, Integer voucher) {
		voucherDetailRepository.appliedVoucher(username, voucher);
	}
	
	
	
}
