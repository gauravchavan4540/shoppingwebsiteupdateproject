package com.amazon.ecommerce.Projection;

public interface ProductBuyerUI {
	// id, description, discount, name, price, quantity, rating, days
	int getId();

	String getDescription();

	double getDiscount();

	String getName();

	int getPrice();

	int getQuantity();

	double getRating();

	int getDays();

	String getImageUrls();

}