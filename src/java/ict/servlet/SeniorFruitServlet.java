/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.CityBean;
import ict.bean.SeniorFruitBean;
import ict.bean.FruitBean;
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
@WebServlet(name = "SeniorFruitServlet", urlPatterns = {"/senior_fruit"})
public class SeniorFruitServlet extends HttpServlet {

    ProjectDB db = ProjectDB.getInstance();
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getList(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getList(req, resp);
    }

    private void getList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<SeniorFruitBean> list = new ArrayList();
        String sql = "SELECT f.*, c.city_name AS source_city_name "
                + "FROM fruit f "
                + "JOIN city c ON f.source_city_id = c.city_id ";
        Connection connection;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                SeniorFruitBean item = new SeniorFruitBean();
                item.setFruitId(rs.getString("fruit_id"));
                item.setFruitName(rs.getString("fruit_name"));
                item.setSourceCityId(rs.getString("source_city_id"));
                item.setSourceCityName(rs.getString("source_city_name"));

                list.add(item);
            }
            req.setAttribute("list", list);
            req.getRequestDispatcher("/senior/fruit.jsp").forward(req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(SeniorFruitServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
