package com.example.pulperia.Models;

import java.util.Date;

public class productos {
    private Integer id;
    private String name;
    private Double price;
    private Integer stock;
    private String barcode;
    private Integer min_stock;
    private String created_at;

    public productos() {}

    // Getters and Setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public Integer getMin_stock() { return min_stock; }
    public void setMin_stock(Integer min_stock) { this.min_stock = min_stock; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
}
