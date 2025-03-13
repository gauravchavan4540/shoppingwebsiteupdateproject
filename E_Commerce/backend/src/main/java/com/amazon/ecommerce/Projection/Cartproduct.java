package com.amazon.ecommerce.Projection;

public interface Cartproduct {
// id, categoryid, date, description, discount, image_urls, name, price, quantity, rating, userid
	
	int getId();

//	int getCategoryid();
	
	String getDescription();

	double getDiscount();

	String getName();

	int getPrice();

	int getQuantity();

	double getRating();

//	int getDays();

	String getImageUrls();
}
