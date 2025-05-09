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
import ict.bean.ReservationBean;
import ict.bean.BorrowBean;
import ict.bean.ShopBean;
import ict.bean.CityBean;
import ict.bean.ConsumptionBean;
import ict.bean.ShopConsumptionBean;
import ict.bean.WarehouseBean;
import ict.util.CodeManager;
import ict.util.IdManager;

/**
 *
 * @author Nevis
 */
public class ProjectDB {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
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

            String createReservationTable = "CREATE TABLE IF NOT EXISTS reservation ("
                    + "reservation_id varchar(20) NOT NULL,"
                    + "shop_id varchar(20) NOT NULL,"
                    + "fruit_id varchar(20) NOT NULL,"
                    + "quantity int NOT NULL,"
                    + "order_date date NOT NULL,"
                    + "end_date date,"
                    + "status int NOT NULL,"
                    + "PRIMARY KEY (reservation_id)"
                    + ")";

            String createBorrowTable = "CREATE TABLE IF NOT EXISTS borrow ("
                    + "borrow_id varchar(20) NOT NULL,"
                    + "from_shop varchar(20) NOT NULL,"
                    + "to_shop varchar(20) NOT NULL,"
                    + "fruit_id varchar(20) NOT NULL,"
                    + "quantity int NOT NULL,"
                    + "status int NOT NULL,"
                    + "date date NOT NULL,"
                    + "PRIMARY KEY (borrow_id)"
                    + ")";

            String createConsumptionTable = "CREATE TABLE IF NOT EXISTS consumption ("
                    + "consumption_id varchar(20) NOT NULL,"
                    + "fruit_id varchar(20) NOT NULL,"
                    + "region_id varchar(20) NOT NULL,"
                    + "quantity int NOT NULL,"
                    + "season int NOT NULL,"
                    + "PRIMARY KEY (consumption_id)"
                    + ")";

