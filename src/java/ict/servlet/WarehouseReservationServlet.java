/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.WarehouseReserveBean;
import ict.util.CodeManager;
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
@WebServlet(name = "WarehouseReservationServlet", urlPatterns = {"/warehouse_reservation"})
public class WarehouseReservationServlet extends HttpServlet {

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
        String userId = req.getParameter("userId");
        String sql = "SELECT r.reservation_id, r.shop_id, s.shop_name, r.fruit_id, "
                + "f.fruit_name, r.order_date, r.end_date, r.quantity, r.status "
                + "FROM reservation r "
                + "JOIN shop s ON r.shop_id = s.shop_id "
                + "JOIN fruit f ON r.fruit_id = f.fruit_id "
                + "JOIN user u ON u.user_id = ? "
                + "JOIN warehouse w ON u.warehouse_id = w.warehouse_id "
                + "WHERE f.source_city_id = w.city_id";
        Connection connection;
        CodeManager cM = new CodeManager();
        try {
            ArrayList<WarehouseReserveBean> list = new ArrayList();
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setString(1, userId);

            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                WarehouseReserveBean item = new WarehouseReserveBean();
                item.setReservationId(rs.getString("reservation_id"));
                item.setShopId(rs.getString("shop_id"));
                item.setShopName(rs.getString("shop_name"));
                item.setFruitId(rs.getString("fruit_id"));
                item.setFruitName(rs.getString("fruit_name"));
                item.setOrderDate(rs.getString("order_date"));
                item.setEndDate(rs.getString("end_date"));
                item.setStatus(cM.getStatus(rs.getInt("status")));
                item.setQuantity(rs.getInt("quantity"));

                list.add(item);
            }
            req.setAttribute("list", list);
            req.getRequestDispatcher("/warehouse/reserve.jsp").forward(req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseStockServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
