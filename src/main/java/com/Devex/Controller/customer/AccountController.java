package com.Devex.Controller.customer;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.Devex.DTO.MailOtpDTO;
import com.Devex.DTO.OtpRequestDTO;
import com.Devex.DTO.OtpValidationRequest;
import com.Devex.Entity.Customer;
import com.Devex.Entity.Dwallet;
import com.Devex.Entity.Role;
import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Sevice.CookieService;
import com.Devex.Sevice.CustomerService;
import com.Devex.Sevice.DwalletService;
import com.Devex.Sevice.MailerService;
import com.Devex.Sevice.NotiService;
import com.Devex.Sevice.OTPService;
import com.Devex.Sevice.ParamService;
import com.Devex.Sevice.RoleService;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserRoleService;
import com.Devex.Sevice.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AccountController {
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+84|0)[1-9]\\d{8}$";
    private static final String PHONE_NUMBER_PREFIX = "+84";
	
	@Autowired
	UserService userService;
	
	@Autowired
	NotiService notiService;
	
	@Autowired
	DwalletService dwalletService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	OTPService otpService;
	
	@Autowired
	MailerService emailService;
	
	@Autowired
	SessionService session;
	
	@Autowired
	CookieService cookie;
	
	@Autowired
	ParamService param;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/signup")
	public String showSignup() {
		return "account/email-phone";
	}
	
	// nhập email sdt
	@PostMapping("/signup")
	public String sendSmsSignup(Model model) {
		String info = param.getString("email-phone","");
		System.out.println(info);
		System.out.println(1);
		User user = null;
		System.out.println(2);
		String type = null;
		if (isValidEmail(info)) {
            type = "email";
            user = userService.findEmail(info);
            System.out.println(3);
        }else if (isValidPhoneNumber(info)) {
        	user = userService.findPhone(info);
            // Chuyển đổi định dạng số điện thoại về "+84" nếu cần
            info = normalizePhoneNumber(info);
            System.out.println(3);
            type = "phone";
        }
//		System.out.println(user);
		System.out.println(4);
		if(user != null) {
			model.addAttribute("message", "Email hoặc số điện thoại này đã tồn tại!");
        	return "account/email-phone";
		}
		if(type.equals("phone")) {
			OtpRequestDTO otpRequest = new OtpRequestDTO(info, info);
			otpService.sendSMS(otpRequest);
		}else if(type.equals("email")) {
			otpService.sendMailOtp(info);
		}else {
        	model.addAttribute("message", "Vui lòng nhập đúng định dạng email hoặc số điện thoại");
        	return "account/email-phone";
        }
        session.set("info-user", info);
		return "redirect:/verify";
	}
	
	@GetMapping("/signup-information")
	public String showInfSignup() {
		return "account/signup";
	}
	
	//điền thông tin tài khoản
	@PostMapping("/signup-information")
	public String doInfSignup(Model model) {
		User user = new Customer();
		String username = param.getString("username", "");
		User userExit = userService.findById(username.trim()).orElse(null);
		if(userExit != null) {
			model.addAttribute("message", "Username đã tồn tại!");
			return "account/signup";
		}
		String fullname = param.getString("fullname", "");
		String password = param.getString("password", "123");
		String gender = param.getString("gender", "Other");
		boolean active = true;
		Date createdDate = new Date();
		String email = null;
		String phone = null;
		String info = session.get("info-user", "");
		if (isValidEmail(info)) {
            email = info;
        }else if (isValidPhoneNumber(info)) {
            // Chuyển đổi định dạng số điện thoại về "0.."
        	info = "0" + info.substring(3);

            phone = info;
        }
		user.setUsername(username.trim());
		user.setFullname(fullname.trim());
		user.setPhone(phone);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setCreateDay(createdDate);
		user.setGender(gender);
		user.setActive(active);
		user.setAvatar("avt.webp");
		userService.save(user);
		//tạo ví cho user
		Dwallet dwallet = new Dwallet();
		String id = generateRandomNumber();
		dwallet.setId(id);
		dwallet.setUser(user);
		dwallet.setBalance(0.0);
		dwallet.setActive(true);
		dwalletService.save(dwallet);
		//tạo customer
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setFullname(fullname);
		customer.setPhone(phone);
		customer.setEmail(email);
		customer.setPassword(passwordEncoder.encode(password));
		customer.setCreateDay(createdDate);
		customer.setGender(gender);
		customer.setActive(active);
		customer.setAvatar("avt.webp");
		customer.setAddress(null);
		customer.setPhoneAddress(null);
		customerService.save(customer);
		Role role = roleService.findById("CUSTOMER").orElse(null);
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		userRoleService.save(userRole);
		
		//Gửi thông báo
        notiService.sendNotification(null, user.getUsername(), null, "welcome",
                user.getFullname());
        notiService.sendNotification(null, null, "/ad/edit/" + user.getUsername(), "khachhangmoi",
        		user.getFullname());
		//Lịch sử
        notiService.sendHistory(user.getUsername(), null, null, "welcome", null);
        
		return "redirect:/signin?success=Success signup";
	}
	
	@GetMapping("/verify")
	public String showVerify() {
		return "account/verifi";
	}
	
	//xác thực email sdt
	@PostMapping("/verify")
	public String doVerify(Model model) {
		String otp = "";
		for (int i = 1; i <= 6; i++) {
			otp += param.getString("o" + i,"");
		}
		String infoUser = session.get("info-user", "");
		System.out.println(infoUser);
		String type = null;
		if (isValidEmail(infoUser)) {
            type = "email";
        }else {
            type = "phone";
        }
		boolean flag = false;
		
		if(type.equals("email")) {
			MailOtpDTO mailOtp = new MailOtpDTO(infoUser, otp);
			flag = otpService.validateMailOtp(mailOtp);
		}else if(type.equals("phone")) {
			OtpValidationRequest otpValidationRequest = new OtpValidationRequest(infoUser, otp);
			flag = otpService.validateOtp(otpValidationRequest);
		}
		
		if(flag == false) {
			model.addAttribute("message", "Mã xác thực không trùng khớp!");
			return "account/verifi";
		}
		
		return "redirect:/signup-information";
	}
	
	@GetMapping("/verify-password")
	public String showVerifyPassword() {
		return "account/current-password";
	}
	
	//xác thực pass hiện tại
	@PostMapping("/verify-password")
	public String doVerifyPassword(Model model) {
		User user = session.get("user");
		String pass = param.getString("password", "123");
		String passEncoder = user.getPassword();	// mã hoá pass hiện tại

		if(!passwordEncoder.matches(pass, passEncoder)) {
			model.addAttribute("message", "Mật khẩu không trùng khớp!");
			return "account/current-password";
		}
		return "redirect:/change-password";
	}
	
	@GetMapping("/change-password")
	public String showChangePassword() {
		
		return "account/new-password";
	}
	
	//đổi pass
	@PostMapping("/change-password")
	public String doChangePassword(Model model) {
		User user = session.get("user");
		String pass = param.getString("password", "");
		String confirmPass = param.getString("confirm-password", "");
		if(!confirmPass.equals(pass)) {
			model.addAttribute("message", "Xác thực mật khẩu không trùng khớp!");
			return "account/new-password";
		}
		user.setPassword(passwordEncoder.encode(confirmPass));
		userService.save(user);
		List<UserRole> role = userRoleService.findAllByUserName(user.getUsername());
		for (UserRole u : role) {
			if (u.getRole().getId().equals("ADMIN")) {
				return "redirect:/ad/profile";
			} else if(u.getRole().getId().equals("SELLER")) {
				return "redirect:/seller/profile";
			}
		}
		//Gửi thông báo

		//Lịch sử
        notiService.sendHistory(user.getUsername(), null, null, "changepassword", null);
		return "redirect:/profile";
	}
	
	@GetMapping("/forget-password/information")
	public String showForgetPassInfo() {
		return "account/email-phone";
	}
	
	
	//quên pass nhập email phone
	@PostMapping("/forget-password/information")
	public String doForgetPassInfo(Model model) {
		String info = param.getString("email-phone", session.get("info-user", ""));
		User user = null;
		String type = null;
		if (isValidEmail(info)) {
            type = "email";
            user = userService.findEmail(info);
        }else if (isValidPhoneNumber(info)) {
        	user = userService.findPhone(info);
            // Chuyển đổi định dạng số điện thoại về "+84" nếu cần
            info = normalizePhoneNumber(info);
            type = "phone";
        }
		if(user==null) {
			model.addAttribute("message", "Email hoặc số điện thoại này chưa được đăng kí!");
        	return "account/email-phone";
		}
		if(type.equals("phone")) {
			OtpRequestDTO otpRequest = new OtpRequestDTO(info, info);
			otpService.sendSMS(otpRequest);
		}else if(type.equals("email")) {
			otpService.sendMailOtp(info);
		}else {
        	model.addAttribute("message", "Vui lòng nhập đúng định dạng email hoặc số điện thoại");
        	return "account/email-phone";
        }
        session.set("info-user", info);
		return "redirect:/forget-password/verify";
	}
	
	@GetMapping("/forget-password/verify")
	public String showForgetPassVerify() {
		return "account/verifi";
	}
	
	//xác thực otp
	@PostMapping("/forget-password/verify")
	public String doForgetPassVerify(Model model) {
		String otp = "";
		for (int i = 1; i <= 6; i++) {
			otp += param.getString("o" + i,"");
		}
		String infoUser = session.get("info-user", "");
		System.out.println(infoUser);
		String type = null;
		if (isValidEmail(infoUser)) {
            type = "email";
        }else {
            type = "phone";
        }
		boolean flag = false;
		
		if(type.equals("email")) {
			MailOtpDTO mailOtp = new MailOtpDTO(infoUser, otp);
			flag = otpService.validateMailOtp(mailOtp);
		}else if(type.equals("phone")) {
			OtpValidationRequest otpValidationRequest = new OtpValidationRequest(infoUser, otp);
			flag = otpService.validateOtp(otpValidationRequest);
		}
		
		if(flag == false) {
			model.addAttribute("message", "Mã xác thực không trùng khớp!");
			return "account/verifi";
		}
		
		return "redirect:/forget-password/new-pass";
	}
	

	
	@GetMapping("/forget-password/new-pass")
	public String showForgetPassword() {
		return "account/new-password";
	}
	
	//nhập pass mới
	@PostMapping("/forget-password/new-pass")
	public String doForgetPassword(Model model) {
		User user = null;
		String info = session.get("info-user", "");
		if (isValidEmail(info)) {
			user = userService.findEmail(info);
        }else if (isValidPhoneNumber(info)) {
            // Chuyển đổi định dạng số điện thoại về "0.."
        	info = "0" + info.substring(3);
        	user = userService.findPhone(info);
        }
		
		String pass = param.getString("password", "");
		String confirmPass = param.getString("confirm-password", "");
		if(!confirmPass.equals(pass)) {
			model.addAttribute("message", "Xác thực mật khẩu không trùng khớp!");
			return "account/new-password";
		}
		user.setPassword(passwordEncoder.encode(confirmPass));
		userService.save(user);
		
		//Lịch sử
        notiService.sendHistory(user.getUsername(), null, null, "changepassword", null);
		return "redirect:/signin";
	}
	
	private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
	


    private static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private static String normalizePhoneNumber(String phoneNumber) {
        // Nếu số điện thoại không bắt đầu bằng "+84", chuyển về "+84"
        if (!phoneNumber.startsWith(PHONE_NUMBER_PREFIX)) {
            phoneNumber = PHONE_NUMBER_PREFIX + phoneNumber.substring(1);
        }
        return phoneNumber;
    }
    
    public static String generateRandomNumber() {
        // Khởi tạo đối tượng StringBuilder để xây dựng chuỗi
        StringBuilder randomStringBuilder = new StringBuilder();

        // Sử dụng lớp Random để sinh số ngẫu nhiên từ 0 đến 9 và ghép chúng lại thành một chuỗi
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
            randomStringBuilder.append(digit);
        }

        // Chuyển đối tượng StringBuilder thành chuỗi và trả về
        return randomStringBuilder.toString();
    }
    

}
