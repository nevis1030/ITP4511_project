/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.ShopBean;
import ict.bean.WarehouseBean;
import ict.db.ProjectDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author local_user
 */
@WebServlet(name = "SeniorEmployeeAddServlet", urlPatterns = {"/employee_add"})
public class SeniorEmployeeAddServlet extends HttpServlet {

    ProjectDB db = ProjectDB.getInstance();
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String displayName = req.getParameter("displayName");
        int role = Integer.parseInt(req.getParameter("role"));
        String shopId = req.getParameter("shopId");
        String warehouseId = req.getParameter("warehouseId");

        String sql = "INSERT INTO user (user_id, shop_id, warehouse_id, display_name, username, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setString(1, userId);
            if (shopId == null
                    || shopId.equalsIgnoreCase("null")
                    || shopId.trim().isEmpty()) {
                stmnt.setNull(2, Types.VARCHAR);
            } else {
                stmnt.setString(2, shopId);
            }
            if (warehouseId == null
                    || warehouseId.equalsIgnoreCase("null")
                    || warehouseId.trim().isEmpty()) {
                stmnt.setNull(3, Types.VARCHAR);
            } else {
                stmnt.setString(3, warehouseId);
            }
            stmnt.setString(4, displayName);
            stmnt.setString(5, username);
            stmnt.setString(6, password);
            stmnt.setInt(7, role);
            int rowsAffected = stmnt.executeUpdate();
            if(rowsAffected > 0){
                req.getRequestDispatcher("employee").forward(req, resp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeniorEmployeeEditServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<ShopBean> shops = new ArrayList();
        ArrayList<WarehouseBean> warehouses = new ArrayList();
        shops = db.listAllShops();
        warehouses = db.listAllWarehouse();
        String userId = db.getNextUserId();

        req.setAttribute("userId", userId);
        req.setAttribute("shops", shops);
        req.setAttribute("warehouses", warehouses);
        req.getRequestDispatcher("/senior/employee_add.jsp").forward(req, resp);
    }
}
