package com.wipro.ms.demo.SpringbootmongoWeb.dao;

import com.wipro.ms.demo.SpringbootmongoWeb.model.Price;
import com.wipro.ms.demo.SpringbootmongoWeb.repository.PriceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
public class MongoDbSpringUnitTest {

    @Autowired
    PriceRepository priceRepository;

    @Test
    public void testSavePriceMongoRepo() {
        priceRepository.deleteAll();
        Price price = priceRepository.save(new Price("1234productId", 24.5f));

       // System.out.println("id : "+price);
        assertEquals(0,24.5f, price.getCost());
        assertNotNull("id is autogenerate", price.getId());
    }

    @Test
    public void testUpdatePriceMongoRepo() {
        priceRepository.deleteAll();
        Price price = priceRepository.save(new Price("1234productId", 24.5f));
        // System.out.println("id : "+price);
        assertEquals(0,24.5f, price.getCost());
        assertNotNull("id is autogenerate", price.getId());
    }

    @Test
    public void testDeletePriceMongoRepo() {

        Price price = priceRepository.save(new Price("1234productId", 24.5f));
        priceRepository.delete(price);
        List<Price> priceList = priceRepository.findAll();
        System.out.println("id : "+price);
        assertEquals(0,priceList.size(), 0);
//        assertNull("price list is empty", priceList);
    }

    @Test
    public void testBtwPriceMongoRepo() {

        priceRepository.deleteAll();

        Price p1 = new Price("1a", "1234productId", 24.5f);
        Price p2 =new Price("1b", "12345productId", 25.5f);
        Price p3 =new Price("1c", "12346productId", 26.5f);

        priceRepository.save(p1);
        priceRepository.save(p2);
        priceRepository.save(p3);

        List<Price> priceList = priceRepository.findByCostBetween(24,27);
        assertEquals(3,priceList.size(), 0);
//        assertNull("price list is empty", priceList);
    }
}
