/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import ict.bean.CityBean;
import ict.bean.FruitBean;
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


@WebServlet(name = "AddFruitServlet", urlPatterns = {"/shop/add-fruit"})
public class AddFruitServlet extends BaseServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        HttpSession session = request.getSession(false);
        
        // Get the user from session
        UserBean user = (UserBean) session.getAttribute("user");
        String shopId = user.getShopId();
        
        // Store shopId in session for easy access in JSP
        session.setAttribute("shopId", shopId);
        
        ProjectDB db = ProjectDB.getInstance();
        
        ArrayList<FruitBean> fruits = db.listAllFruits();
        
        ArrayList<CityBean> cities = db.listAllCities();
        
        request.setAttribute("fruits", fruits);
        request.setAttribute("cities", cities);
        
        String redirectUrl = "/shop_dashboard.jsp?tab=add-fruit";
        
        RequestDispatcher rd = request.getRequestDispatcher(redirectUrl);
        rd.forward(request, response);
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        // Get form parameters
        String cityId = request.getParameter("cityId");
        String fruitName = request.getParameter("fruitName");
        
        if (cityId == null || cityId.isEmpty() || fruitName == null || fruitName.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/shop/add-fruit?error=Missing required fields");
            return;
        }
        
        try {
            // Add the new fruit
            ProjectDB db = ProjectDB.getInstance();
            db.addFruit(cityId, fruitName);
            
            // Redirect back to add-fruit page with success message
            response.sendRedirect(request.getContextPath() + "/shop/add-fruit?success=Fruit added successfully");
        } catch (Exception e) {
            // Handle errors
            response.sendRedirect(request.getContextPath() + "/shop/add-fruit?error=" + e.getMessage());
        }
    }
}
