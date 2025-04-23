package ict.bean;

public class ShopBean {
    private String shopId;
    private String shopName;
    private String cityId;

    public ShopBean() {
    }

    public ShopBean(String shopId, String shopName, String cityId) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.cityId = cityId;
    }

    // Getters and Setters
    public String getShopId() { return shopId; }
    public void setShopId(String shopId) { this.shopId = shopId; }
    
    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    
    public String getCityId() { return cityId; }
    public void setCityId(String cityId) { this.cityId = cityId; }

    // Print method
    public String toString() {
        return String.format("Shop ID: %s, Shop Name: %s, City ID: %s",
            shopId, shopName, cityId);
    }
}
