package com.dao.wethemany.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dao.wethemany.models.Purchasing;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class Payment_Services {
	
	
	@Value("${stripe.apiKey}")
	private String stripeApiKey;
	
	@Value("${spring.mail.username}")
	private String companyEmail;
	
	
	@Autowired
	Others_Services others_Services;
//	public boolean paymentStatus() {
		
		public Map createCharge(Purchasing purchasing)
	    {
            Map returnMap = new HashMap<>();
	        String id = null;
	        try
	        {
	            Stripe.apiKey = stripeApiKey;
	            Map chargeParams = new HashMap<>();
	            
//	            String.valueOf(purchasing.getPaymentInfo().getPayment_Amount());
	            int value = (int)purchasing.getPaymentInfo().getPayment_Amount()*100;
	            chargeParams.put("amount", value);
	            chargeParams.put("currency", "aud");
	            chargeParams.put("source", "tok_visa");
	            chargeParams.put("receipt_email", purchasing.getUserEmail());

	            chargeParams.put("description",
	            		purchasing.getUserEmail()+" Client Has Buyed Products"
	            );

	            Charge charge = Charge.create(chargeParams);
	            if(charge !=null) {
	            	returnMap.put("id", charge.getId());
	            	returnMap.put("receiptlink", charge.getReceiptUrl());

	            }
	            id = charge.getId();
	            if( !StringUtils.isEmpty(id)) {
//	            	others_Services.sendMail(charge.getReceiptEmail(),charge.getReceiptUrl());
	            	
	            }
	        }
	        catch(StripeException e)
	        {
	            throw new RuntimeException("Unable to process the charge", e);
	        }
	        return returnMap;
	    }



	
}
