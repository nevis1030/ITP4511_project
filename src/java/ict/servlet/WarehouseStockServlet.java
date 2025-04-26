/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.WarehouseStockBean;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author local_user
 */
@WebServlet(name = "WarehouseStockServlet", urlPatterns = {"/warehouse_stock"})
public class WarehouseStockServlet extends HttpServlet {

    String sql = "SELECT sl.*, f.fruit_name "
            + "FROM stocklevel sl "
            + "JOIN fruit f ON sl.fruit_id = f.fruit_id "
            + "JOIN user u ON sl.warehouse_id = u.warehouse_id "
            + "WHERE u.user_id = ? ";

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }
    
    

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection connection;
        ArrayList<WarehouseStockBean> list = new ArrayList();
        String userId = req.getParameter("userId");
//        System.out.println("[DEBUG][warehouse_stock][parameter] userId = "+req.getParameter("userId"));
//        System.out.println("[DEBUG][warehouse_stock] userId = "+userId);
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setString(1, userId);

            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                WarehouseStockBean item = new WarehouseStockBean();
                item.setStock_id(rs.getString("stock_id"));
                item.setFruit_id(rs.getString("fruit_id"));
                item.setFruit_name(rs.getString("fruit_name"));
                item.setQuantity(rs.getInt("quantity"));
                
                list.add(item);
            }
            req.setAttribute("list", list);
            req.getRequestDispatcher("/warehouse/stocklevel.jsp").forward(req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseStockServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
