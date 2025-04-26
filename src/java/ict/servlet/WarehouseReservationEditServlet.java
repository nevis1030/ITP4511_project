/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author local_user
 */
@WebServlet(name="WarehouseReservationEditServlet", urlPatterns={"/warehouse_reservation_edit"})
public class WarehouseReservationEditServlet extends HttpServlet {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reservationId = req.getParameter("reservationId");
        String userId = req.getParameter("userId");
        int approval = Integer.parseInt(req.getParameter("approval"));
        String sql = "UPDATE reservation SET status = ? WHERE reservation_id = ?";
        Connection connection;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setInt(1, approval);
            stmnt.setString(2, reservationId);
            int rowsUpdated = stmnt.executeUpdate();
            if (rowsUpdated > 0) {
                req.getRequestDispatcher("warehouse_reservation?userId=" + userId).forward(req, resp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WarehouseStockServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
