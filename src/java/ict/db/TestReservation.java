package ict.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import ict.bean.ReservationBean;

public class TestReservation {
    public static void main(String[] args) {
        ProjectDB db = ProjectDB.getInstance();
        
        // Test reservation operations
        System.out.println("\n=== Reservation Operations Test ===");
        
        // Test creating reservation
        System.out.println("\nTesting creating reservation:");
        try {
            String shopId = "s001";
            String fruitId = "f001";
            
            if (shopId != null && fruitId != null) {

                java.sql.Date orderDate = new java.sql.Date(System.currentTimeMillis());
                db.createReservation(fruitId, shopId, 50, orderDate);
                System.out.println("Reservation created successfully");
                
                // Verify creation
                ArrayList<ReservationBean> reservations = db.getReservationByShop(shopId);
                if (reservations != null && !reservations.isEmpty()) {
                    System.out.println("Created reservation:");
                    for (ReservationBean reservation : reservations) {
                        System.out.println(reservation.toString());
                    }
                } else {
                    System.out.println("Failed to verify reservation creation");
                }
            } else {
                System.out.println("Could not find existing shop or fruit in database");
            }
        } catch (RuntimeException e) {
            System.out.println("Error creating reservation: " + e.getMessage());
        }
        
        // Test approving reservation
        System.out.println("\nTesting approving reservation:");
        try {
            String shopId = "s001";
            if (shopId != null) {
                // Get the latest reservation
                ArrayList<ReservationBean> reservations = db.getReservationByShop(shopId);
                if (reservations != null && !reservations.isEmpty()) {
                    ReservationBean reservation = reservations.get(reservations.size() - 1);
                    db.approveReservation(reservation.getReservationId(), 1); // 1 = approved
                    System.out.println("Reservation approved successfully");
                    
                    // Verify approval
                    ReservationBean updated = db.listAllReservation(reservation.getReservationId());
                    if (updated != null && updated.getStatus() == 1) {
                        System.out.println("Reservation status verified");
                    } else {
                        System.out.println("Failed to verify reservation status");
                    }
                } else {
                    System.out.println("No reservations found to approve");
                }
            } else {
                System.out.println("Could not find existing shop in database");
            }
        } catch (RuntimeException e) {
            System.out.println("Error approving reservation: " + e.getMessage());
        }
        
        // Test getting reservations by shop
        System.out.println("\nTesting getting reservations by shop:");
        try {
            String shopId = "s001";
            if (shopId != null) {
                ArrayList<ReservationBean> reservations = db.getReservationByShop(shopId);
                System.out.println("Reservations in shop:");
                for (ReservationBean reservation : reservations) {
                    System.out.println(reservation.toString());
                }
            } else {
                System.out.println("Could not find existing shop in database");
            }
        } catch (RuntimeException e) {
            System.out.println("Error getting reservations by shop: " + e.getMessage());
        }
        
        // Test getting reservations by city
        System.out.println("\nTesting getting reservations by city:");
        try {
            String shopId = "s001";
            if (shopId != null) {
                String cityId = "c001";
                if (cityId != null) {
                    ArrayList<ReservationBean> reservations = db.getReservationByCity(cityId);
                    System.out.println("Reservations in city:");
                    for (ReservationBean reservation : reservations) {
                        System.out.println(reservation.toString());
                    }
                } else {
                    System.out.println("Could not find city for shop");
                }
            } else {
                System.out.println("Could not find existing shop in database");
            }
        } catch (RuntimeException e) {
            System.out.println("Error getting reservations by city: " + e.getMessage());
        }
        
        // Test getting reservations by region
        System.out.println("\nTesting getting reservations by region:");
        try {
            String warehouseId = "w001";
            if (warehouseId != null) {
                String regionId ="r001";
                if (regionId != null) {
                    ArrayList<ReservationBean> reservations = db.getReservationByRegion(regionId);
                    System.out.println("Reservations in region:");
                    for (ReservationBean reservation : reservations) {
                        System.out.println(reservation.toString());
                    }
                } else {
                    System.out.println("Could not find region for warehouse");
                }
            } else {
                System.out.println("Could not find existing warehouse in database");
            }
        } catch (RuntimeException e) {
            System.out.println("Error getting reservations by region: " + e.getMessage());
        }
        
        // Test getting reservations by user
        System.out.println("\nTesting getting reservations by user:");
        try {
            String userId = "u001";
            if (userId != null) {
                ArrayList<ReservationBean> reservations = db.getReservationByUser(userId);
                System.out.println("Reservations for user:");
                for (ReservationBean reservation : reservations) {
                    System.out.println(reservation.toString());
                }
            } else {
                System.out.println("Could not find existing user in database");
            }
        } catch (RuntimeException e) {
            System.out.println("Error getting reservations by user: " + e.getMessage());
        }
        
        // Close the database connection
        ProjectDB.getInstance().close();
    }
    

}
