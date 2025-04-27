/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.UserBean;
import ict.db.ProjectDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author local_user
 */
@WebServlet(name = "SeniorEmployeeEditServlet", urlPatterns = {"/employee_edit"})
public class SeniorEmployeeEditServlet extends HttpServlet {

    ProjectDB db = ProjectDB.getInstance();
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sql1 = "SELECT * FROM user WHERE user_id = ?";
        String sql2 = "SELECT * FROM shop";
        String sql3 = "SELECT * FROM warehouse";
        String userId = req.getParameter("targetUser");
//        System.out.println("[DEBUG] parameter targetUser = " + req.getParameter("targetUser"));
//        System.out.println("[DEBUG] targetUser = " + userId);
        UserBean user = new UserBean();
        ArrayList<String> shop_id = new ArrayList();
        ArrayList<String> warehouse_id = new ArrayList();
        Connection connection;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql1);
            stmnt.setString(1, userId);
            try (ResultSet rs = stmnt.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getString("user_id"));
                    user.setDisplayName(rs.getString("display_name"));
                    user.setPassword(rs.getString("password"));
                    user.setUsername(rs.getString("username"));
                    user.setShopId(rs.getString("shop_id"));
                    user.setWarehouseId(rs.getString("warehouse_id"));
                    user.setRole(rs.getInt("role"));
                }
//                System.out.println(user.toString());
                req.setAttribute("user", user);
            }
            stmnt = connection.prepareStatement(sql2);
            try (ResultSet rs = stmnt.executeQuery()) {
                while (rs.next()) {
                    shop_id.add(rs.getString("shop_id"));
//                    System.out.println("[DEBUG] shop_id added = " + rs.getString("shop_id"));
                }
                req.setAttribute("shopList", shop_id);
            }
            stmnt = connection.prepareStatement(sql3);
            try (ResultSet rs = stmnt.executeQuery()) {
                while (rs.next()) {
                    warehouse_id.add(rs.getString("warehouse_id"));
//                    System.out.println("[DEBUG] warehouse_id added = " + rs.getString("warehouse_id"));
                }
                req.setAttribute("warehouseList", warehouse_id);
            }
            req.getRequestDispatcher("/senior/employee_edit.jsp").forward(req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(SeniorEmployeeEditServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String displayName = req.getParameter("displayName");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        int role = Integer.parseInt(req.getParameter("role"));
        String shop_id = req.getParameter("shopId");
        String warehouse_id = req.getParameter("warehouseId");
        
        String delete = req.getParameter("delete");
        
        if(delete.equals("Delete")){
            db.deleteUser(userId);
            req.getRequestDispatcher("employee").forward(req, resp);
        }else{
            UserBean user = new UserBean(userId, username, password, role, shop_id, warehouse_id, displayName);
            db.updateUser(user);
            req.getRequestDispatcher("employee").forward(req, resp);
        }
    }

}
