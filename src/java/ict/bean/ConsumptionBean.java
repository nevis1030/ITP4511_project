package ict.bean;

public class ConsumptionBean {
    private String consumptionId;
    private String fruitId;
    private String regionId;
    private int quantity;
    private String season; // {0: Spring, 1: Summer, 2: Fall, 3: Winter}

    public ConsumptionBean() {
    }


    public ConsumptionBean(String consumptionId, String fruitId, String regionId, int quantity, String season) {
        this.consumptionId = consumptionId;
        this.fruitId = fruitId;
        this.regionId = regionId;
        this.quantity = quantity;
        this.season = season;
    }

    // Getters and Setters
    public String getConsumptionId() { return consumptionId; }
    public void setConsumptionId(String consumptionId) { this.consumptionId = consumptionId; }
    
    public String getFruitId() { return fruitId; }
    public void setFruitId(String fruitId) { this.fruitId = fruitId; }
    
    public String getRegionId() { return regionId; }
    public void setRegionId(String regionId) { this.regionId = regionId; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }

    // Print method
    public String toString() {
        return String.format("Consumption ID: %s, Fruit ID: %s, Shop ID: %s, Region ID: %s, Quantity: %d, Season: %d",
            consumptionId, fruitId, regionId, quantity, season);
    }
}
