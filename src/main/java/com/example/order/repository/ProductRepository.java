package com.example.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.order.entity.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

}
