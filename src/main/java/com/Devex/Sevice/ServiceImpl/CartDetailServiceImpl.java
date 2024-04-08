package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.CartDetail;
import com.Devex.Repository.CartDetailRespository;
import com.Devex.Sevice.CartDetailService;

@SessionScope
@Service("cartDetailService")
public class CartDetailServiceImpl implements CartDetailService{
	@Autowired
	CartDetailRespository cartDetail;

	@Override
	public CartDetail save(CartDetail entity) {
		return cartDetail.save(entity);
	}
	
	

	@Override
	public List<CartDetail> saveAll(List<CartDetail> entities) {
		return cartDetail.saveAll(entities);
	}


	@Override
	public void deleteAllInBatch(List<CartDetail> entities) {
		cartDetail.deleteAllInBatch(entities);
	}


	@Override
	public List<CartDetail> findAll(Sort sort) {
		return cartDetail.findAll(sort);
	}

	@Override
	public Page<CartDetail> findAll(Pageable pageable) {
		return cartDetail.findAll(pageable);
	}

	@Override
	public List<CartDetail> findAll() {
		return cartDetail.findAll();
	}

	@Override
	public Optional<CartDetail> findById(Integer id) {
		return cartDetail.findById(id);
	}

	@Override
	public long count() {
		return cartDetail.count();
	}

	@Override
	public void deleteById(Integer id) {
		cartDetail.deleteById(id);
	}

	@Override
	public CartDetail getById(Integer id) {
		return cartDetail.getById(id);
	}

	@Override
	public void delete(CartDetail entity) {
		cartDetail.delete(entity);
	}

	@Override
	public void deleteAll() {
		cartDetail.deleteAll();
	}

	@Override

	public List<CartDetailDTo> findAllCartDTO(String username) {
		return cartDetail.findAllCartDTO(username);
	}

	@Override
	public CartDetail findByIDProductAndUser(int idProd, int idCart) {
		return cartDetail.findByIDProductAndUser(idProd, idCart);

	}
	
	
}
