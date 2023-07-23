package com.Devex.Sevice;

import java.util.Collection;
import java.util.Map;

import com.Devex.Entity.CartProdcut;
import com.Devex.Entity.Product;



public interface ShoppingCartService {

	CartProdcut add(String i);
	void remove(String id);
	CartProdcut update(String id, int qty);
	void clear();
	Collection<CartProdcut> getItems();
	int getCount();
	double getAmount();
	void setItems(Map<String, CartProdcut> items);

	
}