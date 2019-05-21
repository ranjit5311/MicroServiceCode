package com.wipro.ms.demo.SpringbootmongoWeb;

import com.wipro.ms.demo.SpringbootmongoWeb.model.Price;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration  //mongo
public class MongoIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void savePriceIntegrationTest() {
        Price price = new Price("1234productId", 24.5f);
        ResponseEntity<Price> responseEntity = testRestTemplate.postForEntity("/price/create", price, Price.class);

        Price savePrice = responseEntity.getBody();
        System.out.println("saved Price : "+responseEntity);
        assertNotNull(savePrice);
        assertEquals(24.5, responseEntity.getBody().getCost(),0);

    }
    @Test
    public void updatePriceIntegrationTest() {
        Price price = new Price("1234productId", 24.5f);
        ResponseEntity<Price> responseEntity = testRestTemplate.postForEntity("/price/create", price, Price.class);
        String updateId = responseEntity.getBody().getId();
        Price updatePrice = new Price("1234productId", 28);
        testRestTemplate.put("/price/update?id="+updateId, updatePrice);
        Price getUpdatePrice = testRestTemplate.getForObject("/price/getPrice/"+updateId, Price.class);
        System.out.println("updated Price : "+getUpdatePrice);
        assertNotNull(updatePrice);
        assertEquals(28, getUpdatePrice.getCost(),0);

    }

    @Test
    public void getBtwPriceIntegrationTest() throws Exception{
        Price p1 = new Price("1234productId", 24.5f);
        testRestTemplate.postForEntity("/price/create", p1, Price.class);
        Price p2 = new Price("12345productId", 25.5f);
        testRestTemplate.postForEntity("/price/create", p2, Price.class);
        Price p3 = new Price("12346productId", 26.5f);
        testRestTemplate.postForEntity("/price/create", p3, Price.class);
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("/price/getListBetweenPrice?fromPrice=24&toPrice=27", String.class);

        String response = responseEntity.getBody();
        System.out.println("saved Price : "+response);
        assertNotNull(response);
        JSONParser parser = new JSONParser();
        JSONArray json = (JSONArray) parser.parse(response);
        assertEquals(3, json.size(), 0);

    }

    @Test
    public void deletePriceIntegrationTest() {
        Price price = new Price("1234productId", 24.5f);
        ResponseEntity<Price> responseEntity = testRestTemplate.postForEntity("/price/create", price, Price.class);
        String deleteId = responseEntity.getBody().getId();
        Price savePrice = responseEntity.getBody();
        System.out.println("saved Price : "+responseEntity);
        assertNotNull(savePrice);
        testRestTemplate.delete("/price/delete/"+deleteId);
        ResponseEntity<Void> responseEntity1 = testRestTemplate.getForEntity("/price/getList", Void.class);
        Void body = responseEntity1.getBody();
        assertNull(body);



    }
}
