package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.CartDetail;

public interface CartDetailService {

	void deleteAll();

	void delete(CartDetail entity);

	CartDetail getById(Integer id);

	void deleteById(Integer id);

	long count();

	Optional<CartDetail> findById(Integer id);

	List<CartDetail> findAll();

	Page<CartDetail> findAll(Pageable pageable);

	List<CartDetail> findAll(Sort sort);

	CartDetail save(CartDetail entity);

	List<CartDetailDTo> findAllCartDTO(String username);

	CartDetail findByIDProductAndUser(int idProd, int idCart);

	List<CartDetail> saveAll(List<CartDetail> entities);

	void deleteAllInBatch(List<CartDetail> entities);

}
