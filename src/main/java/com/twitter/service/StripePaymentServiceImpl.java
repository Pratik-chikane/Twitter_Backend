package com.twitter.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentUpdateParams;
import com.stripe.param.checkout.SessionCreateParams;
import java.util.*;

@Service
public class StripePaymentServiceImpl implements IStripePaymentService {

	@Value("${BASE.URL}")
	private String BASE_URL;

	@Value("${STRIPE.SECRET.KEY}")
	private String API_KEY;

	@Value("${MONTHLY_SUBSCRIPTION_COST}")
	private Long MONTHLY_PRICE;
	
	@Value("${YEARLY_SUBSCRIPTION_COST}")
	private Long YEARLY_PRICE;

	@Override
	public Session createSession(String planType, String email) throws Exception {
		String successURL = BASE_URL + "verified/sucess";
		String failedURL = BASE_URL + "verified/failed";
		Stripe.apiKey = API_KEY;

		SessionCreateParams params = SessionCreateParams.builder()

				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT).setCancelUrl(failedURL).setCustomerEmail(email)
			
				.addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
						.setPriceData(SessionCreateParams.LineItem.PriceData.builder().setCurrency("usd")
								.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()

										.setDescription(planType).setName("Twitter Subscription").build())

								.setUnitAmount(planType.equals("monthly") ? MONTHLY_PRICE*100: YEARLY_PRICE *100).build())

						.build())

				.setSuccessUrl(successURL).build();

		return Session.create(params);

	}

}
