package com.dao.wethemany.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dao.wethemany.models.Purchasing;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class Payment_Services {
	
//	public boolean paymentStatus() {
		
		public String createCharge(Purchasing purchasing)
	    {
	        String id = null;
	        try
	        {
	            Stripe.apiKey = "sk_test_51JKHBOAjTCM92WB7EGezDMkWyFzs4mGxr5sIUemdVyBkpWZT6GIiHaTKKazE7j1kaWPqlGkiolYBeYcwbkR66xYM00g7mmMjoe";
	            Map chargeParams = new HashMap<>();
//	            String.valueOf(purchasing.getPaymentInfo().getPayment_Amount())
	            int value = (int)purchasing.getPaymentInfo().getPayment_Amount()+1;
	            chargeParams.put("amount", value);
	            chargeParams.put("currency", "aud");
	            chargeParams.put("source", "tok_visa");
	            chargeParams.put(
	              "description",
	              "My First Test Charge (created for API docs)"
	            );
	            
//	            chargeParams.put("amount", (int)(amount*100));
//	            chargeParams.put("currency", "USD");
//	            chargeParams.put("description", "Charge for " + email);
//	            chargeParams.put("source", "req_hLRK3h3WtzuIcf");
	            Charge charge = Charge.create(chargeParams);
	            id = charge.getId();
	        }
	        catch(StripeException e)
	        {
	            throw new RuntimeException("Unable to process the charge", e);
	        }
	        return id;
	    }



	
}
