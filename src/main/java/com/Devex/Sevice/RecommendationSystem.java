package com.Devex.Sevice;
import java.util.List;

import com.Devex.Entity.Product;
public interface RecommendationSystem {
	List<Product> recomendProduct(String username);

	List<Product> recomnedProductIfUserIsNull();

}
