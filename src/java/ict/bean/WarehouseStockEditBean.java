/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author local_user
 */
public class WarehouseStockEditBean {
    String fruit_id, fruit_name;
    int quantity;

    public String getFruit_id() {
        return fruit_id;
    }

    public String getFruit_name() {
        return fruit_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setFruit_name(String fruit_name) {
        this.fruit_name = fruit_name;
    }

    public void setFruit_id(String fruit_id) {
        this.fruit_id = fruit_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
