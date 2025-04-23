/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import ict.bean.UserBean;

/**
 *
 * @author Nevis
 */
public class ProjectDB {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";
    
    private static final String USER_ID_PREFIX = "u";
    
    private static Connection connection = null;
    private static ProjectDB instance = null;

    private ProjectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Database connection failed", ex);
        }
    }

    public static ProjectDB getInstance() {
        if (instance == null) {
            instance = new ProjectDB();
        }
        return instance;
    }

    // Helper methods
    private PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
    
    private ResultSet executeQuery(String query) throws SQLException {
        return prepareStatement(query).executeQuery();
    }
    
    private int executeUpdate(String query) throws SQLException {
        return prepareStatement(query).executeUpdate();
    }

    // Database connection methods
    public void getConnection() {
        // Connection is already established in constructor
        // This method is just a placeholder as per requirements
    }

    public void createTable() {
        try {
            String createUserTable = "CREATE TABLE IF NOT EXISTS user ("
                    + "user_id varchar(20) NOT NULL,"
                    + "username varchar(50) NOT NULL,"
                    + "password varchar(50) NOT NULL,"
                    + "role int NOT NULL,"
                    + "shop_id varchar(20),"
                    + "warehouse_id varchar(20),"
                    + "display_name varchar(100),"
                    + "PRIMARY KEY (user_id)"
                    + ")";
            
            String createShopTable = "CREATE TABLE IF NOT EXISTS shop ("
                    + "shop_id varchar(20) NOT NULL,"
                    + "shop_name varchar(50) NOT NULL,"
                    + "city_id varchar(20) NOT NULL,"
                    + "PRIMARY KEY (shop_id)"
                    + ")";

            String createWarehouseTable = "CREATE TABLE IF NOT EXISTS warehouse ("
                    + "warehouse_id varchar(20) NOT NULL,"
                    + "region_id varchar(20) NOT NULL,"
                    + "warehouse_name varchar(50) NOT NULL,"
                    + "PRIMARY KEY (warehouse_id)"
                    + ")";

            String createCityTable = "CREATE TABLE IF NOT EXISTS city ("
                    + "city_id varchar(20) NOT NULL,"
                    + "city_name varchar(50) NOT NULL,"
                    + "region_id varchar(20) NOT NULL,"
                    + "PRIMARY KEY (city_id)"
                    + ")";

            executeUpdate(createUserTable);
            executeUpdate(createShopTable);
            executeUpdate(createWarehouseTable);
            executeUpdate(createCityTable);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to create tables", ex);
        }
    }

    public void dropTable() {
        try {
            executeUpdate("DROP TABLE IF EXISTS user");
            executeUpdate("DROP TABLE IF EXISTS shop");
            executeUpdate("DROP TABLE IF EXISTS warehouse");
            executeUpdate("DROP TABLE IF EXISTS city");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to drop tables", ex);
        }
    }

    public synchronized String getNextUserId() {
        try {
            ResultSet rs = executeQuery("SELECT user_id FROM user ORDER BY CAST(SUBSTRING(user_id, 2) AS UNSIGNED) DESC LIMIT 1");
            if (rs.next()) {
                String latestId = rs.getString("user_id");
                if (latestId.startsWith(USER_ID_PREFIX)) {
                    int currentNumber = Integer.parseInt(latestId.substring(1));
                    return USER_ID_PREFIX + String.format("%03d", currentNumber + 1);
                }
            }
            return USER_ID_PREFIX + "001";
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to generate user ID", ex);
        }
    }

    private void addUserInternal(String userId, String displayName, String username, String password, String shopId, String warehouseId, int role) throws SQLException {
        String query = "INSERT INTO user (user_id, display_name, username, password, role, shop_id, warehouse_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = prepareStatement(query);
        stmt.setString(1, userId);
        stmt.setString(2, displayName);
        stmt.setString(3, username);
        stmt.setString(4, password);
        stmt.setInt(5, role);
        stmt.setString(6, shopId);
        stmt.setString(7, warehouseId);
        stmt.executeUpdate();
    }

    public void addUser(String username, String displayName, String password, String shopId, int role) {
        String userId = getNextUserId();
        try {
            addUserInternal(userId, displayName, username, password, shopId, null, role);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to add user", ex);
        }
    }

    public void addWarehouseUser(String username, String displayName, String password, String warehouseId, int role) {
        String userId = getNextUserId();
        try {
            addUserInternal(userId, displayName, username, password, null, warehouseId, role);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to add warehouse user", ex);
        }
    }

    public void addSeniorManager(String username, String displayName, String password, int role) {
        String userId = getNextUserId();
        try {
            addUserInternal(userId, displayName, username, password, null, null, role);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to add senior manager", ex);
        }
    }

    public void updateUser(UserBean user) {
        try {
            String query = "UPDATE user SET username = ?, password = ?, role = ?, shop_id = ?, warehouse_id = ?, display_name = ? WHERE user_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getRole());
            stmt.setString(4, user.getShopId());
            stmt.setString(5, user.getWarehouseId());
            stmt.setString(6, user.getDisplayName());
            stmt.setString(7, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update user", ex);
        }
    }

    public void deleteUser(String userId) {
        try {
            String query = "DELETE FROM user WHERE user_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to delete user", ex);
        }
    }

    public ArrayList<UserBean> listAllUsers() {
        ArrayList<UserBean> users = new ArrayList<>();
        try {
            ResultSet rs = executeQuery("SELECT * FROM user");
            while (rs.next()) {
                UserBean user = new UserBean();
                user.setUserId(rs.getString("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getInt("role"));
                user.setShopId(rs.getString("shop_id"));
                user.setWarehouseId(rs.getString("warehouse_id"));
                user.setDisplayName(rs.getString("display_name"));
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to list users", ex);
        }
        return users;
    }

    public boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM user WHERE username=? AND password=?";
        try {
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            throw new RuntimeException("Authentication failed", ex);
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Failed to close connection", ex);
            }
        }
    }
}
