package ict.db;

import java.util.ArrayList;

import ict.bean.FruitBean;

public class TestFruit {
    ProjectDB db = ProjectDB.getInstance();

    public static void main(String[] args) {
        TestFruit testFruit = new TestFruit();
        testFruit.testFruitOperations();
    }

     // Test fruit operations
    public void testFruitOperations() {
        System.out.println("\nTesting fruit operations:");
        
        // Test adding fruit
        System.out.println("\nTesting adding fruit:");
        try {
            db.addFruit("c001", "Apple");
            System.out.println("Fruit added successfully");
        } catch (RuntimeException e) {
            System.out.println("Error adding fruit: " + e.getMessage());
        }
        
        // Test listing all fruits
        System.out.println("\nListing all fruits:");
        ArrayList<FruitBean> fruits = db.listAllFruits();
        for (FruitBean fruit : fruits) {
            System.out.println(fruit.toString());
        }
        
        // Test updating fruit
        System.out.println("\nTesting updating fruit:");
        try {
            FruitBean fruit = new FruitBean();
            fruit.setFruitId("f001");
            fruit.setSourceCityId("c001");
            fruit.setFruitName("Hong Kong Apple");
            
            db.updateFruit(fruit);
            System.out.println("Fruit updated successfully");
            
            // Verify update
            System.out.println("\nVerifying update:");
            fruits = db.listAllFruits();
            for (FruitBean f : fruits) {
                if (f.getFruitId().equals("f001")) {
                    System.out.println("Updated fruit: " + f.toString());
                    break;
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Error updating fruit: " + e.getMessage());
        }
        
        // Test deleting fruit
        System.out.println("\nTesting deleting fruit:");
        try {
            db.deleteFruit("f004");
            System.out.println("Fruit deleted successfully");
            
            // Verify deletion
            System.out.println("\nVerifying deletion:");
            fruits = db.listAllFruits();
            boolean found = false;
            for (FruitBean fruit : fruits) {
                if (fruit.getFruitId().equals("f004")) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Fruit f001 not found - deletion successful");
            }
        } catch (RuntimeException e) {
            System.out.println("Error deleting fruit: " + e.getMessage());
        }

         // Close the database connection
         ProjectDB.getInstance().close();
    }
}   
