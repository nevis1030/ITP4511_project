/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import ict.bean.CityBean;
import ict.bean.FruitBean;
import ict.bean.StockLevelBean;
import ict.bean.UserBean;
import ict.db.ProjectDB;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet to handle shop stock data display
 * 
 * @author local_user
 */
@WebServlet(name = "ShopStockServlet", urlPatterns = {"/shop/stock"})
public class ShopStockServlet extends BaseServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     * Retrieves shop stock data and forwards to stock.jsp for display
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        HttpSession session = request.getSession(false);
        
        // Get the user from session
        UserBean user = (UserBean) session.getAttribute("user");
        String shopId = user.getShopId();
        
        ProjectDB db = ProjectDB.getInstance();
        
        // Get stock data for the shop
        ArrayList<StockLevelBean> stocks = db.getStockByShop(shopId);
        
        // Get all fruits for reference (to display fruit names)
        ArrayList<FruitBean> fruits = db.listAllFruits();
        
        // Get all cities for reference (to display source city names)
        ArrayList<CityBean> cities = db.listAllCities();
        
        // Set the data as request attributes
        request.setAttribute("stocks", stocks);
        request.setAttribute("fruits", fruits);
        request.setAttribute("cities", cities);
        
        // Check if there's a tab parameter in the URL, preserve it when forwarding
        String tab = request.getParameter("tab");
        String redirectUrl = "/shop_dashboard.jsp";
        
        // If a tab is specified in the URL, preserve it, otherwise default to stock tab
        if (tab != null && !tab.isEmpty()) {
            redirectUrl += "?tab=" + tab;
        } else {
            redirectUrl += "?tab=stock";
        }
        
        // Forward to shop dashboard with the appropriate tab
        RequestDispatcher rd = request.getRequestDispatcher(redirectUrl);
        rd.forward(request, response);
    }
}