            executeUpdate(createUserTable);
            executeUpdate(createShopTable);
            executeUpdate(createWarehouseTable);
            executeUpdate(createCityTable);
            executeUpdate(createFruitTable);
            executeUpdate(createStockLevelTable);
            executeUpdate(createReservationTable);
            executeUpdate(createBorrowTable);
            executeUpdate(createConsumptionTable);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to create tables", ex);
        }
    }

    public void dropTable() {
        try {
            executeUpdate("DROP TABLE IF EXISTS borrow");
            executeUpdate("DROP TABLE IF EXISTS reservation");
            executeUpdate("DROP TABLE IF EXISTS stocklevel");
            executeUpdate("DROP TABLE IF EXISTS user");
            executeUpdate("DROP TABLE IF EXISTS shop");
            executeUpdate("DROP TABLE IF EXISTS warehouse");
            executeUpdate("DROP TABLE IF EXISTS city");
            executeUpdate("DROP TABLE IF EXISTS fruit");
            executeUpdate("DROP TABLE IF EXISTS consumption");
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
            // Handle shop_id
            if (user.getShopId() == null
                    || user.getShopId().equalsIgnoreCase("null")
                    || user.getShopId().trim().isEmpty()) {
                stmt.setNull(4, Types.VARCHAR);
            } else {
                stmt.setString(4, user.getShopId());
            }

            // Handle warehouse_id - PROPER NULL HANDLING
            if (user.getWarehouseId() == null
                    || user.getWarehouseId().equalsIgnoreCase("null")
                    || user.getWarehouseId().trim().isEmpty()) {
                stmt.setNull(5, Types.VARCHAR); // Sets true database NULL
            } else {
                stmt.setString(5, user.getWarehouseId());
            }
            stmt.setString(6, user.getDisplayName());
            stmt.setString(7, user.getUserId());
//            System.out.println(stmt.toString());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("SQL State: " + ex.getSQLState());
            System.err.println("Error Code: " + ex.getErrorCode());
            System.err.println("Message: " + ex.getMessage());
            throw new RuntimeException("Failed to update user: " + ex.getMessage(), ex);
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

    public UserBean login(String username, String password) {
        UserBean user = null;
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new UserBean();
                    user.setUserId(rs.getString("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getInt("role"));
                    user.setShopId(rs.getString("shop_id"));
                    user.setWarehouseId(rs.getString("warehouse_id"));
                    user.setDisplayName(rs.getString("display_name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Login error: " + e.getMessage(), e);
        }

        return user;
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

    public void deleteStock(String stockId) {
        try {
            String query = "DELETE FROM stocklevel WHERE stock_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, stockId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to delete stock", ex);
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
        IdManager iM = new IdManager();
        String stockId = iM.combineId(warehouseId, fruitId);
        System.out.println("[DEBUG] stockId = "+stockId);
        try {
            String query = "INSERT INTO stocklevel (stock_id, shop_id, fruit_id, warehouse_id, quantity) VALUES (?, NULL, ?, ?, ?)";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, stockId);
            stmt.setString(2, fruitId);
            stmt.setString(3, warehouseId);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("[ERROR] SQL Exception occurred:");
            System.err.println("SQL State: " + ex.getSQLState());
            System.err.println("Error Code: " + ex.getErrorCode());
            System.err.println("Message: " + ex.getMessage());
            throw new RuntimeException("Failed to add warehouse stock", ex);
        }
    }

    public void updateStock(StockLevelBean stock) {
        try {
            String query = "UPDATE stocklevel SET quantity = quantity + ? WHERE stock_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setInt(1, stock.getQuantity());
            stmt.setString(2, stock.getStockId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to update stock", ex);
        }
    }

    public ArrayList<StockLevelBean> getStockByShop(String shopId) {
        ArrayList<StockLevelBean> stocks = new ArrayList<>();
        try {
            String query = "SELECT sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id, SUM(sl.quantity) as total_quantity "
                    + "FROM stocklevel sl "
                    + "WHERE sl.shop_id = ? "
                    + "GROUP BY sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, shopId);
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

    public StockLevelBean getStockByShopAndFruit(String shopId, String fruitId) {
        try {
            String query = "SELECT sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id, sl.quantity, f.fruit_name "
                    + "FROM stocklevel sl "
                    + "JOIN fruit f ON sl.fruit_id = f.fruit_id "
                    + "WHERE sl.shop_id = ? AND sl.fruit_id = ?";

            PreparedStatement pstmt = prepareStatement(query);
            pstmt.setString(1, shopId);
            pstmt.setString(2, fruitId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                StockLevelBean stock = new StockLevelBean();
                stock.setStockId(rs.getString("stock_id"));
                stock.setShopId(rs.getString("shop_id"));
                stock.setFruitId(rs.getString("fruit_id"));
                stock.setWarehouseId(rs.getString("warehouse_id"));
                stock.setQuantity(rs.getInt("quantity"));
                stock.setFruitName(rs.getString("fruit_name"));

                return stock;
            }

            rs.close();
            pstmt.close();

            return null;
        } catch (SQLException ex) {
            throw new RuntimeException("Error getting stock level", ex);
        }
    }

    // Helper method for reservation ID generation
    private String generateReservationId() {
        try {
            // Get the last reservation ID
            String query = "SELECT reservation_id FROM reservation ORDER BY reservation_id DESC LIMIT 1";
            ResultSet rs = executeQuery(query);
            String lastId = null;
            if (rs.next()) {
                lastId = rs.getString("reservation_id");
            }

            // If no existing reservation, start with v001
            if (lastId == null) {
                return "v001";
            }

            // Generate next ID using IdManager
            return IdManager.nextId(lastId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to generate reservation ID", e);
        }
    }

    // Reservation methods
    public void createReservation(String fruitId, String shopId, int quantity, java.sql.Date orderDate) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        // Validate order date
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        java.sql.Date maxOrderDate = new java.sql.Date(today.getTime() + (14 * 24 * 60 * 60 * 1000));

        if (orderDate.before(today)) {
            throw new RuntimeException("Order date must be in the future");
        }
        if (orderDate.after(maxOrderDate)) {
            throw new RuntimeException("Order date must be within next 14 days");
        }

        String reservationId = generateReservationId();
        try {
            // Calculate end date as 14 days from order date
            java.sql.Date endDate = new java.sql.Date(orderDate.getTime() + (14 * 24 * 60 * 60 * 1000));

            String query = "INSERT INTO reservation (reservation_id, shop_id, fruit_id, quantity, order_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?, 0)";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, reservationId);
            stmt.setString(2, shopId);
            stmt.setString(3, fruitId);
            stmt.setInt(4, quantity);
            stmt.setDate(5, orderDate);
            stmt.setDate(6, endDate);
            stmt.executeUpdate();

            System.out.println("Reservation created successfully with ID: " + reservationId);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create reservation", e);
        }
    }

    public void approveReservation(String reservationId) {
        try {
            // First get the reservation details
            String query = "SELECT shop_id, fruit_id, quantity FROM reservation WHERE reservation_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, reservationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String shopId = rs.getString("shop_id");
                String fruitId = rs.getString("fruit_id");
                int quantity = rs.getInt("quantity");

                // Update stock level
                String updateQuery = "UPDATE stocklevel "
                        + "SET quantity = quantity + ? "
                        + "WHERE shop_id = ? AND fruit_id = ?";
                PreparedStatement updateStmt = prepareStatement(updateQuery);
                updateStmt.setInt(1, quantity);
                updateStmt.setString(2, shopId);
                updateStmt.setString(3, fruitId);
                updateStmt.executeUpdate();

                // Update reservation status to approved (1)
                query = "UPDATE reservation SET status = 1 WHERE reservation_id = ?";
                stmt = prepareStatement(query);
                stmt.setString(1, reservationId);
                stmt.executeUpdate();

                System.out.println("Reservation approved successfully: " + reservationId);
            } else {
                throw new RuntimeException("Reservation not found: " + reservationId);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to approve reservation", ex);
        }
    }

    public void denyReservation(String reservationId) {
        try {
            // Get reservation details
            String query = "SELECT shop_id, fruit_id, quantity FROM reservation WHERE reservation_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, reservationId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Update reservation status to denied (2)
                query = "UPDATE reservation SET status = 2 WHERE reservation_id = ?";
                stmt = prepareStatement(query);
                stmt.setString(1, reservationId);
                stmt.executeUpdate();

                System.out.println("Reservation denied successfully: " + reservationId);
            } else {
                throw new RuntimeException("Reservation not found: " + reservationId);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to deny reservation", ex);
        }
    }

    public ReservationBean listAllReservation(String reservationId) {
        try {
            String query = "SELECT * FROM reservation WHERE reservation_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ReservationBean reservation = new ReservationBean();
                reservation.setReservationId(rs.getString("reservation_id"));
                reservation.setShopId(rs.getString("shop_id"));
                reservation.setFruitId(rs.getString("fruit_id"));
                reservation.setQuantity(rs.getInt("quantity"));
                reservation.setOrderDate(rs.getString("order_date"));
                reservation.setEndDate(rs.getString("end_date"));
                reservation.setStatus(rs.getInt("status"));
                return reservation;
            }
            return null;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to list reservation", ex);
        }
    }

    public ArrayList<ReservationBean> getReservationByShop(String shopId) {
        ArrayList<ReservationBean> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation WHERE shop_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, shopId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReservationBean reservation = new ReservationBean();
                reservation.setReservationId(rs.getString("reservation_id"));
                reservation.setShopId(rs.getString("shop_id"));
                reservation.setFruitId(rs.getString("fruit_id"));
                reservation.setQuantity(rs.getInt("quantity"));
                reservation.setOrderDate(rs.getString("order_date"));
                reservation.setEndDate(rs.getString("end_date"));
                reservation.setStatus(rs.getInt("status"));
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to get reservations by shop", ex);
        }
    }

    public ArrayList<ReservationBean> getReservationByCity(String cityId) {
        ArrayList<ReservationBean> reservations = new ArrayList<>();
        try {
            String query = "SELECT r.* FROM reservation r "
                    + "JOIN shop s ON r.shop_id = s.shop_id "
                    + "WHERE s.city_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, cityId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReservationBean reservation = new ReservationBean();
                reservation.setReservationId(rs.getString("reservation_id"));
                reservation.setShopId(rs.getString("shop_id"));
                reservation.setFruitId(rs.getString("fruit_id"));
                reservation.setQuantity(rs.getInt("quantity"));
                reservation.setOrderDate(rs.getString("order_date"));
                reservation.setEndDate(rs.getString("end_date"));
                reservation.setStatus(rs.getInt("status"));
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to get reservations by city", ex);
        }
    }

    public ArrayList<ReservationBean> getReservationByRegion(String regionId) {
        ArrayList<ReservationBean> reservations = new ArrayList<>();
        try {
            String query = "SELECT r.* FROM reservation r "
                    + "JOIN shop s ON r.shop_id = s.shop_id "
                    + "JOIN city c ON s.city_id = c.city_id "
                    + "WHERE c.region_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, regionId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReservationBean reservation = new ReservationBean();
                reservation.setReservationId(rs.getString("reservation_id"));
                reservation.setShopId(rs.getString("shop_id"));
                reservation.setFruitId(rs.getString("fruit_id"));
                reservation.setQuantity(rs.getInt("quantity"));
                reservation.setOrderDate(rs.getString("order_date"));
                reservation.setEndDate(rs.getString("end_date"));
                reservation.setStatus(rs.getInt("status"));
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to get reservations by region", ex);
        }
    }

    public ArrayList<ReservationBean> getReservationByUser(String userId) {
        ArrayList<ReservationBean> reservations = new ArrayList<>();
        try {
            String query = "SELECT r.* FROM reservation r "
                    + "JOIN user u ON r.shop_id = u.shop_id "
                    + "WHERE u.user_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReservationBean reservation = new ReservationBean();
                reservation.setReservationId(rs.getString("reservation_id"));
                reservation.setShopId(rs.getString("shop_id"));
                reservation.setFruitId(rs.getString("fruit_id"));
                reservation.setQuantity(rs.getInt("quantity"));
                reservation.setOrderDate(rs.getString("order_date"));
                reservation.setEndDate(rs.getString("end_date"));
                reservation.setStatus(rs.getInt("status"));
                reservations.add(reservation);
            }
            return reservations;
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to get reservations by user", ex);
        }
    }

    // Helper method for borrow ID generation
    private String generateBorrowId() {
        try {
            // Get the last borrow ID
            String query = "SELECT borrow_id FROM borrow ORDER BY borrow_id DESC LIMIT 1";
            ResultSet rs = executeQuery(query);
            String lastId = null;
            if (rs.next()) {
                lastId = rs.getString("borrow_id");
            }

            // If no existing borrow, start with b001
            if (lastId == null) {
                return "b001";
            }

            // Generate next ID using IdManager
            return IdManager.nextId(lastId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to generate borrow ID", e);
        }
    }

    // Helper method to check if shops are in the same city
    private void checkSameCity(String fromShopId, String toShopId) throws SQLException {
        String cityQuery = "SELECT s1.city_id, s2.city_id "
                + "FROM shop s1 JOIN shop s2 "
                + "ON s1.city_id = s2.city_id "
                + "WHERE s1.shop_id = ? AND s2.shop_id = ?";
        PreparedStatement cityStmt = prepareStatement(cityQuery);
        cityStmt.setString(1, fromShopId);
        cityStmt.setString(2, toShopId);
        ResultSet cityRs = cityStmt.executeQuery();

        if (!cityRs.next()) {
            throw new RuntimeException("Shops must be in the same city");
        }
    }

    // Helper method to check stock availability
    private void checkStockAvailability(String id, String fruitId, int quantity) throws SQLException {
        String stockQuery = "SELECT SUM(quantity) as total_quantity "
                + "FROM stocklevel "
                + "WHERE (shop_id = ? OR warehouse_id = ?) AND fruit_id = ?";
        PreparedStatement stockStmt = prepareStatement(stockQuery);
        stockStmt.setString(1, id);
        stockStmt.setString(2, id);
        stockStmt.setString(3, fruitId);
        ResultSet stockRs = stockStmt.executeQuery();

        if (!stockRs.next()) {
            throw new RuntimeException("No stock found for ID: " + id + ", fruit: " + fruitId);
        }
        int totalQuantity = stockRs.getInt("total_quantity");

        if (totalQuantity < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + totalQuantity + ", Requested: " + quantity);
        }
    }

    private void updateStockLevel(String fromShopId, String fruitId, int quantity) throws SQLException {
        // Decrease quantity in from shop
        String decreaseQuery = "UPDATE stocklevel "
                + "SET quantity = quantity - ? "
                + "WHERE shop_id = ? AND fruit_id = ?";
        PreparedStatement decreaseStmt = prepareStatement(decreaseQuery);
        decreaseStmt.setInt(1, quantity);
        decreaseStmt.setString(2, fromShopId);
        decreaseStmt.setString(3, fruitId);
        decreaseStmt.executeUpdate();
    }

    // Borrow methods
    public void createBorrowRequest(String fromShopId, String toShopId, String fruitId, int quantity) {
        try {
            // fromShopId = target shop providing the fruit
            // toShopId = requesting shop that wants the fruit
            checkSameCity(fromShopId, toShopId);
            checkStockAvailability(fromShopId, fruitId, quantity);

            // Generate borrow ID
            String borrowId = generateBorrowId();

            // Insert borrow request
            String insertQuery = "INSERT INTO borrow (borrow_id, from_shop, to_shop, fruit_id, quantity, status, date) "
                    + "VALUES (?, ?, ?, ?, ?, 0, CURRENT_DATE())";
            PreparedStatement insertStmt = prepareStatement(insertQuery);
            insertStmt.setString(1, borrowId);
            insertStmt.setString(2, fromShopId); // from_shop = target shop providing the fruit
            insertStmt.setString(3, toShopId);   // to_shop = requesting shop
            insertStmt.setString(4, fruitId);
            insertStmt.setInt(5, quantity);
            insertStmt.executeUpdate();

            // We don't need to update stock level at request creation time
            // Stock will be updated only when the request is approved
            System.out.println("Borrow request created successfully: " + borrowId);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create borrow request: " + e.getMessage(), e);
        }
    }

    public void approveBorrow(String borrowId) {
        try {
            String query = "SELECT from_shop, to_shop, fruit_id, quantity FROM borrow WHERE borrow_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, borrowId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String fromShopId = rs.getString("from_shop"); // target shop providing the fruit
                String toShopId = rs.getString("to_shop");     // requesting shop
                String fruitId = rs.getString("fruit_id");
                int quantity = rs.getInt("quantity");

                // Update stock levels
                // Decrease in from shop (target) since they're providing the fruit
                updateStockLevel(fromShopId, fruitId, -quantity);
                // Increase in to shop (requesting) since they're receiving the fruit
                updateStockLevel(toShopId, fruitId, quantity);

                // Update borrow status
                query = "UPDATE borrow SET status = 1 WHERE borrow_id = ?";
                stmt = prepareStatement(query);
                stmt.setString(1, borrowId);
                stmt.executeUpdate();

                System.out.println("Borrow request approved successfully: " + borrowId);
            } else {
                throw new RuntimeException("Borrow request not found: " + borrowId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to approve borrow request", e);
        }
    }

    public void denyBorrow(String borrowId) {
        try {
            // Get borrow details
            String query = "SELECT from_shop, fruit_id, quantity FROM borrow WHERE borrow_id = ?";
            PreparedStatement selectStmt = prepareStatement(query);
            selectStmt.setString(1, borrowId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Update borrow status to denied (status = 2)
                String updateQuery = "UPDATE borrow SET status = 2 WHERE borrow_id = ?";
                PreparedStatement updateStmt = prepareStatement(updateQuery);
                updateStmt.setString(1, borrowId);
                updateStmt.executeUpdate();

                System.out.println("Borrow request denied successfully: " + borrowId);
            } else {
                throw new RuntimeException("Borrow request not found: " + borrowId);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to deny borrow request: " + e.getMessage(), e);
        }
    }

    public ArrayList<BorrowBean> listAllBorrow() {
        try {
            String query = "SELECT * FROM borrow";
            ResultSet rs = executeQuery(query);
            ArrayList<BorrowBean> borrows = new ArrayList<>();

            // Debug: Print SQL query being executed
            System.out.println("Executing query: " + query);

            int count = 0;
            while (rs.next()) {
                count++;
                BorrowBean borrow = new BorrowBean();
                borrow.setBorrowId(rs.getString("borrow_id"));
                borrow.setFromShop(rs.getString("from_shop"));
                borrow.setToShop(rs.getString("to_shop"));
                borrow.setFruitId(rs.getString("fruit_id"));
                borrow.setQuantity(rs.getInt("quantity"));
                borrow.setStatus(rs.getInt("status"));
                borrow.setDate(rs.getString("date"));

                // Debug: Print each borrow record
                System.out.println("Found borrow record: " + borrow);

                borrows.add(borrow);
            }

            System.out.println("Total borrow records found in database: " + count);
            return borrows;
        } catch (SQLException e) {
            System.out.println("Error in listAllBorrow: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to get borrow requests", e);
        }
    }

    public ArrayList<BorrowBean> getBorrowByCity(String cityId) {
        try {
            String query = "SELECT b.* FROM borrow b "
                    + "JOIN shop s ON s.shop_id = b.from_shop OR s.shop_id = b.to_shop "
                    + "WHERE s.city_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, cityId);

            ResultSet rs = stmt.executeQuery();
            ArrayList<BorrowBean> borrows = new ArrayList<>();
            while (rs.next()) {
                BorrowBean borrow = new BorrowBean();
                borrow.setBorrowId(rs.getString("borrow_id"));
                borrow.setFromShop(rs.getString("from_shop"));
                borrow.setToShop(rs.getString("to_shop"));
                borrow.setFruitId(rs.getString("fruit_id"));
                borrow.setQuantity(rs.getInt("quantity"));
                borrow.setStatus(rs.getInt("status"));
                borrow.setDate(rs.getString("date"));
                borrows.add(borrow);
            }
            return borrows;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get borrows by city", e);
        }
    }

    public void processCheckOutToCentral(String warehouseId, String fruitId, int quantity, String regionId) {
        try {
            // Check if warehouse has enough stock
            checkStockAvailability(warehouseId, fruitId, quantity);

            // Get central warehouse ID for the region
            String centralWarehouseId = "cw" + getCentralWarehouseNumber(regionId);

            // Update both warehouses in a single transaction
            String updateQuery = "UPDATE stocklevel "
                    + "SET quantity = CASE "
                    + "WHEN stock_id = ? THEN quantity - ? "
                    + "WHEN stock_id = ? THEN quantity + ? "
                    + "END "
                    + "WHERE stock_id IN (?, ?)";

            PreparedStatement stmt = prepareStatement(updateQuery);

            // Set parameters for source warehouse (decrease)
            stmt.setString(1, generateWarehouseStockId(warehouseId, fruitId));
            stmt.setInt(2, quantity);

            // Set parameters for central warehouse (increase)
            stmt.setString(3, generateWarehouseStockId(centralWarehouseId, fruitId));
            stmt.setInt(4, quantity);

            // Set parameters for WHERE clause
            stmt.setString(5, generateWarehouseStockId(warehouseId, fruitId));
            stmt.setString(6, generateWarehouseStockId(centralWarehouseId, fruitId));

            stmt.executeUpdate();

            System.out.println("Stock check-out to central warehouse processed successfully for region: " + regionId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to process check-out to central: " + e.getMessage(), e);
        }
    }

    public StockLevelBean getStockByWarehouse(String warehouseId) {
        try {
            String query = "SELECT sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id, SUM(sl.quantity) as total_quantity "
                    + "FROM stocklevel sl "
                    + "WHERE sl.warehouse_id = ? "
                    + "GROUP BY sl.stock_id, sl.shop_id, sl.fruit_id, sl.warehouse_id";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, warehouseId);
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
            throw new RuntimeException("Failed to get stock by warehouse", ex);
        }
    }

    // Helper method to get central warehouse number based on region
    private String getCentralWarehouseNumber(String regionId) {
        switch (regionId) {
            case "r001":
                return "001"; // Hong Kong
            case "r002":
                return "002"; // USA
            case "r003":
                return "003"; // Japan
            default:
                throw new IllegalArgumentException("Invalid region ID: " + regionId);
        }
    }

    // Process checkout from central warehouse to local shop
    public void processCheckOutToLocal(String centralWarehouseId, String shopId, String fruitId, int quantity) {
        try {
            // Check if warehouse has enough stock
            checkStockAvailability(centralWarehouseId, fruitId, quantity);

            // Update both locations in a single transaction
            String updateQuery = "UPDATE stocklevel "
                    + "SET quantity = CASE "
                    + "WHEN stock_id = ? THEN quantity - ? "
                    + "WHEN stock_id = ? THEN quantity + ? "
                    + "END "
                    + "WHERE stock_id IN (?, ?)";

            PreparedStatement stmt = prepareStatement(updateQuery);

            // Set parameters for warehouse (decrease)
            stmt.setString(1, generateWarehouseStockId(centralWarehouseId, fruitId));
            stmt.setInt(2, quantity);

            // Set parameters for shop (increase)
            stmt.setString(3, generateShopStockId(shopId, fruitId));
            stmt.setInt(4, quantity);

            // Set parameters for WHERE clause
            stmt.setString(5, generateWarehouseStockId(centralWarehouseId, fruitId));
            stmt.setString(6, generateShopStockId(shopId, fruitId));

            stmt.executeUpdate();

            System.out.println("Stock check-out to local shop processed successfully");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to process check-out to local: " + e.getMessage(), e);
        }
    }

    // City methods
    public ArrayList<CityBean> listAllCities() {
        ArrayList<CityBean> cities = new ArrayList<>();
        try {
            ResultSet rs = executeQuery("SELECT * FROM city");
            while (rs.next()) {
                CityBean city = new CityBean();
                city.setCityId(rs.getString("city_id"));
                city.setCityName(rs.getString("city_name"));
                city.setRegionId(rs.getString("region_id"));
                cities.add(city);
            }
            System.out.println("Retrieved " + cities.size() + " cities from database");
        } catch (SQLException ex) {
            System.out.println("Error retrieving cities: " + ex.getMessage());
            throw new RuntimeException("Failed to list cities", ex);
        }
        return cities;
    }

    // Shop methods
    public ArrayList<ShopBean> listAllShops() {
        ArrayList<ShopBean> shops = new ArrayList<>();
        try {
            ResultSet rs = executeQuery("SELECT * FROM shop");
            while (rs.next()) {
                ShopBean shop = new ShopBean();
                shop.setShopId(rs.getString("shop_id"));
                shop.setShopName(rs.getString("shop_name"));
                shop.setCityId(rs.getString("city_id"));
                shops.add(shop);
            }
            System.out.println("Retrieved " + shops.size() + " shops from database");
        } catch (SQLException ex) {
            System.out.println("Error retrieving shops: " + ex.getMessage());
            throw new RuntimeException("Failed to list shops", ex);
        }
        return shops;
    }

    // Get shops in the same city as the specified shop
    public ArrayList<ShopBean> getShopsInSameCity(String shopId) {
        ArrayList<ShopBean> shops = new ArrayList<>();
        try {
            // First, get the city of the specified shop
            String cityQuery = "SELECT city_id FROM shop WHERE shop_id = ?";
            PreparedStatement cityStmt = prepareStatement(cityQuery);
            cityStmt.setString(1, shopId);
            ResultSet cityRs = cityStmt.executeQuery();

            if (cityRs.next()) {
                String cityId = cityRs.getString("city_id");

                // Get all shops in the same city
                String shopsQuery = "SELECT * FROM shop WHERE city_id = ? AND shop_id != ?";
                PreparedStatement shopsStmt = prepareStatement(shopsQuery);
                shopsStmt.setString(1, cityId);
                shopsStmt.setString(2, shopId); // Exclude the current shop
                ResultSet rs = shopsStmt.executeQuery();

                while (rs.next()) {
                    ShopBean shop = new ShopBean();
                    shop.setShopId(rs.getString("shop_id"));
                    shop.setShopName(rs.getString("shop_name"));
                    shop.setCityId(rs.getString("city_id"));
                    shops.add(shop);
                }

                System.out.println("Retrieved " + shops.size() + " shops in the same city (City ID: " + cityId + ")");
            } else {
                System.out.println("Shop not found: " + shopId);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving shops in same city: " + ex.getMessage());
            throw new RuntimeException("Failed to list shops in same city", ex);
        }
        return shops;
    }

    // Get the city ID of a shop
    public String getShopCityId(String shopId) {
        try {
            String query = "SELECT city_id FROM shop WHERE shop_id = ?";
            PreparedStatement stmt = prepareStatement(query);
            stmt.setString(1, shopId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("city_id");
            }
            return null;
        } catch (SQLException ex) {
            System.out.println("Error retrieving shop city ID: " + ex.getMessage());
            throw new RuntimeException("Failed to get shop city ID", ex);
        }
    }

    // Get region ID for a shop using city as intermediary
    public String getRegionIdForShop(String shopId) {
        String regionId = "r001"; // Default region if not found
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT c.region_id FROM shop s " +
                "JOIN city c ON s.city_id = c.city_id " +
                "WHERE s.shop_id = ?"
            );
            
            pstmt.setString(1, shopId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                regionId = rs.getString("region_id");
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return regionId;
    }
    
    // Get region ID for a user using their user ID
    public String getRegionIdForUser(String userId) {
        String regionId = "r001"; // Default region if not found
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT c.region_id FROM user u " +
                "JOIN shop s ON u.shop_id = s.shop_id " +
                "JOIN city c ON s.city_id = c.city_id " +
                "WHERE u.user_id = ?"
            );
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                regionId = rs.getString("region_id");
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return regionId;
    }
    
    // Version for all consumption records (for senior staff)
public ArrayList<ConsumptionBean> listAllConsumption() {
        try {
            ArrayList<ConsumptionBean> consumptions = new ArrayList();
            CodeManager cM = new CodeManager();
            String query = "SELECT c.*, f.fruit_name, r.region_name "
                    + "FROM consumption c "
                    + "LEFT JOIN fruit f ON c.fruit_id = f.fruit_id "
                    + "LEFT JOIN region r ON c.region_id = r.region_id ";
            PreparedStatement stmt = prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ConsumptionBean consumption = new ConsumptionBean();
                consumption.setConsumptionId(rs.getString("consumption_id"));
                consumption.setFruitId(rs.getString("fruit_name"));
                consumption.setQuantity(rs.getInt("quantity"));
                consumption.setRegionId(rs.getString("region_name"));
                consumption.setSeason(cM.getSeason(rs.getInt("season")));

                consumptions.add(consumption);
            }
            return consumptions;
        } catch (SQLException ex) {
            System.err.println("[ERROR] SQL Exception occurred:");
            System.err.println("SQL State: " + ex.getSQLState());
            System.err.println("Error Code: " + ex.getErrorCode());
            System.err.println("Message: " + ex.getMessage());
            throw new RuntimeException("Failed to list consumptions", ex);
        }
    }

    // Version for region-specific consumption records
    public ArrayList<ShopConsumptionBean> listAllConsumption(String regionId) {
        ArrayList<ShopConsumptionBean> consumptions = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM consumption WHERE region_id = ?"
            );
            stmt.setString(1, regionId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                ShopConsumptionBean consumption = new ShopConsumptionBean();
                consumption.setConsumptionId(rs.getString("consumption_id"));
                consumption.setFruitId(rs.getString("fruit_id"));
                consumption.setRegionId(rs.getString("region_id"));
                consumption.setQuantity(rs.getInt("quantity"));
                consumption.setSeason(rs.getInt("season"));
                consumptions.add(consumption);
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return consumptions;
    }

    public ShopConsumptionBean getConsumptionById(String consumptionId) {
        ShopConsumptionBean consumption = null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "SELECT * FROM consumption WHERE consumption_id = ?"
            );
            
            pstmt.setString(1, consumptionId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                consumption = new ShopConsumptionBean();
                consumption.setConsumptionId(rs.getString("consumption_id"));
                consumption.setFruitId(rs.getString("fruit_id"));
                consumption.setRegionId(rs.getString("region_id"));
                consumption.setQuantity(rs.getInt("quantity"));
                consumption.setSeason(rs.getInt("season"));
            }
            
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return consumption;
    }

    public void deleteConsumption(String consumptionId) {
        try {
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM consumption WHERE consumption_id = ?");
            pstmt.setString(1, consumptionId);
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateConsumption(String consumptionId, String fruitId, String regionId, int quantity, int season) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "UPDATE consumption SET fruit_id = ?, region_id = ?, quantity = ?, season = ? WHERE consumption_id = ?"
            );
            
            pstmt.setString(1, fruitId);
            pstmt.setString(2, regionId);
            pstmt.setInt(3, quantity);
            pstmt.setInt(4, season);
            pstmt.setString(5, consumptionId);
            
            pstmt.executeUpdate();
            
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add sample consumption data for testing
    public void addSampleConsumptionData() {
        try {
            // First check if consumption data already exists
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM consumption");
            rs.next();
            int count = rs.getInt("count");
            
            if (count == 0) {
                // Insert sample consumption records
                PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO consumption (consumption_id, fruit_id, region_id, quantity, season) " +
                    "VALUES (?, ?, ?, ?, ?)"
                );
                
                // First record
                pstmt.setString(1, "m001");
                pstmt.setString(2, "f001");
                pstmt.setString(3, "r001");
                pstmt.setInt(4, 20);
                pstmt.setInt(5, 1);  // Summer
                pstmt.executeUpdate();
                
                // Second record
                pstmt.setString(1, "m002");
                pstmt.setString(2, "f002");
                pstmt.setString(3, "r001");
                pstmt.setInt(4, 10);
                pstmt.setInt(5, 2);  // Fall
                pstmt.executeUpdate();
                
                pstmt.close();
                System.out.println("Sample consumption data added successfully");
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<WarehouseBean> listAllWarehouse() {
        try {
            ArrayList<WarehouseBean> list = new ArrayList();
            String sql = "SELECT * FROM warehouse";
            PreparedStatement stmt = prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                WarehouseBean item = new WarehouseBean();
                item.setWarehouseId(rs.getString("warehouse_id"));
                item.setCityId(rs.getString("city_id"));
                item.setWarehouseName(rs.getString("warehouse_name"));
                list.add(item);
            }
            return list;
        } catch (SQLException ex) {
            System.err.println("[ERROR] SQL Exception occurred:");
            System.err.println("SQL State: " + ex.getSQLState());
            System.err.println("Error Code: " + ex.getErrorCode());
            System.err.println("Message: " + ex.getMessage());
            throw new RuntimeException("Failed to list consumptions", ex);
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

    // Get all fruits in the user's region
    public ArrayList<FruitBean> getFruitsInRegion(String regionId) {
        ArrayList<FruitBean> fruits = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT f.* FROM fruit f " +
                "JOIN city c ON f.source_city_id = c.city_id " +
                "WHERE c.region_id = ?"
            );
            stmt.setString(1, regionId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                FruitBean fruit = new FruitBean();
                fruit.setFruitId(rs.getString("fruit_id"));
                fruit.setFruitName(rs.getString("fruit_name"));
                fruits.add(fruit);
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return fruits;
    }

    // Get all regions
    public ArrayList<String> getAllRegions() {
        ArrayList<String> regions = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT region_id FROM city");
            
            while (rs.next()) {
                regions.add(rs.getString("region_id"));
            }
            
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return regions;
    }
}
