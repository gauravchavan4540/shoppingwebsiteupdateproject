package com.amazon.ecommerce.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.amazon.ecommerce.Model.Cart;
import com.amazon.ecommerce.Model.MyOrders;
import com.amazon.ecommerce.Model.OrderProduct;
import com.amazon.ecommerce.Model.Product;
import com.amazon.ecommerce.Model.Rating;
import com.amazon.ecommerce.Projection.Cartproduct;
import com.amazon.ecommerce.Projection.OrderRequest;
import com.amazon.ecommerce.Projection.ProductBuyerUI;
import com.amazon.ecommerce.Repo.CartRepo;
import com.amazon.ecommerce.Repo.MyOrdersRepo;
import com.amazon.ecommerce.Repo.OrderProductRepo;
import com.amazon.ecommerce.Repo.ProductRepo;
import com.amazon.ecommerce.Repo.RatingRepo;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("buyer")
public class BuyerController {
	@Autowired
	ProductRepo productRepo;

	@Autowired
	RatingRepo ratingRepo;

	@Autowired
	OrderProductRepo orderProductRepo;

	@Autowired
	MyOrdersRepo myOrdersRepo;

	@Autowired
	CartRepo cartRepo;

	@RequestMapping("addToCart/{productid}/{userid}")
	public int addToCart(@PathVariable int productid, @PathVariable int userid) {
		try {
			int count = cartRepo.countByProductidAndUserid(productid, userid);
			if (count == 1)
				return 1;
			else if (count > 1)
				return 0;
			else {
				Cart cart = new Cart();
				cart.setProductid(productid);
				cart.setUserid(userid);
				cart.setQuantity(1);
				cartRepo.save(cart);
				return 2;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;// exception on server
		}
	}

	@DeleteMapping("removeFromCart/{productid}/{userid}")
	public int removeFromCart(@PathVariable int productid, @PathVariable int userid) {
		try {
			Cart cartItems = cartRepo.findByProductidAndUserid(productid, userid);
			System.out.println(cartItems.toString());
			if (cartItems != null) {
				cartRepo.delete(cartItems); // Remove the first match (assuming unique product per user)
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@RequestMapping("getCartProducts/{userid}")
	public List<Cartproduct> getCartProducts(@PathVariable int userid) {
		List<Cart> cartItems = cartRepo.findByUserid(userid);
		int count = cartRepo.countByUserid(userid);
		if (count == 0)
			return null;
		else {
			List<Integer> productIds = cartItems.stream().map(Cart::getProductid).collect(Collectors.toList());
			System.out.println(productRepo.findAllByProductId(productIds).toString());
			return productRepo.findAllByProductId(productIds);
		}
	}

	@RequestMapping("getProductsBuyer")
	public List<ProductBuyerUI> getProductsBuyer(@RequestBody int[] a) {
//		System.out.println(Arrays.toString(a));
		return productRepo.findProductByFilter(a[0], a[1], a[2], a[3]);
	}

	@RequestMapping("getAllProducts")
	public List<ProductBuyerUI> getAllProducts(@RequestBody int[] a) {
		return productRepo.findAllByFilter(a[1], a[2], a[3]);
	}

	private void updateProductRating(int productid) {
		// Fetch all ratings for the product
		List<Rating> ratings = ratingRepo.findByProductid(productid);

		if (ratings.isEmpty()) {
			// Handle case where there are no ratings
			Product product = productRepo.findById(productid).orElse(null);
			if (product != null) {
				product.setRating(0); // No ratings yet
				productRepo.save(product);
			}
			return;
		}

		// Calculate the average rating
		double avgRating = ratings.stream().mapToInt(Rating::getStars).average().orElse(0);

		// Update the product with the new average rating
		Product product = productRepo.findById(productid).orElse(null);
		if (product != null) {
			product.setRating((int) avgRating);
			productRepo.save(product);
		}
	}

	@RequestMapping("giveRatingToProduct/{productid}/{userid}/{rating}")
	public int giveRatingToProduct(@PathVariable int productid, @PathVariable int userid, @PathVariable int rating) {
		try {
			// Check if the user has already rated the product
			Rating existingRating = ratingRepo.findByProductidAndUserid(productid, userid);
			System.out.println(rating);
			if (existingRating != null) {
				// Update the existing rating
				existingRating.setStars(rating);
				existingRating.setDate(new Date());
				ratingRepo.save(existingRating);
			} else {
				// Add a new rating
				Rating newRating = new Rating();
				newRating.setProductid(productid);
				newRating.setStars(rating);
				newRating.setUserid(userid);
				newRating.setDate(new Date());
				ratingRepo.save(newRating);
			}

			// Recalculate the average rating
			updateProductRating(productid);

			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@PostMapping("placeOrder/{userid}/{totalAmount}")
	public int placeOrder(@PathVariable int userid, @PathVariable double totalAmount,
			@RequestBody OrderRequest orderRequest) {
		try {
			int[] prodIds = orderRequest.getProdIds();
			int[] prodQuantities = orderRequest.getProdQuantities();

			MyOrders myOrders = new MyOrders();
			myOrdersRepo.save(myOrders);

			int count = 0;
			for (int prodId : prodIds) {
				Cart cart = cartRepo.findByProductidAndUserid(prodId, userid);
				if (cart != null) {
					int productId = cart.getProductid();
					Product product = productRepo.findById(productId).orElse(null);
					if (product != null) {
						OrderProduct orderProduct = new OrderProduct();
						orderProduct.setAmount((product.getPrice() - (product.getPrice() * product.getDiscount() / 100))
								* cart.getQuantity());
						orderProduct.setDate(new Date());
						orderProduct.setQuantity(prodQuantities[count]);
						orderProduct.setOrderid(myOrders.getId());
						orderProduct.setProductid(productId);
						orderProductRepo.save(orderProduct);
					}
					cartRepo.delete(cart);
				}
				count++;
			}

			myOrders.setDate(new Date());
			myOrders.setUserid(userid);
			myOrders.setAmount(totalAmount);
			myOrdersRepo.save(myOrders);

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}