package ict.bean;

public class CityBean {
    private String cityId;
    private String cityName;
    private String regionId;

    public CityBean() {
    }

    public CityBean(String cityId, String cityName, String regionId) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.regionId = regionId;
    }

    // Getters and Setters
    public String getCityId() { return cityId; }
    public void setCityId(String cityId) { this.cityId = cityId; }
    
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public String getRegionId() { return regionId; }
    public void setRegionId(String regionId) { this.regionId = regionId; }

    // Print method
    public String toString() {
        return String.format("City ID: %s, City Name: %s, Region ID: %s",
            cityId, cityName, regionId);
    }
}
