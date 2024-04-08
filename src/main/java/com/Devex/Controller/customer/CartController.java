package com.Devex.Controller.customer;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.Entity.Cart;
import com.Devex.Entity.CartDetail;
import com.Devex.Entity.Customer;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Repository.ProductVariantRepository;
import com.Devex.Sevice.CartDetailService;
import com.Devex.Sevice.CartService;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.RecommendationSystem;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.ShoppingCartService;
import com.Devex.Sevice.UserRoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CartController {
	@Autowired
	CartDetailService cartDetailService;
	@Autowired
	CartService cartService;
	@Autowired
	CustomerService customerService;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	SessionService session;
	@Autowired
	ProductService daop;
	@Autowired
	HttpServletResponse resp;
	@Autowired
	HttpServletRequest req;
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	RecommendationSystem recomendationService;

	@Autowired
	ProductVariantService pvService;
	@Autowired
	ProductVariantRepository pv;
	@Autowired
	CartDetailService cartsv;
	ObjectMapper objectMapper = new ObjectMapper();
	Cookie cookie = null;
	
	  @ModelAttribute("admin")
	  public Boolean getAdmin(Principal principal) {
		  User user = session.get("user");
			if (user != null) {
				List<UserRole> roles = userRoleService.findAllByUserName(user.getUsername());
				for (UserRole u : roles) {
					if (u.getRole().getId().equals("ADMIN")) {
						System.out.println("tôi là admin kk");
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
						System.out.println("tôi là seller kk");
						return true;
					}
				}
			}
	      return false;
	  }

	@RequestMapping("/cart")
	public String showcart(Model model) {

		return "user/cartproduct";

	}

	private void addCartItem(int id, int soLuong) {
		ProductVariant pv2 = pvService.findById(id).get();
		User user = session.get("user");
		Customer customer = customerService.findById(user.getUsername()).get();
		Cart cart = cartService.getCartById(customer.getUsername());
		if(cart == null) {
			cart = new Cart();
			cart.setPerson(customer);
			System.out.println(2);
			cartService.save(cart);
		}
		
		CartDetail cartDetail = cartDetailService.findByIDProductAndUser(pv2.getId(), cart.getId());
		
		if(cartDetail != null) {
			cartDetail.setQuantity(cartDetail.getQuantity() + soLuong);
		}else {
			cartDetail = new CartDetail();
			cartDetail.setProductCart(pv2);
			cartDetail.setQuantity(soLuong);
			cartDetail.setCreatedDay(new Date());
			cartDetail.setCart(cart);
		}
		
		cartDetailService.save(cartDetail);	
	}


	@RequestMapping("/cart/add/{idProduct}")
	public String addCart(@PathVariable("idProduct") String id, Model model,
						  @RequestParam(name = "flexRadio", required = false) String size,
						  @RequestParam(name = "flexRadioDefault", required = false) String cloer,
						  @RequestParam(name = "soluong") int soLuong,
						  @RequestParam(name ="action") String action)
			throws JsonProcessingException {
		if ("addcart".equals(action)) {
			int idProductVariant = 0;
			if (size == null) {
				idProductVariant = pvService.findIdProductVaVariantbySize(cloer, id);
			} else {
				idProductVariant = pvService.findIdProductVaVariantbySizeandColor(cloer, size, id);
			}
			addCartItem(idProductVariant, soLuong);

		}
		if ("order".equals(action)) {
			return "redirect:/thanhtoanhoadon";
		}

		return "redirect:/details/{idProduct}";
	}

//	@RequestMapping("/cartproduct/add/{idProduct}")
//	public String addCart(@PathVariable("idProduct") String id, Model model,
//			@RequestParam(name = "soluong") int soLuong,
//			@RequestParam(name = "flexRadio", required = false) String size,
//			@RequestParam(name = "coler", required = false) String cloer) throws JsonProcessingException {
//
//		cart.add(id, soLuong, cloer, size);
//		System.out.println("size" + size);
//		Map<String, CartProdcut> itemsMap = cart.getItems().stream()
//				.collect(Collectors.toMap(CartProdcut::getId, Function.identity()));
//		String cartValue = objectMapper.writeValueAsString(itemsMap);
//		session.set("cartCount", cart.getCount());
//		cart.clear();
//		// Mã hóa chuỗi JSON thành chuỗi Base64
//		byte[] encodedBytes = Base64.encodeBase64(cartValue.getBytes(StandardCharsets.UTF_8));
//		String encodedCartValue = new String(encodedBytes, StandardCharsets.UTF_8);
//		cookie = new Cookie("myCart", encodedCartValue);
//		cookie.setMaxAge(86400);
//		cookie.setPath("/");
//		resp.addCookie(cookie);
//
//		return "redirect:/details/{idProduct}";
//	}

//	@RequestMapping("/cart/remove/{idProduct}")

//	public String remove(@PathVariable("idProduct") String id) throws JsonProcessingException {
//		cart.remove(id);
//		Map<String, CartProdcut> itemsMap = cart.getItems().stream()
//				.collect(Collectors.toMap(CartProdcut::getId, Function.identity()));
//		String cartValue = objectMapper.writeValueAsString(itemsMap);
//		cart.clear();
//		// Giải mã chuỗi Base64
//		byte[] encodedBytes = Base64.encodeBase64(cartValue.getBytes(StandardCharsets.UTF_8));
//		String encodedCartValue = new String(encodedBytes, StandardCharsets.UTF_8);
//		cookie = new Cookie("myCart", encodedCartValue);
//		cookie.setMaxAge(86400);
//		cookie.setPath("/");
//		resp.addCookie(cookie);
//

//		return "redirect:/cart";
//	}

}
