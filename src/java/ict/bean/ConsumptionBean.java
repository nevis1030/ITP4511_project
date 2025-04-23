package ict.bean;

public class ConsumptionBean {
    private String consumptionId;
    private String fruitId;
    private String shopId;
    private String regionId;
    private int quantity;
    private int season; // {0: Spring, 1: Summer, 2: Fall, 3: Winter}

    public ConsumptionBean() {
    }


    public ConsumptionBean(String consumptionId, String fruitId, String shopId, String regionId, int quantity, int season) {
        this.consumptionId = consumptionId;
        this.fruitId = fruitId;
        this.shopId = shopId;
        this.regionId = regionId;
        this.quantity = quantity;
        this.season = season;
    }

    // Getters and Setters
    public String getConsumptionId() { return consumptionId; }
    public void setConsumptionId(String consumptionId) { this.consumptionId = consumptionId; }
    
    public String getFruitId() { return fruitId; }
    public void setFruitId(String fruitId) { this.fruitId = fruitId; }
    
    public String getShopId() { return shopId; }
    public void setShopId(String shopId) { this.shopId = shopId; }
    
    public String getRegionId() { return regionId; }
    public void setRegionId(String regionId) { this.regionId = regionId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public int getSeason() { return season; }
    public void setSeason(int season) { this.season = season; }

    // Print method
    public String toString() {
        return String.format("Consumption ID: %s, Fruit ID: %s, Shop ID: %s, Region ID: %s, Quantity: %d, Season: %d",
            consumptionId, fruitId, shopId, regionId, quantity, season);
    }
}
