package com.wipro.ms.demo.SpringbootmongoWeb.repository;

import com.wipro.ms.demo.SpringbootmongoWeb.model.Price;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface PriceRepository extends MongoRepository<Price, String> {
    Price findByProductId(@PathVariable String productId);
    //List<Price> findByLastname(@Param("name") String productId);
    List<Price> findByCost(@Param("price") float price);
 //   Price findBy_id(String id);
    List<Price> findByCostBetween(float from, float to);



}
