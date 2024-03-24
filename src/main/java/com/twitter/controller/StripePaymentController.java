package com.twitter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.twitter.model.User;
import com.twitter.response.StripeResponse;
import com.twitter.service.IStripePaymentService;
import com.twitter.service.IUserService;

@RestController
@RequestMapping("/api")
public class StripePaymentController {


    @Autowired
    private IStripePaymentService paymentService;
    
    @Autowired
    private IUserService userService;


    @PostMapping("/create-checkout-session/{planType}")
    public ResponseEntity<StripeResponse> checkoutList(@PathVariable String planType,
			@RequestHeader("Authorization") String jwt)
            throws Exception {
    	User user = userService.findUserProfileByJwt(jwt);
    	String email = user.getEmail();
        Session session = paymentService.createSession(planType,email);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);

    }

}
