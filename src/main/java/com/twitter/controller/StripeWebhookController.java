
package com.twitter.controller;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.net.Webhook;
import com.twitter.model.User;
import com.twitter.repository.UserRepository;
import com.twitter.service.IUserService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import com.stripe.model.checkout.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripe/webhook")
public class StripeWebhookController {

	@Value("${WEBHOOK.SECRET.KEY}")
	private String webhookSecret;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IUserService userService;

	@Value("${MONTHLY_SUBSCRIPTION_COST}")
	private Long MONTHLY_PRICE;
	
	@Value("${YEARLY_SUBSCRIPTION_COST}")
	private Long YEARLY_PRICE;

	@PostMapping("/stripe-webhook")
	public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,

			@RequestHeader("Stripe-Signature") String signature) {

		System.out.println("+++++++++++++++++++++++");
		try {

			Event event = Webhook.constructEvent(payload, signature, webhookSecret);

			if ("payment_intent.succeeded".equals(event.getType())) {
		
				PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();

				System.out.println("@@@@@@@@@@@@@@@@@@@@" + paymentIntent);
				long amount = paymentIntent.getAmount();
				System.out.println(amount + "WEEEEEEEEEEEEEE");

				String customerId = paymentIntent.getCustomer();
				System.out.println("^###################3" + customerId);
				Customer customer = Customer.retrieve(customerId);
				String email = customer.getEmail();
				System.out.println("Customer email: " + email);
				long subscriptionAmount = amount/100;
				User user = userRepository.findByEmail(email);
				System.out.println("!!!!!!!!!!!!!!!!!!!"+user);
				
				user.getVerification().setStartedAt(LocalDateTime.now());
				user.getVerification().setPlanType(amount == YEARLY_PRICE ? "Yearly":"Monthly");

				if (subscriptionAmount == YEARLY_PRICE) {
					
					LocalDateTime endsAt = user.getVerification().getStartedAt().plusYears(1);
					System.out.println("$$$$$$$$$$$$$$"+endsAt);
					user.getVerification().setEndsAt(endsAt);
				} else if (subscriptionAmount == MONTHLY_PRICE) {
					LocalDateTime endsAt = user.getVerification().getStartedAt().plusMonths(1);
					System.out.println("$$$$$$$$$$$$$$"+endsAt);
					user.getVerification().setEndsAt(endsAt);
				}
				
				
				
				
				user.getVerification().setStatus(true);
				System.out.println("%%%%%%%%%%%%%%%%" + user);
				userRepository.save(user);
//				
			} else if ("checkout.session.failed".equals(event.getType())) {

				System.out.println("Payment failed.");
			}

			return ResponseEntity.ok("Received");

		} catch (SignatureVerificationException e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
		}

	}
}
