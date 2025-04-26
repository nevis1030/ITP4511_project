/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.bean;

/**
 *
 * @author local_user
 */
public class WarehouseReserveBean {
    String reservationId, shopId, shopName, fruitId, fruitName, orderDate, endDate, status;
    int quantity;

    public String getEndDate() {
        return endDate;
    }

    public String getFruitId() {
        return fruitId;
    }

    public String getFruitName() {
        return fruitName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public String getStatus() {
        return status;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setFruitId(String fruitId) {
        this.fruitId = fruitId;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
