package ict.bean;

public class StockLevelBean {
    private String stockId;
    private String shopId;
    private String fruitId;
    private String fruitName;
    private String warehouseId;
    private int quantity;

    public StockLevelBean() {
    }

    public StockLevelBean(String shopId, String fruitName, int quantity) {
        this.shopId = shopId;
        this.fruitName = fruitName;
        this.quantity = quantity;
    }

    public StockLevelBean(String stockId, String shopId, String fruitId, String warehouseId, int quantity) {
        this.stockId = stockId;
        this.shopId = shopId;
        this.fruitId = fruitId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
    }

    public StockLevelBean(String stockId, String shopId, String fruitId, String fruitName, String warehouseId, int quantity) {
        this.stockId = stockId;
        this.shopId = shopId;
        this.fruitId = fruitId;
        this.fruitName = fruitName;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getStockId() { return stockId; }
    public void setStockId(String stockId) { this.stockId = stockId; }
    
    public String getShopId() { return shopId; }
    public void setShopId(String shopId) { this.shopId = shopId; }
    
    public String getFruitId() { return fruitId; }
    public void setFruitId(String fruitId) { this.fruitId = fruitId; }
    
    public String getFruitName() { return fruitName; }
    public void setFruitName(String fruitName) { this.fruitName = fruitName; }
    
    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Print method
    public String toString() {
        return String.format("Shop: %s, Fruit: %s (%s), Quantity: %d",
            shopId, fruitName, fruitId, quantity);
    }
}
