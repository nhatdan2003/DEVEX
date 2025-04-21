package com.Devex.Controller.customer;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Devex.Entity.History;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Repository.ProductRepository;
import com.Devex.Sevice.HistoryService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller

public class ProductDetailController {

	@Autowired
	UserService userService;
	@Autowired
	HistoryService historyService;
	@Autowired
	ProductService productService;

	@Autowired
	ProductVariantService productVariantService;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	SessionService session;

	@Autowired
	UserRoleService userRoleService;

	@ModelAttribute("admin")
	public Boolean getAdmin(Principal principal) {
		User user = session.get("user");
		if (user != null) {
			List<UserRole> roles = userRoleService.findAllByUserName(user.getUsername());
			for (UserRole u : roles) {
				if (u.getRole().getId().equals("ADMIN")) {
					// System.out.println("tôi là admin kk");
					return true;
				}
			}
		}
		return false;
	}

	@ModelAttribute("seller")
	public Boolean getSeller(Principal principal) {
		User user = session.get("user");
		if (user != null) {
			List<UserRole> roles = userRoleService.findAllByUserName(user.getUsername());
			for (UserRole u : roles) {
				if (u.getRole().getId().equals("SELLER")) {
					// System.out.println("tôi là seller kk");
					return true;
				}
			}
		}
		return false;
	}

	@RequestMapping("/details/{id}")
	public String details(ModelMap model, @PathVariable("id") String id, HttpServletRequest request) {
		// System.out.println("SSS" + id);
		session.set("url", "/details/" + id);
		// Lưu Lịch Sử Sản phẩm
		User user = session.get("user");
		if (user != null) {
			History history = new History();
			history.setProductId(id);
			history.setUser(user);
			historyService.save(history);

		}

		// end.
		Product seller = productService.findProductById(id);
		Product product = productService.findById(id).orElse(new Product());
		// System.out.println(product.getActive());
		// System.out.println("SSSS" +
		// product.getProductVariants().get(0).getPriceSale());
		List<String> listSize = new ArrayList<>();
		product.getProductVariants().forEach(sv -> {
			if (!listSize.contains(sv.getSize())) {
				listSize.add(sv.getSize());
			}
		});

		// Đếm số lượng đánh giá và Tổng sao
		model.addAttribute("commentCount", productService.getCommentCount(id));
		model.addAttribute("starAverage", productService.getStarAverage(id));
		// ListColor
		List<String> listColor = new ArrayList<>();

		product.getProductVariants().forEach(sv -> {
			if (!listColor.contains(sv.getColor())) {
				listColor.add(sv.getColor());
			}
		});

		String[] name = product.getName().split("\\s+");
		List<Product> list = new ArrayList<>();
		Set<Product> uniqueProducts = new HashSet<>(); // check trùng
		// sản phẩm của shop
		List<Product> listPrS = productService
				.findProductBySellerUsername("%" + seller.getSellerProduct().getUsername() + "%");
		listPrS.remove(product);
		// Tìm tên từ theo từ khóa
		for (int i = 0; i < 2; i++) {
			list.addAll(productService.findByKeywordName(name[i]));
			list.forEach(pr -> {
				uniqueProducts.add(pr);
			});

		}
		// Chuyển đổi lại thành danh sách (List)
		List<Product> uniqueProductList = new ArrayList<>(uniqueProducts);
		// Tìm theo shop bán
		uniqueProductList.removeAll(
				productService.findProductBySellerUsername("%" + seller.getSellerProduct().getUsername() + "%"));
		// Collections.shuffle(uniqueProductList);

		boolean shop = true;
		int flag = 0;
		if (user != null) {
			// kiểm tra xem người đó co phan quyen customer không
			for (UserRole role : user.getRoles()) {
				if (role.getRole().getId().equalsIgnoreCase("CUSTOMER")) {

					flag++;
				}
				// System.out.println(role.getRole().getId());
			}
			// Kieem tra xem có phải chính shop đó đăn bán không
			if (user.getUsername().equals(seller.getSellerProduct().getUsername()) || flag == 0) {
				shop = false;
			}
		}
		model.addAttribute("shopProduct", listPrS); // sản phẩm khác của shop
		model.addAttribute("products", uniqueProductList);// gợi ý sản phẩm theo keyword
		model.addAttribute("listColor", listColor);
		model.addAttribute("listSize", listSize);
		model.addAttribute("product", product);
		model.addAttribute("sellerObj", seller);
		model.addAttribute("id", product.getId());
		model.addAttribute("shop", shop);
		return "user/productDetail";

	}

	// Phương thức nhận dữ liệu từ request
	@RequestMapping(value = ("/data"), method = RequestMethod.POST)
	@ResponseBody
	public Double Data(@RequestParam("id") String id, @RequestParam("color") String color,
			@RequestParam("size") String size) {
		Double price = productVariantService.findPriceByColorAndSize(id, color, size);
		return price;
	}

	@RequestMapping(value = ("/quantityproductvariant"), method = RequestMethod.POST)
	@ResponseBody
	public int quantityProductVariant(@RequestParam("id") String id, @RequestParam("color") String color,
			@RequestParam("size") String size) {
		ProductVariant p = productVariantService.findProductVariantByColorAndSizeAndIdProduct(id, color, size);
		int quantity = p.getQuantity();
		return quantity;
	}

}
