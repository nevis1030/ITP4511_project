/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ict.servlet;

import ict.bean.BorrowBean;
import ict.bean.UserBean;
import ict.db.ProjectDB;
import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet to handle approving/denying borrow requests
 * 
 * @author local_user
 */
@WebServlet(name = "BorrowEditServlet", urlPatterns = {"/shop/borrow/edit"})
public class BorrowEditServlet extends BaseServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     * Updates borrow status based on form submission
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Use BaseServlet's helper method to check if user is logged in and is a shop staff
        checkShopStaff(request, response);
        
        HttpSession session = request.getSession(false);
        UserBean user = (UserBean) session.getAttribute("user");
        String userShopId = user.getShopId();
        
        // Get form parameters
        String borrowId = request.getParameter("borrowId");
        String action = request.getParameter("action");
        
        if (borrowId == null || borrowId.isEmpty() || action == null || action.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-record&error=Invalid request parameters");
            return;
        }
        
        try {
            ProjectDB db = ProjectDB.getInstance();
            
            // Get the borrow request to verify destination shop
            ArrayList<BorrowBean> borrows = db.listAllBorrow();
            BorrowBean targetBorrow = null;
            
            System.out.println("Looking for borrow ID: " + borrowId + " in " + (borrows != null ? borrows.size() : "null") + " records");
            
            for (BorrowBean borrow : borrows) {
                if (borrow.getBorrowId().equals(borrowId)) {
                    targetBorrow = borrow;
                    System.out.println("Found matching borrow record: " + borrow);
                    break;
                }
            }
            
            if (targetBorrow == null) {
                response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-record&error=Borrow request not found");
                return;
            }
            
            // Security check - ensure user is only approving/denying requests for their shop
            if (!userShopId.equals(targetBorrow.getToShop())) {
                // Redirect with error message if trying to approve/deny another shop's request
                response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-record&error=Unauthorized: You can only approve/deny requests for your shop");
                return;
            }
            
            // Process the action
            if ("approve".equals(action)) {
                db.approveBorrow(borrowId);
                response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-record&success=Borrow request approved successfully");
            } else if ("deny".equals(action)) {
                db.denyBorrow(borrowId);
                response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-record&success=Borrow request denied successfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-record&error=Invalid action");
            }
        } catch (Exception e) {
            // Handle errors
            response.sendRedirect(request.getContextPath() + "/shop/borrow?subtab=borrow-record&error=" + e.getMessage());
        }
    }
}
