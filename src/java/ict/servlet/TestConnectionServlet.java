package ict.servlet;

import ict.db.ProjectDB;
import ict.bean.StockLevelBean;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "TestConnectionServlet", urlPatterns = {"/test-connection"})
public class TestConnectionServlet extends HttpServlet {
    

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Test database connection
            ProjectDB db = ProjectDB.getInstance();
            
            // Test getting stock data
            String shopId = "s001"; // Test shop ID
            ArrayList<StockLevelBean> stockList = db.getStockByShop(shopId);
            request.setAttribute("stockList", stockList);
            
            request.setAttribute("connectionStatus", "Database connection successful!");
            
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
        }
        
        request.getRequestDispatcher("/WEB-INF/test-connection.jsp").forward(request, response);
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
