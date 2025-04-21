package com.Devex.Controller.api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.DTO.RequestDataDTO;
import com.Devex.DTO.SizeColorDTO;
import com.Devex.Entity.CartDetail;
import com.Devex.Entity.Customer;
import com.Devex.Entity.FlashSale;
import com.Devex.Entity.FlashSaleTime;
import com.Devex.Entity.Order;
import com.Devex.Entity.OrderDetails;
import com.Devex.Entity.Product;
import com.Devex.Entity.ProductVariant;
import com.Devex.Entity.User;
import com.Devex.Entity.Voucher;
import com.Devex.Sevice.CartDetailService;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.FlashSalesService;
import com.Devex.Sevice.FlashSalesTimeService;
import com.Devex.Sevice.OrderDetailService;
import com.Devex.Sevice.OrderService;
import com.Devex.Sevice.OrderStatusService;
import com.Devex.Sevice.PaymentService;
import com.Devex.Sevice.ProductService;
import com.Devex.Sevice.ProductVariantService;
import com.Devex.Sevice.SessionService;

@CrossOrigin("*")
@RestController
public class CartAPIController {
	@Autowired
	private CartDetailService cart;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ProductService productService;

	@Autowired
	private FlashSalesService flashSalesService;

	@Autowired
	CustomerService customerService;

	@Autowired
	OrderService orderService;

	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private OrderStatusService orderStatusService;

	@Autowired
	private ProductVariantService productVariantService;

	@Autowired
	private FlashSalesTimeService flashSalesTimeService;

	@GetMapping("/rest/cart")
	public List<CartDetailDTo> getAll(Model model) {
		User user = sessionService.get("user");
		Customer customer = null;
		List<CartDetailDTo> cartDetails = new ArrayList<>();
		if (user != null) {
			customer = customerService.findById(user.getUsername()).orElse(null);
			if (customer != null) {
				cartDetails = cart.findAllCartDTO(customer.getUsername());
			}

		}
		List<FlashSale> fs = flashSalesService.findAll();
		LocalDateTime currentTime = LocalDateTime.now();
		for (CartDetailDTo cartDetailDTo : cartDetails) {
			if (fs != null) {
				for (FlashSale flashSale : fs) {
					LocalDateTime firstTime = flashSale.getFlashSaleTime().getFirstTime();
					LocalDateTime lastTime = flashSale.getFlashSaleTime().getLastTime();
					if (!(currentTime.isAfter(firstTime) && currentTime.isBefore(lastTime))) {
						cartDetailDTo.setIdFlashSale(null);
					}

				}
			}

		}

		// Customer customer = customerService.findById(user.getUsername()).get();
		// List<CartDetailDTo> cartDetails =
		// cart.findAllCartDTO(customer.getUsername());
		// Map<String, CartDetailDTo> cartDetailMap = new HashMap<>();
		//
		// for (CartDetailDTo cartDetail : cartDetails) {
		// String uniqueKey = cartDetail.getImg() + "-" + cartDetail.getColor() + "-" +
		// cartDetail.getSize();
		// if (cartDetailMap.containsKey(uniqueKey)) {
		// CartDetailDTo existingCartDetail = cartDetailMap.get(uniqueKey);
		// existingCartDetail.setQuantity((existingCartDetail.getQuantity() +
		// cartDetail.getQuantity()));
		// } else {
		// cartDetailMap.put(uniqueKey, cartDetail);
		// }
		// }
		// for (CartDetailDTo cartDetail : cartDetailMap.values()) {
		// int totalQuantity = cartDetail.getQuantity();
		// if (totalQuantity == 2 ||totalQuantity == 3||totalQuantity == 4
		// ||totalQuantity == 5 ) {
		// int newQuantity = 1;
		// cartDetail.setQuantity(newQuantity);
		//
		// } else {
		// int newQuantity = (int) Math.sqrt(totalQuantity);
		// cartDetail.setQuantity(newQuantity);
		// }
		// // Lấy căn bậc hai của tổng số lượng
		//
		// }
		// return new ArrayList<>(cartDetailMap.values());

		return cartDetails;
	}

