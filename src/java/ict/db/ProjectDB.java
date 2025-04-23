/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import ict.bean.UserBean;
import ict.bean.FruitBean;
import ict.bean.StockLevelBean;

/**
 *
 * @author Nevis
 */
public class ProjectDB {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";
    
    private static final String USER_ID_PREFIX = "u";
    private static final String FRUIT_ID_PREFIX = "f";
    
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

            String createFruitTable = "CREATE TABLE IF NOT EXISTS fruit ("
                    + "fruit_id varchar(20) NOT NULL,"
                    + "source_city_id varchar(20) NOT NULL,"
                    + "fruit_name varchar(50) NOT NULL,"
                    + "PRIMARY KEY (fruit_id)"
                    + ")";

            String createStockLevelTable = "CREATE TABLE IF NOT EXISTS stocklevel ("
                    + "stock_id varchar(40) NOT NULL,"
                    + "shop_id varchar(20),"
                    + "fruit_id varchar(20) NOT NULL,"
                    + "warehouse_id varchar(20),"
                    + "quantity int NOT NULL,"
                    + "PRIMARY KEY (stock_id)"
                    + ")";

            executeUpdate(createUserTable);
            executeUpdate(createShopTable);
            executeUpdate(createWarehouseTable);
            executeUpdate(createCityTable);
            executeUpdate(createFruitTable);
            executeUpdate(createStockLevelTable);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to create tables", ex);
        }
    }

    public void dropTable() {
        try {
            executeUpdate("DROP TABLE IF EXISTS stocklevel");
            executeUpdate("DROP TABLE IF EXISTS user");
            executeUpdate("DROP TABLE IF EXISTS shop");
            executeUpdate("DROP TABLE IF EXISTS warehouse");
            executeUpdate("DROP TABLE IF EXISTS city");
            executeUpdate("DROP TABLE IF EXISTS fruit");
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


    //fruit
    public synchronized String getNextFruitId() {
        try {
            ResultSet rs = executeQuery("SELECT fruit_id FROM fruit ORDER BY CAST(SUBSTRING(fruit_id, 2) AS UNSIGNED) DESC LIMIT 1");
            if (rs.next()) {
                String latestId = rs.getString("fruit_id");
                if (latestId.startsWith(FRUIT_ID_PREFIX)) {
                    int currentNumber = Integer.parseInt(latestId.substring(1));
                    return FRUIT_ID_PREFIX + String.format("%03d", currentNumber + 1);
                }
            }
            return FRUIT_ID_PREFIX + "001";
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to generate fruit ID", ex);
        }
    }

    public void addFruit(String fruitId, String cityId, String fruitName) {
        try {
            String query = "INSERT INTO fruit (fruit_id, source_city_id, fruit_name) VALUES (?, ?, ?)";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, fruitId);
            stmt.setString(2, cityId);
            stmt.setString(3, fruitName);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to add fruit", ex);
        }
    }

    public void addFruit(String cityId, String fruitName) {
        String fruitId = getNextFruitId();
        addFruit(fruitId, cityId, fruitName);
    }

    public void updateFruit(FruitBean fruit) {
        try {
            String query = "UPDATE fruit SET source_city_id = ?, fruit_name = ? WHERE fruit_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, fruit.getSourceCityId());
            stmt.setString(2, fruit.getFruitName());
            stmt.setString(3, fruit.getFruitId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update fruit", ex);
        }
    }

    public void deleteFruit(String fruitId) {
        try {
            String query = "DELETE FROM fruit WHERE fruit_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, fruitId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to delete fruit", ex);
        }
    }

    public ArrayList<FruitBean> listAllFruits() {
        ArrayList<FruitBean> fruits = new ArrayList<>();
        try {
            ResultSet rs = executeQuery("SELECT * FROM fruit");
            while (rs.next()) {
                FruitBean fruit = new FruitBean();
                fruit.setFruitId(rs.getString("fruit_id"));
                fruit.setSourceCityId(rs.getString("source_city_id"));
                fruit.setFruitName(rs.getString("fruit_name"));
                fruits.add(fruit);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to list fruits", ex);
        }
        return fruits;
    }

    private String generateShopStockId(String shopId, String fruitId) {
        return shopId + fruitId;
    }

    private String generateWarehouseStockId(String warehouseId, String fruitId) {
        return warehouseId + fruitId;
    }

    public void addStockToShop(String shopId, String fruitId, int quantity) {
        String stockId = generateShopStockId(shopId, fruitId);
        try {
            String query = "INSERT INTO stocklevel (stock_id, shop_id, fruit_id, warehouse_id, quantity) VALUES (?, ?, ?, NULL, ?)";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, stockId);
            stmt.setString(2, shopId);
            stmt.setString(3, fruitId);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to add shop stock", ex);
        }
    }

    public void addStockToWarehouse(String warehouseId, String fruitId, int quantity) {
        String stockId = generateWarehouseStockId(warehouseId, fruitId);
        try {
            String query = "INSERT INTO stocklevel (stock_id, shop_id, fruit_id, warehouse_id, quantity) VALUES (?, NULL, ?, ?, ?)";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, stockId);
            stmt.setString(2, fruitId);
            stmt.setString(3, warehouseId);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to add warehouse stock", ex);
        }
    }

    public void updateStock(StockLevelBean stock) {
        if (stock.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        try {
            String query = "UPDATE stocklevel SET quantity = ? WHERE stock_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setInt(1, stock.getQuantity());
            stmt.setString(2, stock.getStockId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update stock", ex);
        }
    }

    public StockLevelBean getStockByShop(String shopId) {
        try {
            String query = "SELECT sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id, SUM(sl.quantity) as total_quantity "
                       + "FROM stocklevel sl "
                       + "WHERE sl.shop_id = ? "
                       + "GROUP BY sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, shopId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                StockLevelBean stock = new StockLevelBean();
                stock.setStockId(rs.getString("stock_id"));
                stock.setShopId(rs.getString("shop_id"));
                stock.setFruitId(rs.getString("fruit_id"));
                stock.setWarehouseId(rs.getString("warehouse_id"));
                stock.setQuantity(rs.getInt("total_quantity"));
                return stock;
            }
            return null;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to get stock by shop", ex);
        }
    }

    public ArrayList<StockLevelBean> getStockByCity(String cityId) {
        ArrayList<StockLevelBean> stocks = new ArrayList<>();
        try {
            String query = "SELECT sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id, SUM(sl.quantity) as total_quantity "
                       + "FROM stocklevel sl "
                       + "JOIN shop s ON sl.shop_id = s.shop_id "
                       + "WHERE s.city_id = ? "
                       + "GROUP BY sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, cityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                StockLevelBean stock = new StockLevelBean();
                stock.setStockId(rs.getString("stock_id"));
                stock.setShopId(rs.getString("shop_id"));
                stock.setFruitId(rs.getString("fruit_id"));
                stock.setWarehouseId(rs.getString("warehouse_id"));
                stock.setQuantity(rs.getInt("total_quantity"));
                stocks.add(stock);
            }
            return stocks;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to get stock by city", ex);
        }
    }

    public ArrayList<StockLevelBean> getStockByRegion(String regionId) {
        ArrayList<StockLevelBean> stocks = new ArrayList<>();
        try {
            String query = "SELECT sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id, SUM(sl.quantity) as total_quantity "
                       + "FROM stocklevel sl "
                       + "JOIN shop s ON sl.shop_id = s.shop_id "
                       + "JOIN city c ON s.city_id = c.city_id "
                       + "WHERE c.region_id = ? "
                       + "GROUP BY sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, regionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                StockLevelBean stock = new StockLevelBean();
                stock.setStockId(rs.getString("stock_id"));
                stock.setShopId(rs.getString("shop_id"));
                stock.setFruitId(rs.getString("fruit_id"));
                stock.setWarehouseId(rs.getString("warehouse_id"));
                stock.setQuantity(rs.getInt("total_quantity"));
                stocks.add(stock);
            }
            return stocks;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to get stock by region", ex);
        }
    }

    public boolean checkStockAvailability(String fruitId, String shopId) {
        try {
            String query = "SELECT quantity FROM stocklevel WHERE fruit_id = ? AND shop_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, fruitId);
            stmt.setString(2, shopId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt("quantity") > 0;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to check stock availability", ex);
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
