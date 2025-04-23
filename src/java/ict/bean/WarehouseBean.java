package ict.bean;

public class WarehouseBean {
    private String warehouseId;
    private String cityId;
    private String warehouseName;

    // Default constructor
    public WarehouseBean() {
    }

    // Parameterized constructor
    public WarehouseBean(String warehouseId, String cityId, String warehouseName) {
        this.warehouseId = warehouseId;
        this.cityId = cityId;
        this.warehouseName = warehouseName;
    }

    // Getters and Setters
    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }
    
    public String getCityId() { return cityId; }
    public void setCityId(String cityId) { this.cityId = cityId; }
    
    public String getWarehouseName() { return warehouseName; }
    public void setWarehouseName(String warehouseName) { this.warehouseName = warehouseName; }

    // Print method
    public String toString() {
        return String.format("Warehouse ID: %s, City ID: %s, Warehouse Name: %s",
            warehouseId, cityId, warehouseName);
    }
}
