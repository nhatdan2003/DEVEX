package com.Devex.Config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.Devex.Entity.User;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
    UserService userService;
	
	@Autowired
    SessionService session;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
        // Kiểm tra trạng thái "active" của tài khoản
//        if (!authentication. .getActive()) {
//            // Nếu tài khoản không active, từ chối đăng nhập
//            response.sendRedirect("/signin?error=Tài khoản bạn đã bị khoá");
//            return;
//        }
        
        // Lấy thông tin người dùng từ authentication
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		// Tiến hành phân quyền
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals("ADMIN")) {
				// Nếu có vai trò "ADMIN", chuyển hướng đến "/admin/home"
				response.sendRedirect("/ad/home");
				return;
			} else if (authority.getAuthority().equals("MANAGER")) {
				// Nếu có vai trò "MANAGER", chuyển hướng đến "/manager/home"
				response.sendRedirect("/manager/home");
				return;
			} else if (authority.getAuthority().equals("SELLER")) {
				// Nếu có vai trò "SELLER", chuyển hướng đến "/seller/home"
				response.sendRedirect("/seller/home");
				return;
			}
		}
		String url = session.get("url");
		if(url != null) {
			response.sendRedirect(url);
			return;
		}
		// Mặc định chuyển hướng đến "/home" cho các vai trò khác
		response.sendRedirect("/home");
	}
	
	
}
