/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

import ict.bean.ConsumptionBean;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import ict.db.ProjectDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author local_user
 */
@WebServlet(name="SeniorConsumptionServlet", urlPatterns={"/consumption"})
public class SeniorConsumptionServlet extends HttpServlet{
    ProjectDB db = ProjectDB.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<ConsumptionBean> consumptions = db.listAllConsumption();
        req.setAttribute("consumptions", consumptions);
        req.getRequestDispatcher("/senior/consumption.jsp").forward(req, resp);
    }       
}
