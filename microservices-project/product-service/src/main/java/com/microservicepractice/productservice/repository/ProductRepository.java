package com.microservicepractice.productservice.repository;


import com.microservicepractice.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
