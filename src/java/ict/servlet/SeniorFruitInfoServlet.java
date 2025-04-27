/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.CityBean;
import ict.bean.FruitBean;
import ict.bean.SeniorFruitBean;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author local_user
 */
@WebServlet(name="SeniorFruitInfoServlet", urlPatterns={"/senior_fruit_info"})
public class SeniorFruitInfoServlet extends HttpServlet{
    
    ProjectDB db = ProjectDB.getInstance();
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getItem(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getItem(req, resp);
    }

    private void getItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fruitId = req.getParameter("fruitId");
        String sql = "SELECT * FROM fruit WHERE fruit_id = ?";
        Connection connection;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
            stmnt.setString(1, fruitId);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                FruitBean fruit = new FruitBean(rs.getString("fruit_id"), rs.getString("source_city_id"), rs.getString("fruit_name"));
                req.setAttribute("fruit", fruit);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SeniorFruitServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<CityBean> cities = db.listAllCities();
        req.setAttribute("cities", cities);
        req.getRequestDispatcher("/senior/fruit_edit.jsp").forward(req, resp);
    }
}
