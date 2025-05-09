package ict.bean;

public class ShopConsumptionBean {
    private String consumptionId;
    private String fruitId;
    private String regionId;
    private int quantity;
    private int season; // {0: Spring, 1: Summer, 2: Fall, 3: Winter}

    public ShopConsumptionBean() {
    }

    public ShopConsumptionBean(String consumptionId, String fruitId, String regionId, int quantity, int season) {
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
    
    public int getSeason() { return season; }
    public void setSeason(int season) { this.season = season; }

    public String getSeasonText() {
        switch (season) {
            case 0:
                return "Spring";
            case 1:
                return "Summer";
            case 2:
                return "Fall";
            case 3:
                return "Winter";
            default:
                return "Unknown";
        }
    }

    // Print method
    public String toString() {
        return String.format("Consumption ID: %s, Fruit ID: %s, Region ID: %s, Quantity: %d, Season: %d",
            consumptionId, fruitId, regionId, quantity, season);
    }
}
