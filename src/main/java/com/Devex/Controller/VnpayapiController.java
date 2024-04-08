package com.Devex.Controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.Devex.Sevice.*;
import org.apache.coyote.http11.upgrade.UpgradeServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.Customer;
import com.Devex.Entity.TransactionHistory;
import com.Devex.Entity.User;
import com.Devex.Sevice.ServiceImpl.MailerServiceImpl;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Controller
public class VnpayapiController {

	@Autowired
	vnPayService vnPayService;

	@Autowired
	MailerServiceImpl mailer;
	@Autowired
	SessionService session;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	TransactionService transactionService;

	@GetMapping("/thanhtoanhoadon")
	public String index() {
		return "user/formOderpay";
	}

	@PostMapping("/submitOrder")
	public String sumbitOder2(HttpServletRequest request) {
		session.set("payment", "vnpay");
		List<CartDetailDTo> list = session.get("listItemOrder", null);
		double total = session.get("total", 0.0);
//		double hihi1 = 0;
//		for (CartDetailDTo cartDetailDTo : list) {
//			hihi1 += (cartDetailDTo.getPrice() * cartDetailDTo.getQuantity());
//		}
		int orderTotalInt = (int) total;

		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		System.out.println(baseUrl);
		String vnpayUrl = vnPayService.createOrder(orderTotalInt, "Thanhtoanhoadon", baseUrl);
		System.out.println(vnpayUrl);



		return "redirect:" + vnpayUrl;

	}

	@PostMapping("/submitOrder1")
	public String submidOrder(@RequestParam("amount") String orderTotal1, @RequestParam("orderInfo") String orderInfo,
			@RequestParam(value = "product_name", required = false) List<String> product_name,
			@RequestParam(value = "product_size", required = false) List<String> product_size,
			@RequestParam(value = "product_color", required = false) List<String> product_color,
			@RequestParam(value = "product_price", required = false) List<Integer> product_price,
			@RequestParam(value = "product_quantity", required = false) List<Integer> quantity,
			@RequestParam(value = "product_img", required = false) List<String> product_img,
			@RequestParam(value = "address", required = false) String address, HttpServletRequest request) {

		int maxLength = Math.max(Math.max(product_name.size(), product_price.size()),
				Math.max(quantity.size(), product_img.size()));

		while (product_name.size() < maxLength) {
			product_name.add("Không có");
		}

		while (product_price.size() < maxLength) {
			product_price.add(0);
		}

		while (quantity.size() < maxLength) {
			quantity.add(0);
		}

		while (product_img.size() < maxLength) {
			product_img.add("Không có");
		}
		while (product_size.size() < maxLength) {
			product_size.add("Không có");
		}
		while (product_color.size() < maxLength) {
			product_color.add("Không có");
		}

//		 for (int i = 0; i < maxLength; i++) {
//		     System.out.println("Sản phẩm " + (i + 1) + ":");
//		     System.out.println("Tên sản phẩm: " + product_name.get(i));
//		     System.out.println("Giá sản phẩm: " + product_price.get(i));
//		     System.out.println("Số lượng: " + quantity.get(i));
//		     System.out.println("Link ảnh: " + product_img.get(i));
//		     System.out.println("Link ảnh: " + product_size.get(i));
//		     System.out.println("Link ảnh: " + product_color.get(i));
//		     System.out.println();
//		 }

		double orderTotal = Double.parseDouble(orderTotal1);
		float orderTotalFloat = Float.parseFloat(orderTotal1);
		int orderTotalInt = (int) orderTotal;
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String vnpayUrl = vnPayService.createOrder(orderTotalInt, orderInfo, baseUrl);
		int list2 = product_name.size();
		// Gửi Email

		return "redirect:" + vnpayUrl;
	}

	@GetMapping("/vnpay-payment")
	public String GetMapping(HttpServletRequest request, Model model) {
		int paymentStatus = vnPayService.orderReturn(request);
		System.out.println("status: " + paymentStatus);
		String orderInfo = request.getParameter("vnp_OrderInfo");
		System.out.println("status: " + orderInfo);
		String paymentTime = request.getParameter("vnp_PayDate");
		String transactionId = request.getParameter("vnp_TransactionNo");
		String totalPrice = request.getParameter("vnp_Amount");
		model.addAttribute("orderId", orderInfo);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("paymentTime", paymentTime);
		model.addAttribute("transactionId", transactionId);

		if (orderInfo.equals("naptien")) {
			return paymentStatus == 1 ? "redirect:/rechargeSuccess?totalPrice=" + totalPrice : "orderfail";
		} 
		return paymentStatus == 1 ? "redirect:order/success" : "orderfail";

	}

	@PostMapping("/sumbitRecharge")
	public String Recharge(@RequestParam("amount") int total, String noteOder, HttpServletRequest request) {
		noteOder = "naptien";
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String vnpayUrl = vnPayService.createOrder(total, noteOder, baseUrl);
		System.out.println(vnpayUrl);
		return "redirect:" + vnpayUrl;

	}

	@Autowired
	private DwalletService dwalletService;
	@Autowired
	private TransactionHistoryService transactionHistoryService;

	@GetMapping("/rechargeSuccess")
	public String rechargeSuccess(Model mdoel, HttpServletRequest request) {

		long balanceIn = Long.parseLong(request.getParameter("totalPrice")) / 100;
		int intValue = (int) balanceIn;
		double doubleValue = (double) balanceIn;
		User user = session.get("user");
		transactionService.transactionDwallet(user.getUsername(), user.getUsername(), doubleValue, "vnpayInToWallet");

		return "user/paymentSuccess";
	}

}
