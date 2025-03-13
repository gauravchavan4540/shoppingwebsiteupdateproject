package com.amazon.ecommerce.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amazon.ecommerce.Model.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	int countByProductidAndUserid(int productid, int userid);

	List<Cart> findByUserid(int userid);

	@Query(value = "select * from cart where productid=?1", nativeQuery = true)
	Cart findByProductId(int id);

	@Query(value = "select * from cart where userid=?1", nativeQuery = true)
	List<Cart> findByUserId(int id);
	
	int countByUserid(int userid);

	Cart findByProductidAndUserid(int productid, int userid);

}