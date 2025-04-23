package ict.bean;

public class FruitBean {
    private String fruitId;
    private String sourceCityId;
    private String fruitName;

    public FruitBean() {
    }

    public FruitBean(String fruitId, String sourceCityId, String fruitName) {
        this.fruitId = fruitId;
        this.sourceCityId = sourceCityId;
        this.fruitName = fruitName;
    }

    // Getters and Setters
    public String getFruitId() { return fruitId; }
    public void setFruitId(String fruitId) { this.fruitId = fruitId; }
    
    public String getSourceCityId() { return sourceCityId; }
    public void setSourceCityId(String sourceCityId) { this.sourceCityId = sourceCityId; }
    
    public String getFruitName() { return fruitName; }
    public void setFruitName(String fruitName) { this.fruitName = fruitName; }

    // Print method
    public String toString() {
        return String.format("Fruit ID: %s, Source City ID: %s, Fruit Name: %s",
            fruitId, sourceCityId, fruitName);
    }
}
