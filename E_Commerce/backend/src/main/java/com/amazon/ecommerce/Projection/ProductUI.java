package com.amazon.ecommerce.Projection;

import java.util.List;

public interface ProductUI {
//name, price, quantity, discount, description, catName
	int getId();
	
	String getName();

	int getPrice();

	int getQuantity();

	int getDiscount();

	String getDescription();

	String getCatName();

	String getImageUrls();
}
