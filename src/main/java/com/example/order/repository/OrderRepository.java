package com.example.order.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.order.entity.Order;
import com.example.order.entity.OrderProduct;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	@Query("{'products': {$all: ?0}, 'address': ?1}")
	List<Order> findByProductsContainingAllProductsAndAddress(List<OrderProduct> products, String address);
	
}
