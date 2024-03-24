package com.twitter.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface IStripePaymentService {
	
	public Session createSession(String planType,String email) throws Exception;

}
