package com.Devex.Controller.customer;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserRoleService;

@Controller
public class MessageController {
	
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
	  
	@GetMapping("/message")
	public String showMessage(Model model) {
		return "user/message";
	}
}
