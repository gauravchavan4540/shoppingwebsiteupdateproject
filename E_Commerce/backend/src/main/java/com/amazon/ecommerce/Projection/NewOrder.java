package com.amazon.ecommerce.Projection;

import java.util.Date;

public interface NewOrder {
	// totalamount, productid, name, quantity, amount, date, name

	Integer getOrderId();     // Changed from int to Integer

	Integer getStatus(); 
	
	Double getTotalamount();  // Changed from double to Double

	Integer getProductid();    // Changed from int to Integer

	String getProductName();   // Corrected capitalization to match Java conventions

	Integer getQuantity();     // Changed from int to Integer

	Double getAmount();        // Changed from double to Double

	Date getDate();

	String getName();
}
