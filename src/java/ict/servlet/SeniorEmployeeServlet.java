/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.EmployeeBean;
import ict.util.CodeManager;
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
@WebServlet(name = "SeniorEmployeeServlet", urlPatterns = {"/employee"})
public class SeniorEmployeeServlet extends HttpServlet {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/itp4511_project?useSSL=false";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String sql = "SELECT \n"
            + "    u.user_id,\n"
            + "    u.display_name,\n"
            + "    u.role,\n"
            + "    COALESCE(\n"
            + "        s.shop_name, \n"
            + "        w.warehouse_name, \n"
            + "        'N/A'\n"
            + "    ) AS location_name,\n"
            + "    COALESCE(\n"
            + "        r.region_name,\n"
            + "        (SELECT r2.region_name \n"
            + "         FROM warehouse w2\n"
            + "         JOIN city c2 ON w2.city_id = c2.city_id\n"
            + "         JOIN region r2 ON c2.region_id = r2.region_id\n"
            + "         WHERE w2.warehouse_id = u.warehouse_id),\n"
            + "        'N/A'\n"
            + "    ) AS region_name,\n"
            + "    CASE \n"
            + "        WHEN u.shop_id IS NOT NULL THEN 'shop'\n"
            + "        WHEN u.warehouse_id IS NOT NULL THEN 'warehouse'\n"
            + "        ELSE 'N/A'\n"
            + "    END AS location_type\n"
            + "FROM \n"
            + "    user u\n"
            + "LEFT JOIN \n"
            + "    shop s ON u.shop_id = s.shop_id\n"
            + "LEFT JOIN \n"
            + "    city sc ON s.city_id = sc.city_id\n"
            + "LEFT JOIN \n"
            + "    region sr ON sc.region_id = sr.region_id\n"
            + "LEFT JOIN \n"
            + "    warehouse w ON u.warehouse_id = w.warehouse_id\n"
            + "LEFT JOIN \n"
            + "    city wc ON w.city_id = wc.city_id\n"
            + "LEFT JOIN \n"
            + "    region wr ON wc.region_id = wr.region_id\n"
            + "LEFT JOIN \n"
            + "    region r ON COALESCE(sc.region_id, wc.region_id) = r.region_id";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doProcess(req, resp);
    }
    

    private void doProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<EmployeeBean> users = new ArrayList();
        CodeManager cM = new CodeManager();
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            PreparedStatement stmnt = connection.prepareStatement(sql);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                String displayName = rs.getString("display_name");
                String role = cM.getRole(rs.getInt("role"));
                String regionName = rs.getString("region_name");

                // Determine location
                String location;
                String locationType = rs.getString("location_type");
                if ("shop".equals(locationType)) {
                    location = rs.getString("location_name") + " (Shop)";
                } else if ("warehouse".equals(locationType)) {
                    location = rs.getString("location_name") + " (Warehouse)";
                } else {
                    location = "N/A";
                }

                users.add(new EmployeeBean(userId, regionName, location, displayName, role));
            }
            req.setAttribute("users", users);
            req.getRequestDispatcher("/senior/employee.jsp").forward(req, resp);
        } catch (SQLException ex) {
            Logger.getLogger(SeniorAccountChangePwServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
