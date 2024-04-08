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
import com.Devex.Entity.VoucherProduct;
import com.Devex.Repository.VoucherProductRepository;
import com.Devex.Sevice.VoucherProductService;

@SessionScope
@Service("voucherProductService")
public class VoucherProductServiceImpl implements VoucherProductService{
	@Autowired
	VoucherProductRepository voucherProductRepository;

	@Override
	public List<VoucherProduct> findAllByVoucher(Integer id) {
		return voucherProductRepository.findAllByVoucher(id);
	}

	@Override
	public VoucherProduct save(VoucherProduct entity) {
		return voucherProductRepository.save(entity);
	}

	@Override
	public List<VoucherProduct> saveAll(List<VoucherProduct> entities) {
		return voucherProductRepository.saveAll(entities);
	}

	@Override
	public  Optional<VoucherProduct> findOne(Example<VoucherProduct> example) {
		return voucherProductRepository.findOne(example);
	}

	@Override
	public List<VoucherProduct> findAll(Sort sort) {
		return voucherProductRepository.findAll(sort);
	}

	@Override
	public Page<VoucherProduct> findAll(Pageable pageable) {
		return voucherProductRepository.findAll(pageable);
	}

	@Override
	public List<VoucherProduct> findAll() {
		return voucherProductRepository.findAll();
	}

	@Override
	public List<VoucherProduct> findAllById(Iterable<Integer> ids) {
		return voucherProductRepository.findAllById(ids);
	}

	@Override
	public Optional<VoucherProduct> findById(Integer id) {
		return voucherProductRepository.findById(id);
	}

	@Override
	public long count() {
		return voucherProductRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		voucherProductRepository.deleteById(id);
	}

	@Override
	public VoucherProduct getById(Integer id) {
		return voucherProductRepository.getById(id);
	}

	@Override
	public void delete(VoucherProduct entity) {
		voucherProductRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		voucherProductRepository.deleteAll();
	}
	
	

}
