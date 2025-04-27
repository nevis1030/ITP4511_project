/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.FruitBean;
import ict.db.ProjectDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author local_user
 */
@WebServlet(name="WarehouseStockAddServlet", urlPatterns={"/warehouse_stock_add"})
public class WarehouseStockAddServlet extends HttpServlet{
    ProjectDB db = ProjectDB.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String warehouseId = req.getParameter("warehouseId");
        String fruitId = req.getParameter("fruitId");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        db.addStockToWarehouse(warehouseId, fruitId, quantity);
        req.getRequestDispatcher("warehouse_stock?userId="+userId).forward(req, resp);
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<FruitBean> list = db.listAllFruits();
        req.setAttribute("list", list);
        req.getRequestDispatcher("/warehouse/stocklevel_add.jsp").forward(req, resp);
    }
    
    
}
