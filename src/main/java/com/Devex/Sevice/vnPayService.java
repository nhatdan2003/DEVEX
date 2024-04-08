package com.Devex.Sevice;

import jakarta.servlet.http.HttpServletRequest;


public interface vnPayService {

	public String createOrder(int total, String orderInfor, String urlReturn);
	
	public int orderReturn(HttpServletRequest request);
}
