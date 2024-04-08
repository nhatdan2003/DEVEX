package com.Devex.Sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SessionScope
@Service("cookieService")
public class CookieService {
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;

	// đọc cookie từ request
	public Cookie get(String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(name)) {
					return cookie;
				}
			}
		}
		return null;
	}

	// đọc giá trị của cookie từ request
	public String getValue(String name, String defaultValue) {
		Cookie cookie = get(name);
		if (cookie != null) {
			return cookie.getValue();
		}
		return defaultValue;
	}

	// tạo và gửi cookie vè client
	public Cookie add(String name, String value, int hours) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(hours * 60 * 60);
		cookie.setPath("/");
		response.addCookie(cookie);
		return cookie;
	}

	// xoá cookie khỏi client
	public void remove(String name) {
		add(name, "", 0);
	}
}
