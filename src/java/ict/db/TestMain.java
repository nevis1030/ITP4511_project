package ict.db;

import java.util.ArrayList;
import ict.bean.UserBean;


public class TestMain {
    public static void main(String[] args) {
        ProjectDB db = ProjectDB.getInstance();
        TestDB testDB = new TestDB();
        
        System.out.println("Testing user authentication: " + testDB.testAuthenticateUser("hkss1",   "hkss1"));
        
        // Test adding a shop user
        System.out.println("\nTesting adding shop user:");
        db.addUser("Test User 1", "testuser", "password123", "s001", 0);
        System.out.println("Shop user added successfully");
        
        // Test adding a warehouse user
        System.out.println("\nTesting adding warehouse user:");
        db.addWarehouseUser("Test warehouse", "testwarehouse", "password123", "w001", 1);
        System.out.println("Warehouse user added successfully");

        // Test adding a senior manager user
        System.out.println("\nTesting adding senior manager user:");
        db.addSeniorManager("Test senior manager", "testseniormanager", "password123", 2);
        System.out.println("Senior manager user added successfully");
        
        
        // Test listing all users
        System.out.println("\nListing all users:");
        ArrayList<UserBean> users = db.listAllUsers();
        for (UserBean user : users) {
            System.out.println(user.toString());
        }
        
        // Test updating user
        System.out.println("\nTesting updating user:");
        try {
            UserBean user = new UserBean();
            user.setUserId("u015");
            user.setDisplayName("Updated User 1");
            user.setUsername("updateduser");
            user.setPassword("newpassword123");
            user.setRole(0);
            user.setShopId("s001");
            user.setWarehouseId(null);
            
            db.updateUser(user);
            System.out.println("User updated successfully");
            
            // Verify update
            System.out.println("\nVerifying update:");
            users = db.listAllUsers();
            for (UserBean u : users) {
                if (u.getUserId().equals("u015")) {
                    System.out.println("Updated user: " + u.toString());
                    break;
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }

        // Test deleting user
        System.out.println("\nTesting deleting user:");
        try {
            db.deleteUser("u015");
            db.deleteUser("u016");
            db.deleteUser("u017");
            System.out.println("User deleted successfully");
            
            // Verify deletion
            System.out.println("\nVerifying deletion:");
            users = db.listAllUsers();
            for (UserBean u : users) {
                if (u.getUserId().equals("u015")) {
                    System.out.println("User still exists: " + u.toString());
                    break;
                }
                if (u.getUserId().equals("u016")) {
                    System.out.println("User still exists: " + u.toString());
                    break;
                }
                if (u.getUserId().equals("u017")) {
                    System.out.println("User still exists: " + u.toString());
                    break;
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    
        // Close the database connection
        ProjectDB.getInstance().close();
    }
}
