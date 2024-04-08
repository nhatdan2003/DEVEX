package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.DTO.ShopDTO;
import com.Devex.DTO.SellerDTO;
import com.Devex.Entity.Seller;

public interface SellerService {

	void deleteAll();

	void delete(Seller entity);

	Seller getById(String id);

	void deleteById(String id);

	long count();

	Optional<Seller> findById(String id);

	List<Seller> findAllById(Iterable<String> ids);

	List<Seller> findAll();

	Page<Seller> findAll(Pageable pageable);

	List<Seller> findAll(Sort sort);

	Optional<Seller> findOne(Example<Seller> example);


    List<Seller> saveAll(List<Seller> entities);

	Seller save(Seller entity);

	Seller findFirstByUsername(String username);

	void insertSeller(String Username, String Shopname, String Address, String Phoneaddress, Boolean Mall, Boolean Activeshop, String Description);

	void updateSeller(String Shopname, String Address, String Phoneaddress, Boolean Mall, Boolean Activeshop, String Description, String Username);

	List<Seller> findByShopNameContainingKeyword(String keyword);

	List<SellerDTO> findByShopNameAndUsernameContainingKeyword(String keyword);

	void updateActiveSellerByUsername(boolean active, String username);

	List<ShopDTO> findAllId();

	List<SellerDTO> findAllSeller();

	SellerDTO findSeller(String username);

	List<Seller> findAllSellerSortUp();

	List<SellerDTO> findAllSellerDTOSortUp();

}
