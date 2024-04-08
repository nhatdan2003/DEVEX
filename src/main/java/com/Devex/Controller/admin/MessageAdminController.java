package com.Devex.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageAdminController {
	@GetMapping("/ad/message")
	public String showMessage(Model model) {
		return "admin/message";
	}
}
