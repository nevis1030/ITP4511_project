package ict.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {
    
    protected void checkLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
    }
    
    protected void checkAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || 
            !session.getAttribute("isAdmin").equals(true)) {
            response.sendRedirect("login");
            return;
        }
    }
    
    protected void checkShopStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || 
            session.getAttribute("shopId") == null) {
            response.sendRedirect("login");
            return;
        }
    }
    
    protected void checkWarehouseStaff(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || 
            session.getAttribute("warehouseId") == null) {
            response.sendRedirect("login");
            return;
        }
    }
}
