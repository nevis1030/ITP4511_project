package ict.db;

import java.util.ArrayList;
import ict.bean.BorrowBean;

public class TestBorrow {
    public static void main(String[] args) {
        ProjectDB db = ProjectDB.getInstance();
        
        // Test borrow operations
        System.out.println("\n=== Borrow Operations Test ===");
        
        // Test creating borrow request
        System.out.println("\nTesting creating borrow request:");
        try {
            // Create borrow request between shops
            db.createBorrowRequest("s001", "s009", "f001", 20);
            System.out.println("Borrow request created successfully");
            
            // Verify creation
            ArrayList<BorrowBean> borrows = db.listAllBorrow();
            if (borrows != null && !borrows.isEmpty()) {
                System.out.println("Created borrow request:");
                for (BorrowBean borrow : borrows) {
                    System.out.println(borrow.toString());
                }
            } else {
                System.out.println("Failed to verify borrow request creation");
            }
        } catch (RuntimeException e) {
            System.out.println("Error creating borrow request: " + e.getMessage());
        }
        
        // Test approving borrow request
        System.out.println("\nTesting approving borrow request:");
        try {
            ArrayList<BorrowBean> borrows = db.listAllBorrow();
            if (borrows != null && !borrows.isEmpty()) {
                BorrowBean borrow = borrows.get(borrows.size() - 1);
            db.approveBorrow(borrow.getBorrowId()); // 1 = approved
                System.out.println("Borrow request approved successfully");
                
                ArrayList<BorrowBean> updatedBorrows = db.listAllBorrow();
                BorrowBean updated = null;
                for (BorrowBean b : updatedBorrows) {
                    if (b.getBorrowId().equals(borrow.getBorrowId())) {
                        updated = b;
                        break;
                    }
                }
                if (updated != null && updated.getStatus() == 1) {
                    System.out.println("Borrow request status verified");
                } else {
                    System.out.println("Failed to verify borrow request status");
                }
            } else {
                System.out.println("No borrow requests found to approve");
            }
        } catch (RuntimeException e) {
            System.out.println("Error approving borrow request: " + e.getMessage());
        }
        
        // Test getting all borrows
        System.out.println("\nTesting getting all borrows:");
        try {
            ArrayList<BorrowBean> borrows = db.listAllBorrow();
            System.out.println("All borrows:");
            for (BorrowBean borrow : borrows) {
                System.out.println(borrow.toString());
            }
        } catch (RuntimeException e) {
            System.out.println("Error getting all borrows: " + e.getMessage());
        }
        
        // Close the database connection
        ProjectDB.getInstance().close();
    }
}
