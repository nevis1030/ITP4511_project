package ict.bean;

public class BorrowBean {
    private String borrowId;
    private String fromShop;
    private String toShop;
    private String fruitId;
    private int quantity;
    private int status; // {0: ordered, 1: approved, 2: denied, 3: complete}
    private String date;

    public BorrowBean() {
    }

    public BorrowBean(String borrowId, String fromShop, String toShop, String fruitId, int quantity, int status, String date) {
        this.borrowId = borrowId;
        this.fromShop = fromShop;
        this.toShop = toShop;
        this.fruitId = fruitId;
        this.quantity = quantity;
        this.status = status;
        this.date = date;
    }

    // Getters and Setters
    public String getBorrowId() { return borrowId; }
    public void setBorrowId(String borrowId) { this.borrowId = borrowId; }
    
    public String getFromShop() { return fromShop; }
    public void setFromShop(String fromShop) { this.fromShop = fromShop; }
    
    public String getToShop() { return toShop; }
    public void setToShop(String toShop) { this.toShop = toShop; }
    
    public String getFruitId() { return fruitId; }
    public void setFruitId(String fruitId) { this.fruitId = fruitId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    // Print method
    public String toString() {
        return String.format("Borrow ID: %s, From Shop: %s, To Shop: %s, Fruit ID: %s, Quantity: %d, Status: %d, Date: %s",
            borrowId, fromShop, toShop, fruitId, quantity, status, date);
    }
}
