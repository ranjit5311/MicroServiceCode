package com.wipro.ms.demo.SpringbootmongoWeb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
@Document
public class Price {

        @Id
        public String id;

        public String productId;
        public float cost;

        public Price() {
        }

        public Price(String productId, float cost) {
            this.productId = productId;
            this.cost = cost;
        }

    public Price(String id, String productId, float cost) {
        this.id = id;
        this.productId = productId;
        this.cost = cost;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public float getCost() {
            return cost;
        }

        public void setCost(float cost) {
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Price{" +
                    "id='" + id + '\'' +
                    ", productId='" + productId + '\'' +
                    ", cost=" + cost +
                    '}';
        }

}
