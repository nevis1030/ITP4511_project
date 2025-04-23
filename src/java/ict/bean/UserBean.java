package ict.bean;

public class UserBean {
    private String userId;
    private String username;
    private String password;
    private String role;
    private String shopId;
    private String warehouseId;
    private String displayName;

    public UserBean() {
    }

    public UserBean(String userId, String username, String password, String role, String shopId, String warehouseId, String displayName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.shopId = shopId;
        this.warehouseId = warehouseId;
        this.displayName = displayName;
    }

    public UserBean(String userId, String username, String password, String role, String displayName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.displayName = displayName;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getShopId() { return shopId; }
    public void setShopId(String shopId) { this.shopId = shopId; }
    
    public String getWarehouseId() { return warehouseId; }
    public void setWarehouseId(String warehouseId) { this.warehouseId = warehouseId; }
    
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    // Print method
    public String toString() {
        return String.format("User ID: %s, Username: %s, Role: %s, Shop ID: %s, Warehouse ID: %s",
            userId, username, role, shopId, warehouseId);
    }
}
