package com.amazon.ecommerce.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazon.ecommerce.Model.MyOrders;
import com.amazon.ecommerce.Model.Product;
import com.amazon.ecommerce.Projection.NewOrder;
import com.amazon.ecommerce.Projection.ProductUI;
import com.amazon.ecommerce.Repo.CategoryRepo;
import com.amazon.ecommerce.Repo.MyOrdersRepo;
import com.amazon.ecommerce.Repo.OrderProductRepo;
import com.amazon.ecommerce.Repo.ProductRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("seller")
public class SellerController {

	@Autowired
	ProductRepo productRepo;

	@Autowired
	CategoryRepo categoryRepo;

	@Autowired
	OrderProductRepo orderProductRepo;

	@Autowired
	MyOrdersRepo myOrdersRepo;

	@RequestMapping("getProductImages{productId}")
	public String getProductImages(@PathVariable int productId) {
		// Fetch the JSON string from the database
		String imageUrls = productRepo.findByProductId(productId);
//		System.out.println(imageUrls);
		return imageUrls;
	}

	@RequestMapping("getAllProducts{userid}")
	public List<ProductUI> findByCategoryName(@PathVariable int userid) {
		List<ProductUI> list = productRepo.findByCategoryName(userid);

//		System.out.println(list.toString());
		return list;
	}

	@RequestMapping("addNewProduct")
	public Product addNewProduct(@RequestBody Product obj) {
		obj.setDate(new Date());

		System.out.println(obj.getImageUrls().toString());
		System.out.println(obj.toString());
		return productRepo.save(obj);
	}

	@DeleteMapping("deleteProduct/{id}")
	public boolean deleteProduct(@PathVariable int id) {
		boolean status = false;
		if (id != 0) {
			status = true;
			productRepo.deleteById(id);
		}
		return status;
	}

	@PutMapping("updateProduct/{id}")
	public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
		Product prod = productRepo.findById(id).get();
		prod.setName(product.getName());
//		prod.setCategoryid(product.getCategoryid());
		prod.setDescription(product.getDescription());
		prod.setImageUrlsJson(product.getImageUrlsJson());
		prod.setDiscount(product.getDiscount());
		prod.setPrice(product.getPrice());
//		prod.setRating(product.getRating());

		return productRepo.save(prod);
	}

	@RequestMapping("getCatId/{catName}")
	public int getProductImages(@PathVariable String catName) {
		// Fetch the JSON string from the database
		int catId = categoryRepo.findByName(catName);
//		System.out.println(catId);
		return catId;
	}

//	@GetMapping("newOrder")
//	public List<NewOrder> newOrder() {
//		List<NewOrder> neworder = orderProductRepo.getNewOrder();
////	    System.out.println(neworder);
//		return neworder;
//	}

//	@RequestMapping("letestOrder/{userid}")
//	public List<NewOrder> letestOrder(@PathVariable int userid) {
//		List<NewOrder> neworder = orderProductRepo.letestOrder(userid);
//		return neworder;
//	}

	@PutMapping("updateStatus/{id}")
	public int updateStatus(@PathVariable int id, @RequestBody int status) {
		MyOrders orders = myOrdersRepo.findById(id).get();
		System.out.println(orders.toString());
		if (orders != null) {
			orders.setStatus(status);
			myOrdersRepo.save(orders);
			return 1;
		} else {
			return 0;
		}
	}

	@GetMapping("sortWithStatus{statusCode}")
	public List<NewOrder> newOrder(@PathVariable int statusCode) {
		List<NewOrder> neworder = orderProductRepo.letestOrder(statusCode);
//	    System.out.println(neworder);
		return neworder;
	}
}