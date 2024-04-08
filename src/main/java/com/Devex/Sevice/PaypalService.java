package com.Devex.Sevice;

import java.util.List;

import com.Devex.DTO.CartDetailDTo;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;


public interface PaypalService {

	Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

	Payment getPaymentDetails(String paymentId) throws PayPalRESTException;

	String authorizePayment(List<CartDetailDTo> cartDetailDTo) throws PayPalRESTException;


}