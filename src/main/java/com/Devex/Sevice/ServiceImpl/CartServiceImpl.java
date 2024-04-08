package com.Devex.Sevice.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Cart;
import com.Devex.Repository.CartRespository;
import com.Devex.Sevice.CartService;

@SessionScope
@Service("cartService")
public class CartServiceImpl implements CartService{
	@Autowired
	private CartRespository cart;

	@Override
	public Cart save(Cart entity) {
		return cart.save(entity);
	}


	@Override
	public Cart getCartById(String username) {
		return cart.getCartById(username);
	}

	@Override
	public List<Cart> findAll(Sort sort) {
		return cart.findAll(sort);
	}

	@Override
	public Page<Cart> findAll(Pageable pageable) {
		return cart.findAll(pageable);
	}

	@Override
	public List<Cart> findAll() {
		return cart.findAll();
	}

	@Override
	public List<Cart> findAllById(Iterable<Integer> ids) {
		return cart.findAllById(ids);
	}

	@Override
	public Optional<Cart> findById(Integer id) {
		return cart.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return cart.existsById(id);
	}

	@Override
	public long count() {
		return cart.count();
	}

	@Override
	public void deleteById(Integer id) {
		cart.deleteById(id);
	}

	@Override
	public Cart getById(Integer id) {
		return cart.getById(id);
	}

	@Override
	public void delete(Cart entity) {
		cart.delete(entity);
	}

	@Override
	public void deleteAll() {
		cart.deleteAll();
	}

	@Override
	public Double getCountCartByProductShopAndCreatedDay(String username, int year, int month) {
		return cart.getCountCartByProductShopAndCreatedDay(username, year, month); 
	}
	
}
