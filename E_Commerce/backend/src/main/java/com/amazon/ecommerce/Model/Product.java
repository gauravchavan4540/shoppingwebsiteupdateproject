package com.amazon.ecommerce.Model;

import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.*;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String name; // ui user
	Date date;
	int userid; // ui from app
	int price; // ui user
	int quantity; // ui user
	int rating;
	String description; // uiuser
	double discount; // % stage discount
	int categoryid;

//	@ElementCollection
//	@CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
//	@Column(name = "image_urls")
//	private List<String> imageUrls;

//	@Lob
//	@Column(columnDefinition = "MEDIUMTEXT")
//	private String imageUrls;

	@Column(columnDefinition = "json")
	private String imageUrls; // Store JSON as a string

	@Transient
	private List<String> imageUrlsJson; // Transient field for internal use

	public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}

	public List<String> getImageUrlsJson() {
		if (imageUrlsJson == null && imageUrls != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				imageUrlsJson = mapper.readValue(imageUrls, new TypeReference<List<String>>() {
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return imageUrlsJson;
	}

	public void setImageUrlsJson(List<String> imageUrlsJson) {
		this.imageUrlsJson = imageUrlsJson;
		if (imageUrlsJson != null) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				this.imageUrls = mapper.writeValueAsString(imageUrlsJson);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.imageUrls = null;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", date=" + date + ", userid=" + userid + ", price=" + price
				+ ", quantity=" + quantity + ", rating=" + rating + ", description=" + description + ", discount="
				+ discount + ", categoryid=" + categoryid + ", imageUrlsJson=" + imageUrlsJson + "]";
	}

	public Product(int id, String name, Date date, int userid, int price, int quantity, int rating, String description,
			double discount, int categoryid, List<String> imageUrlsJson) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.userid = userid;
		this.price = price;
		this.quantity = quantity;
		this.rating = rating;
		this.description = description;
		this.discount = discount;
		this.categoryid = categoryid;
		this.imageUrlsJson = imageUrlsJson;
	}

	public Product() {
		super();
	}
}
