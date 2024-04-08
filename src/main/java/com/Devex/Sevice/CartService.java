package com.Devex.Sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.Devex.Entity.Cart;

public interface CartService {

	void deleteAll();

	void delete(Cart entity);

	Cart getById(Integer id);

	void deleteById(Integer id);

	long count();

	boolean existsById(Integer id);

	Optional<Cart> findById(Integer id);

	List<Cart> findAllById(Iterable<Integer> ids);

	List<Cart> findAll();

	Page<Cart> findAll(Pageable pageable);

    Cart getCartById(String username);

    List<Cart> findAll(Sort sort);

	Cart save(Cart entity);

	Double getCountCartByProductShopAndCreatedDay(String username, int year, int month);

}