	@DeleteMapping("/rest/cart/{id}")
	public ResponseEntity<Void> deleteCartDetail(@PathVariable("id") int id) {
		Optional<CartDetail> optionalCartDetail = cart.findById(id);

		if (optionalCartDetail.isPresent()) {
			cart.delete(optionalCartDetail.get());
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/rest/cart")
	public ResponseEntity<Void> deleteCartDetailAll() {
		cart.deleteAll();
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/rest/cart/shop/{idShop}")
	public ResponseEntity<Void> deleteCartDetailsByShopId(@PathVariable("idShop") String idShop) {
		List<CartDetail> listNew = new ArrayList<>();
		List<CartDetail> list = cart.findAll();
		list.forEach(cartDetail -> {
			if (cartDetail.getProductCart().getProduct().getSellerProduct().getUsername().equals(idShop)) {
				listNew.add(cartDetail);
			}
		});
		cart.deleteAllInBatch(listNew);

		return ResponseEntity.ok().build();
	}

	@PutMapping("/rest/cart/{id}")
	public ResponseEntity<CartDetailDTo> updateCartDetail(@PathVariable int id,
			@RequestBody CartDetailDTo updatedCartDetail) {
		CartDetail cartDetail = cart.findById(id).get();

		if (cartDetail != null) {
			cartDetail.setQuantity(updatedCartDetail.getQuantity());
			cartDetail.setCreatedDay(new Date());
			cart.save(cartDetail);
			return ResponseEntity.ok(updatedCartDetail);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/rest/cart/order")
	public ResponseEntity<Void> paymentOrder(@RequestBody RequestDataDTO requestData) {

		List<CartDetailDTo> listOrder = requestData.getItemsOrderSession();
		List<Voucher> listVoucher = requestData.getVoucherApply();
		Double total = requestData.getTotal();
		sessionService.set("listItemOrder", listOrder);
		sessionService.set("listVoucherApply", listVoucher);
		sessionService.set("total", total);
		return ResponseEntity.ok().build();
	}

	// @PostMapping("/rest/cart/order-true")
	// public ResponseEntity<Void> order(@RequestBody List<CartDetailDTo> listOrder)
	// {
	//// Customer user = sessionService.get("user");
	// User user = sessionService.get("user");
	// Customer customer = null;
	// if (user != null) {
	// customer = customerService.findById(user.getUsername()).get();
	// }
	//// Customer customer = customerService.findById(user.getUsername()).get();
	// Order order = new Order();
	// order.setCreatedDay(new Date());
	// System.out.println(new Date());
	// order.setNote("Đóng gói kĩ và giao vào giờ hành chính");
	// order.setAddress(customer.getAddress());
	// order.setPhone(customer.getPhoneAddress());
	// order.setPriceDiscount(0.0);
	// order.setCustomerOrder(customer);
	// order.setOrderStatus(orderStatusService.findById(1001).get());
	// order.setPayment(paymentService.findById(1001).get());
	// order.setTotal(listOrder.stream().mapToDouble(item -> item.getQuantity() *
	// item.getPrice()).sum());
	// orderService.save(order);
	//
	// for (CartDetailDTo item : listOrder) {
	// OrderDetails orderDetails = new OrderDetails();
	// Order orders = orderService.findLatestOrder();
	// orderDetails.setOrder(orders);
	// orderDetails.setPrice(item.getPrice());
	// CartDetail cartDetail = cart.getById(item.getId());
	// int id = cartDetail.getProductCart().getId();
	// ProductVariant prod = productVariantService.findById(id).get();
	// orderDetails.setProductVariant(prod);
	// orderDetails.setQuantity(item.getQuantity());
	// int totalquantity = prod.getQuantity();
	// int countquantity = totalquantity - item.getQuantity();
	// productVariantService.updateQuantity(countquantity, prod.getId());
	// orderDetails.setStatus(orderStatusService.findById(1001).get());
	// orderDetailService.save(orderDetails);
	// cart.deleteById(item.getId());
	// }
	//
	// return ResponseEntity.ok().build();
	// }

	// @PostMapping("/rest/cart")
	// public ResponseEntity<String> createCartDetail(@RequestBody CartDetail
	// cartDetail) {
	// CartDetail savedCartDetail = cart.save(cartDetail);
	// return ResponseEntity.ok("Cart detail created successfully with ID: " +
	// savedCartDetail.getId());
	// }
	//
	// @PutMapping("/rest/cart/{id}")
	// public ResponseEntity<String> updateCartDetail(@PathVariable int id,
	// @RequestBody CartDetail updatedCartDetail) {
	// Optional<CartDetail> optionalCartDetail = cart.findById(id);
	//
	// if (optionalCartDetail.isPresent()) {
	// CartDetail existingCartDetail = optionalCartDetail.get();
	// // Cập nhật thông tin của existingCartDetail từ updatedCartDetail
	// existingCartDetail.setProductCart(updatedCartDetail.getProductCart());
	// existingCartDetail.setCart(updatedCartDetail.getCart());
	// existingCartDetail.setQuantity(updatedCartDetail.getQuantity());
	//
	// cart.save(existingCartDetail);
	// return ResponseEntity.ok("Cart detail updated successfully.");
	// } else {
	// return ResponseEntity.notFound().build();
	// }
	// }

	@GetMapping("/rest/cart/size/{id}")
	public List<String> size(@PathVariable("id") String id) {
		List<ProductVariant> pv = productVariantService.findAllProductVariantByProductId(id);
		List<String> sizes = new ArrayList<>();
		for (ProductVariant p : pv) {
			String size = p.getSize();
			if (!sizes.contains(size)) {
				// Nếu chưa tồn tại, thêm phần tử vào danh sách
				sizes.add(size);
			}
		}
		if (sizes.size() < 0) {
			return null;
		}
		return sizes;
	}

	@GetMapping("/rest/cart/color/{id}")
	public List<String> color(@PathVariable("id") String id) {
		List<ProductVariant> pv = productVariantService.findAllProductVariantByProductId(id);
		List<String> colors = new ArrayList<>();
		for (ProductVariant p : pv) {
			String color = p.getColor();
			if (!colors.contains(color)) {
				// Nếu chưa tồn tại, thêm phần tử vào danh sách
				colors.add(color);
			}
		}
		if (colors.isEmpty()) {
			return null;
		}
		return colors;
	}

	@PutMapping("/rest/cart/changeSizenColor/{id}")
	public ResponseEntity<ProductVariant> changeSizenColor(@PathVariable("id") String id,
			@RequestParam("cartDetailId") int cartDetailId, @RequestBody SizeColorDTO sizeColorDTO) {
		List<ProductVariant> pvList = productVariantService.findAllProductVariantByProductId(id);
		ProductVariant item = null;
		System.out.println(cartDetailId);
		System.out.println(sizeColorDTO.getSize());
		System.out.println(sizeColorDTO.getColor());
		List<String> colors = new ArrayList<>();
		List<String> sizes = new ArrayList<>();
		for (ProductVariant p : pvList) {
			if (!colors.contains(p.getColor())) {
				colors.add(p.getColor());
			}
			if (!sizes.contains(p.getSize())) {
				sizes.add(p.getSize());
			}
		}
		for (ProductVariant p : pvList) {
			if (colors.size() == 1) {
				if (p.getSize().equalsIgnoreCase(sizeColorDTO.getSize())) {
					item = p;
					CartDetail cd = cart.findById(cartDetailId).get();
					cd.setProductCart(item);
					cd.setCreatedDay(new Date());
					cart.save(cd);
					break;
				}
			} else if (sizes.size() == 1) {
				if (p.getColor().equalsIgnoreCase(sizeColorDTO.getColor())) {
					item = p;
					CartDetail cd = cart.findById(cartDetailId).get();
					cd.setProductCart(item);
					cd.setCreatedDay(new Date());
					cart.save(cd);
					break;
				}
			} else {
				if (p.getColor().equalsIgnoreCase(sizeColorDTO.getColor())
						&& p.getSize().equalsIgnoreCase(sizeColorDTO.getSize())) {
					item = p;
					CartDetail cd = cart.findById(cartDetailId).get();
					cd.setProductCart(item);
					cd.setCreatedDay(new Date());
					cart.save(cd);
					break;
				}
			}
		}
		if (item != null) {
			return ResponseEntity.ok(item); // Trả về 200 OK nếu tìm thấy
		} else {
			return ResponseEntity.notFound().build(); // Trả về 404 Not Found nếu không tìm thấy
		}
	}

	@GetMapping("/rest/getTimeFlashSale")
	public FlashSaleTime getTimeFlashSale() {
		FlashSaleTime flashSaleTime = flashSalesTimeService.findFlashSaleTimesByTimeNow();

		return flashSaleTime;

	}
}
