package com.amazon.ecommerce.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.amazon.ecommerce.Model.OrderProduct;
import com.amazon.ecommerce.Projection.NewOrder;

public interface OrderProductRepo extends JpaRepository<OrderProduct, Integer> {
//	@Query(value = "select mo.id as orderId,mo.status,mo.amount as totalamount, op.productid,p.name as productName, op.quantity, op.amount,op.date, u.name from order_product op \r\n"
//			+ "join my_orders mo on op.orderid=mo.id \r\n" + "join user u on mo.userid=u.id\r\n"
//			+ "join product p on op.productid=p.id order by op.date DESC;", nativeQuery = true)
//	List<NewOrder> getNewOrder();

	@Query(value = "select mo.id as orderId,mo.status,mo.amount as totalamount, op.productid,p.name as productName, op.quantity, op.amount,op.date, u.name from order_product op \r\n"
			+ "join my_orders mo on op.orderid=mo.id \r\n"
			+ "join user u on mo.userid=u.id \r\n"
			+ "join product p on op.productid=p.id where mo.status=?1 order by op.date DESC", nativeQuery = true)
	List<NewOrder> letestOrder(int statusCode);

}
