/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author local_user
 */
public class ReserveRecordBean {
    String reservation_id, shop_id, shop_name, fruit_id, fruit_name;
    int quantity;

    public String getReservation_id() {
        return reservation_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getFruit_id() {
        return fruit_id;
    }

    public String getFruit_name() {
        return fruit_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setReservation_id(String reservation_id) {
        this.reservation_id = reservation_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public void setFruit_id(String fruit_id) {
        this.fruit_id = fruit_id;
    }

    public void setFruit_name(String fruit_name) {
        this.fruit_name = fruit_name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
