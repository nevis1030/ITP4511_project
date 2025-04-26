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
@WebServlet(name = "SeniorReservationServlet", urlPatterns = {"/reservation"})
public class SeniorReservationServlet extends HttpServlet {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql = "SELECT * FROM";
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SeniorReservationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
