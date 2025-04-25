package ict.db;

public class TestCheckout {
    public static void main(String[] args) {
        ProjectDB db = ProjectDB.getInstance();
        
        // Test checkout operations
        System.out.println("\n=== Checkout Operations Test ===");
        
        // Test processCheckOutToCentral
        testProcessCheckOutToCentral(db);
        
        // Test processCheckOutToLocal
        testProcessCheckOutToLocal(db);
    }
    
    private static void testProcessCheckOutToCentral(ProjectDB db) {
        System.out.println("\n=== Test Process Check Out to Central ===");
        
        // Test data
        String warehouseId = "w002";  // Hong Kong warehouse
        String fruitId = "f002";      // Hong Kong fruit
        int quantity = 50;
        String regionId = "r001";     // Hong Kong region
        
        try {
            // Check initial stock
            System.out.println("Initial stock in warehouse: " + db.getStockByWarehouse(warehouseId).getQuantity());
            System.out.println("Initial stock in central warehouse: " + db.getStockByWarehouse("cw001").getQuantity());
            
            // Process checkout to central
            db.processCheckOutToCentral(warehouseId, fruitId, quantity, regionId);
            
            // Verify results
            System.out.println("Final stock in warehouse: " + db.getStockByWarehouse(warehouseId).getQuantity());
            System.out.println("Final stock in central warehouse: " + db.getStockByWarehouse("cw001").getQuantity());
            
            System.out.println("Test Process Check Out to Central completed successfully");
            
        } catch (RuntimeException e) {
            System.out.println("Error in testProcessCheckOutToCentral: " + e.getMessage());
        }
    }
    
    private static void testProcessCheckOutToLocal(ProjectDB db) {
        System.out.println("\n=== Test Process Check Out to Local ===");
        
        // Test data
        String centralWarehouseId = "cw001"; // Hong Kong central warehouse
        String shopId = "s001";            // Hong Kong shop
        String fruitId = "f001";           // Hong Kong fruit
        int quantity = 50;
        
        try {
            // Check initial stock
//            System.out.println("Initial stock in central warehouse: " + db.getStockByWarehouse(centralWarehouseId).getQuantity());
//            System.out.println("Initial stock in shop: " + db.getStockByShop(shopId).getQuantity());
            
            // Process checkout to local
            db.processCheckOutToLocal(centralWarehouseId, shopId, fruitId, quantity);
            
            // Verify results
//            System.out.println("Final stock in central warehouse: " + db.getStockByWarehouse(centralWarehouseId).getQuantity());
//            System.out.println("Final stock in shop: " + db.getStockByShop(shopId).getQuantity());
            
            System.out.println("Test Process Check Out to Local completed successfully");
            
        } catch (RuntimeException e) {
            System.out.println("Error in testProcessCheckOutToLocal: " + e.getMessage());
        }
    }
}
