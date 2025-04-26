/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author local_user
 */
@WebServlet(name = "SeniorAccountChangePwServlet", urlPatterns = {"/password_change"})
public class SeniorAccountChangePwServlet extends HttpServlet {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String oldPassword = req.getParameter("old_password");
        String validate = req.getParameter("validate_password");
        String newPassword = req.getParameter("new_password");
        String msg;

        String sql = "UPDATE user SET password = ? WHERE user_id = ?";
        if (oldPassword.contentEquals(validate)) {
            msg = "success";
            req.setAttribute("error", msg);
            req.getRequestDispatcher("account_password").forward(req, resp);
            try {
                Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                PreparedStatement stmnt = connection.prepareStatement(sql);

                stmnt.setString(1, newPassword);
                stmnt.setString(2, userId);

                int rowsUpdated = stmnt.executeUpdate();
                if (rowsUpdated > 0) {
                    msg = "success";
                    req.setAttribute("error", msg);
                    req.getRequestDispatcher("account_password").forward(req, resp);
                } else {
                    msg = "access_denied";
                    req.setAttribute("error", msg);
                    req.getRequestDispatcher("account_password").forward(req, resp);
                }
            } catch (SQLException ex) {
                Logger.getLogger(SeniorAccountChangePwServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            msg = "access_denied";
            req.setAttribute("error", msg);
            req.getRequestDispatcher("account_password").forward(req, resp);
        }
    }

}
