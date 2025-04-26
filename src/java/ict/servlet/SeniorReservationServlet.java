/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.ReserveRecordBean;
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
@WebServlet(name = "SeniorReservationServlet", urlPatterns = {"/reservation"})
public class SeniorReservationServlet extends HttpServlet {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "SELECT r.*, s.shop_name, f.fruit_name "
                + "FROM reservation r "
                + "LEFT JOIN shop s ON r.shop_id = s.shop_id "
                + "LEFT JOIN fruit f ON r.fruit_id = f.fruit_id ";
        ArrayList<ReserveRecordBean> list = new ArrayList();
        CodeManager cM = new CodeManager();
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                ReserveRecordBean item = new ReserveRecordBean();

                item.setReservation_id(rs.getString("reservation_id"));
                item.setShop_id(rs.getString("shop_id"));
                item.setShop_name(rs.getString("shop_name"));
                item.setFruit_id(rs.getString("fruit_id"));
                item.setFruit_name(rs.getString("fruit_name"));
                item.setQuantity(rs.getInt("quantity"));
                
                list.add(item);
            }
            req.setAttribute("list", list);
            req.getRequestDispatcher("/senior/reservation.jsp").forward(req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(SeniorReservationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
