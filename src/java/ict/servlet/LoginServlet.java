/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import ict.bean.*;
import jakarta.servlet.annotation.WebServlet;
/**
 *
 * @author local_user
 */
@WebServlet(name="LoginServlet", urlPatterns={"/login"})
public class LoginServlet extends HttpServlet{
    private static final String LOGIN_PAGE = "index.jsp";
    private static final String SHOP_DASHBOARD = "shop_dashboard.jsp";
    private static final String WAREHOUSE_DASHBOARD = "warehouse_dashboard.jsp";
    private static final String MANAGEMENT_DASHBOARD = "senior_dashboard.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Step 1: Validate credentials
            UserBean userBean = validateUser(username, password);
            if (userBean != null) {
                // Step 2: Save user ID in session
                HttpSession session = request.getSession();
                session.setAttribute("userBean", userBean);

                // Step 3: Redirect based on role
                int role = userBean.getRole();
                switch (role) {
                    case 0:
                        response.sendRedirect(SHOP_DASHBOARD);
                        break;
                    case 1:
                        response.sendRedirect(WAREHOUSE_DASHBOARD);
                        break;
                    case 2:
                        response.sendRedirect(MANAGEMENT_DASHBOARD);
                        break;
                    default:
                        response.sendRedirect(LOGIN_PAGE);
                }
            } else {
                // Invalid credentials
                response.sendRedirect(LOGIN_PAGE + "?error=invalid_credentials");
            }
        } catch (SQLException e) {
            // Handle database errors
            request.setAttribute("error", "Database error: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(LOGIN_PAGE);
            dispatcher.forward(request, response);
        }
    }

    private UserBean validateUser(String username, String password) throws SQLException {
        // Assuming you have a database connection
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/itp4511_project", "root", "");
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("SELECT * FROM user WHERE username = ?");
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    return new UserBean(
                            rs.getString("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getInt("role"),
                            rs.getString("display_name")
                    );
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        return null;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(LOGIN_PAGE);
    }
}
