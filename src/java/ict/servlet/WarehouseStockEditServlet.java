/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.WarehouseStockEditBean;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author local_user
 */
@WebServlet(name = "WarehouseStockEditServlet", urlPatterns = {"/warehouse_stock_edit"})
public class WarehouseStockEditServlet extends HttpServlet {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stock_id = req.getParameter("stockId");
        String sql = "SELECT sl.fruit_id, sl.quantity, f.fruit_name "
                + "FROM stocklevel sl "
                + "JOIN fruit f ON sl.fruit_id = f.fruit_id "
                + "WHERE sl.stock_id = ?";
        Connection connection;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setString(1, stock_id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                WarehouseStockEditBean item = new WarehouseStockEditBean();
                item.setFruit_id(rs.getString("fruit_id"));
                item.setFruit_name(rs.getString("fruit_name"));
                item.setQuantity(rs.getInt("quantity"));

                req.setAttribute("item", item);
                req.getRequestDispatcher("/warehouse/stocklevel_edit.jsp").forward(req, resp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseStockEditServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fruitId = req.getParameter("fruitId");
        String userId = req.getParameter("userId");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String sql = "UPDATE stocklevel SET quantity = ? WHERE fruit_id = ?";
        Connection connection;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setInt(1, quantity);
            stmnt.setString(2, fruitId);
            int rowsUpdated = stmnt.executeUpdate();
            if(rowsUpdated > 0){
                req.getRequestDispatcher("warehouse_stock?userId="+userId).forward(req, resp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseStockEditServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
