package com.example.pulperia.Models;

public class movimientos {
    private Integer id_movimiento;
    private Integer product_id;
    private Integer qty;
    private String type;
    private String date;

    public movimientos() {
    }

    public movimientos(Integer id_movimiento, Integer product_id, Integer qty, String type, String date) {
        this.id_movimiento = id_movimiento;
        this.product_id = product_id;
        this.qty = qty;
        this.type = type;
        this.date = date;
    }

    public Integer getId() {
        return id_movimiento;
    }

    public void setId(Integer id) {
        this.id_movimiento = id_movimiento;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
