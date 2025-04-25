/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ict.bean.*;
import ict.util.*;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author local_user
 */
@WebServlet(name = "ListEmployeeServlet", urlPatterns = {"/listemployee"})
public class ListEmployeeServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/itp4511_project";
    private static final String DB_USER = "your_user";
    private static final String DB_PASSWORD = "your_password";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<EmployeeBean> employeeList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL query to fetch the required data
            String sql = "SELECT \n"
                    + "                u.display_name, \n"
                    + "                u.role, \n"
                    + "                u.shop_id, \n"
                    + "                u.warehouse_id,\n"
                    + "                s.shop_name, \n"
                    + "                w.warehouse_name, \n"
                    + "                c.region_id, \n"
                    + "                r.region_name\n"
                    + "            FROM \n"
                    + "                user u\n"
                    + "            LEFT JOIN shop s ON u.shop_id = s.shop_id\n"
                    + "            LEFT JOIN city c ON s.city_id = c.city_id\n"
                    + "            LEFT JOIN region r ON c.region_id = r.region_id\n"
                    + "            LEFT JOIN warehouse wh ON u.warehouse_id = wh.warehouse_id\n"
                    + "            ORDER BY u.display_name";

            // Execute the query
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // Process each row
            while (rs.next()) {
                EmployeeBean employee = new EmployeeBean();

                // Set common fields
                employee.setName(rs.getString("display_name"));
                employee.setRole(rs.getString("role"));

                // Determine location and region
                if (rs.getLong("shop_id") != 0) {
                    // Use shop information
                    employee.setLocation(rs.getString("shop_name"));
                    if (rs.getLong("region_id") != 0) {
                        employee.setRegion(rs.getString("region_name"));
                    } else {
                        employee.setRegion("Unknown Region");
                    }
                } else if (rs.getLong("warehouse_id") != 0) {
                    // Use warehouse information
                    employee.setLocation(rs.getString("warehouse_name"));
                    if (rs.getLong("region_id") != 0) {
                        employee.setRegion(rs.getString("region_name"));
                    } else {
                        employee.setRegion("Unknown Region");
                    }
                } else {
                    // Default to USA if both shop_id and warehouse_id are null
                    employee.setLocation("N/A");
                    employee.setRegion("USA");
                }

                employeeList.add(employee);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while fetching employee data.");
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Create HTML content
        StringBuilder html = new StringBuilder();

//    for (EmployeeBean employee : employeeList) {
//        html.append("<tr>");
//        html.append("<td>").append(employee.getRegion()).append("</td>");
//        html.append("<td>").append(employee.getLocation()).append("</td>");
//        html.append("<td>").append(employee.getName()).append("</td>");
//        html.append("<td>").append(employee.getRole()).append("</td>");
//        html.append("</tr>");
//    }
        for (int i = 0; i < 4; i++) {
            html.append("<tr>");
            html.append("<td>").append(i).append("</td>");
            html.append("<td>").append(i).append("</td>");
            html.append("<td>").append(i).append("</td>");
            html.append("<td>").append(i).append("</td>");
            html.append("</tr>");
        }

        html.append("</tbody>");
        html.append("</table>");

        // Set the response content type
        response.setContentType("text/html");

        // Write the HTML response
        response.getWriter().write(html.toString());
    }
}
