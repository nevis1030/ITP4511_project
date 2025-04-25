package ict.servlet;

import ict.bean.FruitBean;
import ict.bean.StockLevelBean;
import ict.db.ProjectDB;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DisplayStockServlet", urlPatterns = {"/display-stock"})
public class DisplayStockServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get shop ID
            String shopId = "s001";
            
            // Get database instance
            ProjectDB db = ProjectDB.getInstance();
            
            // Get all fruits
            ArrayList<FruitBean> fruits = db.listAllFruits();
            
            // Get stock levels for each fruit
            ArrayList<StockLevelBean> stocks = new ArrayList<>();
            if (shopId != null) {
                for (FruitBean fruit : fruits) {
                    StockLevelBean stock = db.getStockByShopAndFruit(shopId, fruit.getFruitId());
                    if (stock != null) {
                        stocks.add(stock);
                    }
                }
            }
            
            // Set attributes for JSP
            request.setAttribute("fruits", fruits);
            request.setAttribute("stocks", stocks);
            
            // Forward to stock display page
            request.getRequestDispatcher("/WEB-INF/stock-display.jsp").forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
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
