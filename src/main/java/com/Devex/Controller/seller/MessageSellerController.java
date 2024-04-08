package com.Devex.Controller.seller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageSellerController {
	@GetMapping("/seller/message")
	public String showMessage(Model model) {
		return "seller/message";
	}
}
