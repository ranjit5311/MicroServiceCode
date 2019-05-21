package com.wipro.ms.demo.SpringbootmongoWeb.controller;

import com.wipro.ms.demo.SpringbootmongoWeb.Controller.PriceController;
import com.wipro.ms.demo.SpringbootmongoWeb.model.Price;
import com.wipro.ms.demo.SpringbootmongoWeb.repository.PriceRepository;
import jdk.nashorn.internal.ir.Optimistic;
import org.json.simple.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
@RunWith(SpringRunner.class)
@WebMvcTest(PriceController.class)
public class PriceControlerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PriceRepository mockpriceRepository;


    @Test
    public void createPriceController() throws Exception{
        Price price = new Price("1a", "1234productId", 24.5f);
        when(mockpriceRepository.save(any(Price.class))).thenReturn(price);
        String priceInJson = "{\"productId\":\"1234product\",\"cost\":\"24\"}";

        mockMvc.perform(post("/price/create")
                .content(priceInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cost", is(24.5)));

        verify(mockpriceRepository, times(1)).save(any(Price.class));
    }

    @Test
    public void updatePriceController() throws Exception{
        Price price = new Price("1a", "1234productId", 24.5f);
        Price updatePrice = new Price("1a", "1234productId", 88);
        String updatePriceInJson = "{\"productId\":\"1234product\",\"cost\":\"88\"}";
        when(mockpriceRepository.save(any(Price.class))).thenReturn(price);
        //String priceInJson = "{\"productId\":\"1234product\",\"cost\":\"24\"}";
        when(mockpriceRepository.findById("1a")).thenReturn(Optional.of(price));

        MvcResult result = mockMvc.perform(put("/price/update?id=1a")
                .content(updatePriceInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals("check the message","sucessfully updated price for id = 1a",
                result.getResponse().getContentAsString());
        //assertEquals("sucessfully updated price for id = 1a",
              //  result.getResponse().toString(),null);
        verify(mockpriceRepository, times(1)).save(any(Price.class));
    }
    @Test
    public void getBtwPriceController() throws Exception{
        List<Price> priceList  = Arrays.asList(
                new Price("1a", "1234productId", 24.5f),
                new Price("1b", "12345productId", 25.5f),
                new Price("1c", "12346productId", 26.5f)
        );
        when(mockpriceRepository.findByCostBetween(any(Float.class), any(Float.class))).thenReturn(priceList);

        MvcResult result = mockMvc.perform(get("/price/getListBetweenPrice?"+"fromPrice=24"+"&"+"toPrice=26")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        JSONParser parser = new JSONParser();
        JSONArray json = (JSONArray) parser.parse(response);
        assertEquals(3, json.size(), 0);

        verify(mockpriceRepository, times(1)).findByCostBetween(any(Float.class), any(Float.class));
    }

    @Test
    public void deletePriceController() throws Exception{

        doNothing().when(mockpriceRepository).delete(any(Price.class));
        Price price = new Price("1a", "1234productId", 24.5f);
        Optional<Price> opPrice = Optional.of(price);
        when(mockpriceRepository.findById("1a")).thenReturn(Optional.of(price));
        mockMvc.perform(delete("/price/delete/1a"))
                .andExpect(status().isOk());

        verify(mockpriceRepository, times(1)).delete(any(Price.class));
    }
}
