package com.Devex.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import com.Devex.Entity.User;
import com.Devex.Entity.UserRole;
import com.Devex.Sevice.SessionService;
import com.Devex.Sevice.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@Transactional
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
    private UserDetailsService userDetailsService;
	
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
		
		
		System.out.println("Authentication name: " + authentication.getName());
		String name = authentication.getName();
		CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
//		OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
//		System.out.println(oauthUser);
//		CustomOAuth2User oauth2User = new CustomOAuth2User(oauthUser);
		System.out.println(oauth2User);
		String fullname = oauth2User.getName();
		System.out.println(fullname);
		String email = oauth2User.getEmail();
		System.out.println(email);
		String str = userService.processOAuthPostLogin(fullname, email);
		System.out.println(str);
		User user = userService.findEmail(email);
		System.out.println(user.getUsername());
		session.set("user", user);

		// Lấy thông tin người dùng từ authentication
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
//		updatedAuthorities.clear();
//		updatedAuthorities.add(new SimpleGrantedAuthority("CUSTOMER"));

//		// Tạo lại đối tượng Authentication với vai trò mới
//		Authentication newAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
//				authentication.getCredentials(), updatedAuthorities);
//
//		// Set lại Authentication trong SecurityContextHolder
//		SecurityContextHolder.getContext().setAuthentication(newAuthentication);
		if (str.equals("new")) {
			// Tạo lại đối tượng Authentication với vai trò mới
			updatedAuthorities.clear();
			updatedAuthorities.add(new SimpleGrantedAuthority("CUSTOMER"));
			
			// Lấy UserDetails từ UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            
			// Tạo lại đối tượng Authentication với vai trò mới
			Authentication newAuthentication = new UsernamePasswordAuthenticationToken(userDetails,
					authentication.getCredentials(), updatedAuthorities);
			// Set lại Authentication trong SecurityContextHolder
			System.out.println(1);
			SecurityContextHolder.getContext().setAuthentication(newAuthentication);
			System.out.println(2);
		} else {
			// Tạo lại đối tượng Authentication với vai trò mới
			updatedAuthorities.clear();
			for(UserRole role : user.getRoles()) {
				updatedAuthorities.add(new SimpleGrantedAuthority(role.getRole().getId()));
			}
			
			// Lấy UserDetails từ UserDetailsService
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            
			// Tạo lại đối tượng Authentication với vai trò mới
			Authentication newAuthentication = new UsernamePasswordAuthenticationToken(userDetails,
					authentication.getCredentials(), updatedAuthorities);
			// Set lại Authentication trong SecurityContextHolder
			System.out.println(3);
			SecurityContextHolder.getContext().setAuthentication(newAuthentication);
			System.out.println(4);
		}

		// Tiến hành phân quyền
		for (GrantedAuthority authority : updatedAuthorities) {
			System.out.println(authority.getAuthority());
			System.out.println(2);
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
		System.out.println(url);
		if(url != null) {
			response.sendRedirect(url);
			return;
		}
		// Mặc định chuyển hướng đến "/home" cho các vai trò khác
		response.sendRedirect("/home");
	}

}
