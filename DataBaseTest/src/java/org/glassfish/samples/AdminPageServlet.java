/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.samples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.samples.model.Portfolio;
import org.glassfish.samples.model.TraderEJB;
import org.glassfish.samples.model.Users;
import org.glassfish.samples.model.UsersEJB;

/**
 *
 * @author fredr
 */
@WebServlet(name = "AdminPageServlet", urlPatterns = {"/AdminPageServlet"})
public class AdminPageServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     @EJB UsersEJB userEjb;
     @EJB TraderEJB traderEjb;
    
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = "";
        System.out.println("before get stocks");
        List users = userEjb.getEmail();
        List stocks = traderEjb.getStocks();
        double totalBalance = 0;
        System.out.println("after get stocks");
        
        for(int x = 0; x < users.size(); x++){
            totalBalance += ((Users)users.get(x)).getBalance();
        }
        
        message = "<tr>"
                + "<td>" + users.size() + "</td>"
                + "<td>$" + (int)totalBalance + "</td>"
                + "<td>" + (int)(totalBalance/users.size()) + "</td>"
                + "<td>|</td>"
                + "<td>" + stocks.size() + "</td>"
                + "<tr>";
        
        request.setAttribute("message", message); // This will be available as ${message}
       
        request.getRequestDispatcher("/WEB-INF/AdminPageJsp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           
        String name = request.getParameter("name");
        double value = Double.parseDouble(request.getParameter("value"));
        
        //Users u = new Users(mail,pw);
        traderEjb.create(name, value);
        response.sendRedirect("AdminPageServlet");
        System.out.println(name);
        System.out.println(value);
    }
}
