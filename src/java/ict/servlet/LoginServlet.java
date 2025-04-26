package ict.servlet;

import ict.bean.UserBean;
import ict.db.ProjectDB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (username != null && password != null) {
            try {
                ProjectDB db = ProjectDB.getInstance();
                UserBean user = db.login(username, password);
                
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                       
                    // Set role-specific attributes
                    switch (user.getRole()) {
                        case 0: // Shop Staff
                            session.setAttribute("shopId", user.getShopId());
                            System.out.println("LoginServlet: Setting shopId=" + user.getShopId());
                            response.sendRedirect("shop_dashboard.jsp");
                            return;
                        case 1: // Warehouse Staff
                            session.setAttribute("warehouseId", user.getWarehouseId());
                            System.out.println("LoginServlet: Setting warehouseId=" + user.getWarehouseId());
                            response.sendRedirect("warehouse_dashboard.jsp");
                            return;
                        case 2: // Senior Manager
                            session.setAttribute("isAdmin", true);
                            System.out.println("LoginServlet: Setting isAdmin=true");
                            response.sendRedirect("senior_dashboard.jsp");
                            return;
                    }
                    
                    // Debug session variables
                    System.out.println("LoginServlet: User role=" + user.getRole());
                    System.out.println("LoginServlet: shopId in session=" + session.getAttribute("shopId"));
                } else {
                    request.setAttribute("error", "Invalid username or password");
                }
            } catch (Exception e) {
                request.setAttribute("error", "Login error: " + e.getMessage());
            }
        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

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
}
