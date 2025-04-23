package ict.bean;

public class RegionBean {
    private String regionId;
    private String regionName;

    public RegionBean() {
    }

    public RegionBean(String regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    // Getters and Setters
    public String getRegionId() { return regionId; }
    public void setRegionId(String regionId) { this.regionId = regionId; }
    
    public String getRegionName() { return regionName; }
    public void setRegionName(String regionName) { this.regionName = regionName; }

    // Print method
    public String toString() {
        return String.format("Region ID: %s, Region Name: %s",
            regionId, regionName);
    }
}
