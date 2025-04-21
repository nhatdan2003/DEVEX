package com.Devex.Controller.customer;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.Devex.DTO.CartDetailDTo;
import com.Devex.Entity.Customer;
import com.Devex.Entity.User;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.PaypalService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.ServiceImpl.MailerServiceImpl;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaypalController {
	@Autowired
	PaypalService paypalService;

	@Autowired
	SessionService session;
	@Autowired
	MailerServiceImpl mailer;

	@Autowired
	CustomerService customerService;

	@PostMapping("/paypal-payment")
	public String authorizePayment(Model model) {
		List<CartDetailDTo> list = session.get("listItemOrder", null);
		System.out.println(list.get(0).getPrice());
		try {
			String approvalLink = paypalService.authorizePayment(list);
			session.set("payment", "paypal");
			return "redirect:" + approvalLink;

		} catch (PayPalRESTException ex) {
			ex.printStackTrace();
			return "admin/erorr404";
		}

	}
}
