package com.Devex.Config;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Devex.Entity.UserRole;
import com.Devex.Sevice.UserRoleService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private final UserRoleService userRoleService;

	@Autowired
	public CustomAuthorizationFilter(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Cập nhật quyền ngay lập tức cho tài khoản hiện tại
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String username = authentication.getName();
			// userRoleService.findAllByUserName(username)
			List<UserRole> list = userRoleService.findAllByUserName(username);
			if (list != null) {

				for (UserRole roleUser : list) {

					List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
					updatedAuthorities.add(new SimpleGrantedAuthority(roleUser.getRole().getId()));

					Authentication updatedAuthentication = new UsernamePasswordAuthenticationToken(
							authentication.getPrincipal(), authentication.getCredentials(), updatedAuthorities);

					SecurityContextHolder.getContext().setAuthentication(updatedAuthentication);

				}
			}

		}

		filterChain.doFilter(request, response);
	}
}
