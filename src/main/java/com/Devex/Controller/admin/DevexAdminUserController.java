package com.Devex.Controller.admin;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Devex.Entity.User;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;

@Controller
@RequestMapping("/ad")
public class DevexAdminUserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	SessionService sessionService;

	@GetMapping("/userManage")
	public String getUserManage(ModelMap model) {
		List<User> user = userService.findAll();

		model.addAttribute("user", user);

		return "admin/userManage/userManage";
	}

	@GetMapping("/edit/{username}")
	public String editUser(@PathVariable("username") String username) {
		sessionService.set("username", username);
		return "admin/userManage/formUserManage";
	}

}
