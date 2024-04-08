package com.Devex.Sevice.ServiceImpl;

import java.util.*;

import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.Devex.Entity.Product;
import com.Devex.Repository.CustomerRepository;
import com.Devex.Repository.ProductRepository;
import com.Devex.Repository.UserRepository;
import com.Devex.Sevice.RecommendationSystem;

@SessionScope
@Service("recomendService")
public class RecommendationSystemImpl implements RecommendationSystem {
	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	UserSearchService userSearchService;

	@Autowired
	ProductService productService;

	@Override
	public List<Product> recomendProduct(String username) {
		List<Product> recomendProductList = new ArrayList<>();
		List<Integer> categogyRecomend = customerRepository.recomendationSystem(username);
//		System.out.println(categogyRecomend.toString());
		for (int categogy : categogyRecomend) {
			List<Product> rcmForCate = productRepository.findProductsByCategoryId(categogy);
			recomendProductList.addAll(rcmForCate);
			Collections.shuffle(recomendProductList);
		}
		Collections.shuffle(recomendProductList);
		return recomendProductList;
	}

	@Override
	public List<Product> recomnedProductIfUserIsNull() {
		List<Product> recomendProductList = new ArrayList<>();
		List<String> top10KeySerch = userSearchService.selectTop10();
		Set<Product> uniqueProducts = new LinkedHashSet<>();
        for (String kwords: top10KeySerch) {
            // Tìm tên từ theo từ khóa
            recomendProductList.addAll(productService.findByKeywordName(kwords));
            // Tìm theo shop bán
            recomendProductList.addAll(productService.findProductBySellerUsername("%" + kwords + "%"));
			Collections.shuffle(recomendProductList);
            // FILLTER SẢN PHẨM TRÙNG NHAU
            List<Product> pByC = new ArrayList<>();// hào
            if (recomendProductList.size() > 0) {
                pByC = productService.findProductsByCategoryId(recomendProductList.get(0).getCategoryDetails().getCategory().getId());
            } // hào
            recomendProductList.addAll(pByC);

            recomendProductList.forEach(pr -> {
                uniqueProducts.add(pr);
            });
        }
		recomendProductList = new ArrayList<>(uniqueProducts);
		Collections.shuffle(recomendProductList);
		return recomendProductList;
	}

}
	
