package com.Devex.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;


@Controller
public class SigninController {
	@Autowired
	UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	ParamService param;
	

	@GetMapping("/signin")
	public String showSignin(Model model, Principal principal) {
		if(principal != null) {
//			User user = userService.findById(principal.getName()).get();
			List<UserRole> role = userRoleService.findAllByUserName(principal.getName());
			for (UserRole u : role) {
				System.out.println(u.getRole().getId());
				if (u.getRole().getId().equals("ADMIN")) {
					return "redirect:/ad/home";
				} else if(u.getRole().getId().equals("SELLER")) {
					return "redirect:/seller/home";
				}
			}
			return "redirect:/home";
		}
//		model.addAttribute("username", cookie.getValue("username", ""));
//		model.addAttribute("password", cookie.getValue("password", ""));
		return "account/signin";
	}
	
	@GetMapping("/signout")
    public String doSignout() {
		session.remove("user");
		return "redirect:/home";
    }
	
	@GetMapping("/404")
    public String get404() {
		return "admin/erorr404";
    }
	
	@GetMapping("/auth/login/error")
    public String getEmailErrorLogin() {
		return "admin/lockAccount";
    }
	
	

	@PostMapping("/signin")
	public String doSignin(Model model) {
		String username = param.getString("username", "");
		String pass = param.getString("password", "");
		boolean remember = param.getBoolean("remember", false);
		User user = userService.checkLogin(username, pass);
		System.out.println(user.getFullname());
		if(user.getActive()) {
			session.set("user", user);
			if(remember == true) {
				cookie.add("username", username, 10);
				cookie.add("password", pass, 10);
			}else {
				cookie.remove("username");
				cookie.remove("password");
			}
			

			List<UserRole> roles = user.getRoles();
		    for (UserRole role : roles) {
		        String roleName = role.getRole().getId();
		        session.set("user", user);
		        if (roleName.equalsIgnoreCase("ADMIN")) {
		            return "redirect:/admin/home";
		        }else if (roleName.equalsIgnoreCase("SELLER")) {
		            return "redirect:/seller/home";
		        }else if (roleName.equalsIgnoreCase("CUSTOMER")) {
		            return "redirect:/home";
		        }
		    }
		    return "redirect:/home";
			
//				if(user.getRoles().getName().equalsIgnoreCase("Customer")) {
//					session.set("user", user);
//					return "redirect:/home";
//				}
//				if(user.getRole().getName().equalsIgnoreCase("Seller")) {
//					session.set("user", user);
//					return "redirect:/seller/home";
//				}
//				else {
//					session.set("user", user);
//					return "redirect:/admin/home";
//				}
		}else {
			model.addAttribute("message", "Tài khoản này đã bị khoá!");
			return "account/signin";
		}
	}

}
