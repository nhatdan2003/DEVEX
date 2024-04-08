package com.Devex.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Entity.History;
import com.Devex.Entity.Product;
import com.Devex.Entity.User;
import com.Devex.Sevice.HistoryService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.SessionService;

@Controller
public class HistoryProductControler {

	@Autowired
	HistoryService historyService;
	@Autowired
	ProductService productService;
	@Autowired
    SessionService session;
	@RequestMapping("/history")
	public String home(Model model) {
		User user = session.get("user");
		if (user != null) {
		    List<Product> sp = new ArrayList<>();
		    List<History> history = historyService.findByIdUser(user);

		    for (History history2 : history) {
		        Optional<Product> product = productService.findById(history2.getProductId());
		        if (product.isPresent()) {
		            Product product2 = product.get();
		            
		            // Kiểm tra xem sản phẩm đã tồn tại trong danh sách sp hay chưa
		            boolean isProductInList = false;
		            for (Product existingProduct : sp) {
		                if (existingProduct.getId() == product2.getId()) {
		                    isProductInList = true;
		                    break;
		                }
		            }
		            
		            // Nếu sản phẩm chưa tồn tại trong danh sách, thêm nó vào danh sách
		            if (!isProductInList) {
		                sp.add(product2);
		            }
		        }
		    }
		    
		    // Thêm danh sách sản phẩm vào thuộc tính "products" của model
		    model.addAttribute("products", sp);
		}

		 

		return "user/historyProduct";
		
		
	///Để đây khánh nghiên cứu lại
		
//		User user = session.get("user");
//		if (user != null) {
//		    Set<Product> uniqueProducts = new HashSet<>();
//		    List<History> history = historyService.findByIdUser(user);
//
//		    for (History history2 : history) {
//		        Optional<Product> productOptional = productService.findById(history2.getProductId());
//		        productOptional.ifPresent(uniqueProducts::add);
//		    }
//
//		    List<Product> sp = new ArrayList<>(uniqueProducts);
//		    model.addAttribute("product1", sp);
//		}
//
//	    
//	    
//
//	    return "user/historyProduct";
	}

}
