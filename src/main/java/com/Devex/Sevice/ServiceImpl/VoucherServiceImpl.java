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

import com.Devex.Entity.Voucher;
import com.Devex.Repository.VoucherRepository;
import com.Devex.Sevice.VoucherService;

@SessionScope
@Service("voucherService")
public class VoucherServiceImpl implements VoucherService{
	@Autowired
	VoucherRepository voucherRepository;

	@Override
	public Voucher save(Voucher entity) {
		return voucherRepository.save(entity);
	}

	@Override
	public void disabledVoucher(Integer id) {
		 voucherRepository.disabledVoucher(id);
	}

	@Override
	public List<Voucher> findVoucherOfUser(String username) {
		return voucherRepository.findVoucherOfUser(username);
	}

	@Override
	public List<Voucher> saveAll(List<Voucher> entities) {
		return voucherRepository.saveAll(entities);
	}

	@Override
	public  Optional<Voucher> findOne(Example<Voucher> example) {
		return voucherRepository.findOne(example);
	}

	@Override
	public List<Voucher> findAll(Sort sort) {
		return voucherRepository.findAll(sort);
	}

	@Override
	public Page<Voucher> findAll(Pageable pageable) {
		return voucherRepository.findAll(pageable);
	}

	@Override
	public List<Voucher> findAll() {
		return voucherRepository.findAll();
	}

	@Override
	public List<Voucher> findAllById(Iterable<Integer> ids) {
		return voucherRepository.findAllById(ids);
	}

	@Override
	public Optional<Voucher> findById(Integer id) {
		return voucherRepository.findById(id);
	}

	@Override
	public long count() {
		return voucherRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		voucherRepository.deleteById(id);
	}

	@Override
	public Voucher getById(Integer id) {
		return voucherRepository.getById(id);
	}

	@Override
	public void delete(Voucher entity) {
		voucherRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		voucherRepository.deleteAll();
	}

	@Override
	public List<Voucher> findAllByShop(String shopId) {
		return voucherRepository.findAllByShop(shopId);
	}

	@Override
	public Voucher getVoucherNew() {
		return voucherRepository.getVoucherNew();
	}

}
