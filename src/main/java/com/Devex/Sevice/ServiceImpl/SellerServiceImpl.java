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

import com.Devex.DTO.ShopDTO;
import com.Devex.DTO.SellerDTO;
import com.Devex.Entity.Seller;
import com.Devex.Repository.SellerRepository;
import com.Devex.Sevice.SellerService;

import jakarta.transaction.Transactional;

@SessionScope
@Service("sellerService")
public class SellerServiceImpl implements SellerService{
	@Autowired
	SellerRepository sellerRepository;

	@Override
	public Seller save(Seller entity) {
		return sellerRepository.save(entity);
	}

	@Override
	public List<ShopDTO> findAllId() {
		return sellerRepository.findAllId();
	}


	
	@Override
	public SellerDTO findSeller(String username) {
		return sellerRepository.findSeller(username);
	}

	@Override
	public List<SellerDTO> findAllSeller() {
		return sellerRepository.findAllSeller();
	}
	@Override
	public List<Seller> saveAll(List<Seller> entities) {
		return sellerRepository.saveAll(entities);
	}

	@Override
	public Optional<Seller> findOne(Example<Seller> example) {
		return sellerRepository.findOne(example);
	}

	@Override
	public List<Seller> findAll(Sort sort) {
		return sellerRepository.findAll(sort);
	}

	@Override
	public Page<Seller> findAll(Pageable pageable) {
		return sellerRepository.findAll(pageable);
	}

	@Override
	public List<Seller> findAll() {
		return sellerRepository.findAll();
	}

	@Override
	public List<Seller> findAllById(Iterable<String> ids) {
		return sellerRepository.findAllById(ids);
	}

	@Override
	public Optional<Seller> findById(String id) {
		return sellerRepository.findById(id);
	}

	@Override
	public long count() {
		return sellerRepository.count();
	}

	@Override
	public void deleteById(String id) {
		sellerRepository.deleteById(id);
	}

	@Override
	public Seller getById(String id) {
		return sellerRepository.getById(id);
	}

	@Override
	public void delete(Seller entity) {
		sellerRepository.delete(entity);
	}

	@Override
	public void deleteAll() {
		sellerRepository.deleteAll();
	}

	@Override
	public Seller findFirstByUsername(String username) {
		return sellerRepository.findFirstByUsername(username);
	}

	@Override
	@Transactional
	public void insertSeller(String Username, String Shopname, String Address, String Phoneaddress, Boolean Mall,
			Boolean Activeshop, String Description) {
		sellerRepository.insertSeller(Username, Shopname, Address, Phoneaddress, Mall, Activeshop, Description);
	}

	@Override
	public void updateSeller(String Shopname, String Address, String Phoneaddress, Boolean Mall, Boolean Activeshop, String Description,
			String Username) {
		sellerRepository.updateSeller(Shopname, Address, Phoneaddress, Mall, Activeshop, Description, Username);
	}

	@Override
	public List<Seller> findByShopNameContainingKeyword(String keyword) {
		return sellerRepository.findByShopNameContainingKeyword(keyword);
	}

	@Override
	public List<SellerDTO> findByShopNameAndUsernameContainingKeyword(String keyword) {
		return sellerRepository.findByShopNameAndUsernameContainingKeyword(keyword);
	}

	@Transactional
	@Override
	public void updateActiveSellerByUsername(boolean active, String username) {
		sellerRepository.updateActiveSellerByUsername(active, username);
	}

	@Override
	public List<Seller> findAllSellerSortUp() {
		return sellerRepository.findAllSellerSortUp();
	}

	@Override
	public List<SellerDTO> findAllSellerDTOSortUp() {
		return sellerRepository.findAllSellerDTOSortUp();
	}
	
	
	
}
