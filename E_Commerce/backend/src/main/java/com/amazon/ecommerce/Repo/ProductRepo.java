package com.amazon.ecommerce.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amazon.ecommerce.Model.Product;
import com.amazon.ecommerce.Projection.Cartproduct;
import com.amazon.ecommerce.Projection.ProductBuyerUI;
import com.amazon.ecommerce.Projection.ProductUI;

public interface ProductRepo extends JpaRepository<Product, Integer> {
	List<Product> findByUserid(int id);

	@Query(value = "SELECT id,description,image_urls as imageUrls,discount,name,price,quantity,rating FROM product WHERE id IN (?1);", nativeQuery = true)
	List<Cartproduct> findAllByProductId(List<Integer> productIds);

	@Query(value = "SELECT p.id, p.name, p.price, p.quantity, p.discount, p.description, c.cat_name as catName, p.image_urls AS imageUrls FROM product p JOIN category c ON p.categoryid = c.id WHERE p.userid = ?1", nativeQuery = true)
	List<ProductUI> findByCategoryName(int userId);

//	@Query(value = "SELECT p.id, p.name, p.price, p.quantity, p.discount, p.description, c.cat_name as catName, im.image_urls AS imageUrls FROM product p JOIN category c ON p.categoryid = c.id join product_images im on p.id=product_id WHERE p.userid =?1", nativeQuery = true)
//	List<ProductUI> findByCategoryName(int userId);

	@Query(value = "select id,description,discount,name,price,quantity,rating,image_urls as imageUrls, datediff(now(),date) as days \r\n"
			+ "from product where categoryid=?1 and price>?2 and price <?3 and rating>=?4;", nativeQuery = true)
	List<ProductBuyerUI> findProductByFilter(int categoryid, int minprice, int maxprice, int rating);

	@Query(value = "select id,description,discount,name,price,quantity,rating,image_urls as imageUrls, datediff(now(),date) as days \r\n"
			+ "from product where price>?1 and price <?2 and rating>=?3;", nativeQuery = true)
	List<ProductBuyerUI> findAllByFilter(int minprice, int maxprice, int rating);

//	@Query(value = "select image_urls from product_images where id=?1", nativeQuery = true)
//	List<String> findByProductId(int productId);

	@Query(value = "select image_urls from product where id=?1", nativeQuery = true)
	String findByProductId(int productId);
}