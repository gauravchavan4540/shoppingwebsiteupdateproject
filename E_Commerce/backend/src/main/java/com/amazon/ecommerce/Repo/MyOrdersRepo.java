package com.amazon.ecommerce.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.ecommerce.Model.MyOrders;

public interface MyOrdersRepo extends JpaRepository<MyOrders, Integer> {

}
