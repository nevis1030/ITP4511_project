package ict.db;

import java.util.ArrayList;

import ict.bean.StockLevelBean;

public class TestStock {
    ProjectDB db = ProjectDB.getInstance();
    public static void main(String[] args) {
        TestStock testStock = new TestStock();
        testStock.testStockOperations();
    }
    //stock
    //Test stock operations
    public void testStockOperations() {
        System.out.println("\nTesting stock operations:");
        
        // Test adding stock to shop
        System.out.println("\nTesting adding stock to shop:");
        try {
            db.addStockToShop("s001", "f002", 100);
            System.out.println("Stock added to shop successfully");
        } catch (RuntimeException e) {
            System.out.println("Error adding stock to shop: " + e.getMessage());
        }
        
        //Test adding stock to warehouse
        System.out.println("\nTesting adding stock to warehouse:");
        try {
            db.addStockToWarehouse("w001", "f002", 500);
            System.out.println("Stock added to warehouse successfully");
        } catch (RuntimeException e) {
            System.out.println("Error adding stock to warehouse: " + e.getMessage());
        }
        
        // Test updating stock
        System.out.println("\nTesting updating stock:");
        try {
            StockLevelBean stock = new StockLevelBean();
            stock.setStockId("s001f002");
            stock.setQuantity(150);
            
            db.updateStock(stock);
            System.out.println("Stock updated successfully");
            
            // Verify update
            StockLevelBean updatedStock = db.getStockByShop("s001");
            System.out.println("Updated shop stock quantity: " + updatedStock.getQuantity());
        } catch (RuntimeException e) {
            System.out.println("Error updating stock: " + e.getMessage());
        }
        
        // Test getting stock by shop
        System.out.println("\nTesting getting stock by shop:");
        try {
            StockLevelBean stock = db.getStockByShop("s001");
            if (stock != null) {
                System.out.println("Stock in shop1:");
                System.out.println(stock.toString());
            } else {
                System.out.println("No stock found in shop1");
            }
        } catch (RuntimeException e) {
            System.out.println("Error getting stock by shop: " + e.getMessage());
        }
        
        // Test getting stock by city
        System.out.println("\nTesting getting stock by city:");
        try {
            ArrayList<StockLevelBean> stocks = db.getStockByCity("c001");
            System.out.println("Stock in city c001:");
            for (StockLevelBean stock : stocks) {
                System.out.println(stock.toString());
            }
        } catch (RuntimeException e) {
            System.out.println("Error getting stock by city: " + e.getMessage());
        }
        
        // Test getting stock by region
        System.out.println("\nTesting getting stock by region:");
        try {
            ArrayList<StockLevelBean> stocks = db.getStockByRegion("r001");
            System.out.println("Stock in region r001:");
            for (StockLevelBean stock : stocks) {
                System.out.println(stock.toString());
            }
        } catch (RuntimeException e) {
            System.out.println("Error getting stock by region: " + e.getMessage());
        }
        
        // Test checking stock availability
        // System.out.println("\nTesting checking stock availability:");
        // try {
        //     boolean available = db.checkStockAvailability("f001", "s001", 10);
        //     System.out.println("Stock available in shop1 for f001: " + available);
        // } catch (RuntimeException e) {
        //     System.out.println("Error checking stock availability: " + e.getMessage());
        // }

        
        // Close the database connection
        ProjectDB.getInstance().close();
    }
}
