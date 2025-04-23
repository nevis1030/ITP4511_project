package ict.bean;

public class ReservationBean {

    private String reservationId;
    private String shopId;
    private String fruitId;
    private int quantity;
    private String orderDate;
    private String endDate;
    private int status; // {0: pending, 1: approved, 2: denied}

    public ReservationBean() {
    }

    public ReservationBean(String reservationId, String shopId, String fruitId, int quantity, String orderDate, String endDate, int status) {
        this.reservationId = reservationId;
        this.shopId = shopId;
        this.fruitId = fruitId;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.endDate = endDate;
        this.status = status;
    }

    public ReservationBean(String reservationId, String shopId, String fruitId, int quantity, String orderDate, int status) {
        this.reservationId = reservationId;
        this.shopId = shopId;
        this.fruitId = fruitId;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters and Setters
    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }
    
    public String getShopId() { return shopId; }
    public void setShopId(String shopId) { this.shopId = shopId; }
    
    public String getFruitId() { return fruitId; }
    public void setFruitId(String fruitId) { this.fruitId = fruitId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public String getOrderDate() { return orderDate; }
    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }
    
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    // Print method
    public String toString() {
        return String.format("Reservation ID: %s, Shop ID: %s, Fruit ID: %s, Quantity: %d, Order Date: %s, End Date: %s, Status: %d",
            reservationId, shopId, fruitId, quantity, orderDate, endDate, status);
    }
}
