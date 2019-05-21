package com.wipro.ms.demo.SpringbootmongoWeb.Controller;

import com.wipro.ms.demo.SpringbootmongoWeb.Exception.ResourceNotFoundException;
import com.wipro.ms.demo.SpringbootmongoWeb.model.Price;
import com.wipro.ms.demo.SpringbootmongoWeb.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    PriceRepository priceRepository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Price CreatePrice(@RequestBody Price priceToSave) {
        return priceRepository.save(priceToSave);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String UpdatePrice(@RequestBody Price requestPrice,
                              @RequestParam("id") String id) {
        Optional<Price> price = priceRepository.findById(id);
       // Optional<Price> nullPrice = Optional.ofNullable(price.get());
        System.out.println("update id: "+id);
        if (price.isPresent()) {
            Price priceToUpdate = price.get();
            if(requestPrice.getProductId() != null) {
                priceToUpdate.setProductId(requestPrice.getProductId());
            }
            priceToUpdate.setCost(requestPrice.getCost());
            priceRepository.save(priceToUpdate);
            return "sucessfully updated price for id = "+id;

        } else {
            return "id = "+id+" not found in database ";
        }
    }

    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public List<Price> GetPriceList() {
        return priceRepository.findAll();
    }

    @GetMapping("/getPrice/{id}")
    public ResponseEntity<Price> getPriceById(@PathVariable(value = "id") String priceId)
            throws ResourceNotFoundException {
        Price foundPrice = priceRepository.findById(priceId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + priceId));
        return ResponseEntity.ok().body(foundPrice);
    }

    @RequestMapping(value = "/getListBetweenPrice", method = RequestMethod.GET)
    public List<Price> GetPriceListBetweenPrice(@RequestParam("fromPrice") float fromPrice,
                                                @RequestParam("toPrice") float toPrice) {
        return priceRepository.findByCostBetween(fromPrice, toPrice);
    }

    @RequestMapping(value = "/delete/{deleteId}", method = RequestMethod.DELETE)
    public void deletePrice(@PathVariable String deleteId) {
        System.out.println("id to delete : "+deleteId);
        Optional<Price> price = priceRepository.findById(deleteId);
        System.out.println("price1 : "+price);
        priceRepository.delete(price.get());
    }

}
