/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.samples;

import java.io.IOException;
import javax.ejb.EJB;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.glassfish.samples.model.UsersEJB;

/**
 *
 * @author Johannes
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @PersistenceUnit
    EntityManagerFactory emf;

    @EJB
    UsersEJB ejb;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = null;
        String mail = request.getParameter("mail");
        String password = request.getParameter("pw");
        
        System.out.println();

        if (!ejb.tryLogin(mail, password).isEmpty()) {
            session = request.getSession();
            session.setAttribute("mailOn", mail);
               
            response.sendRedirect("http://localhost:8080/StockTrader-test/PortfolioServlet");


        } else {
            RequestDispatcher view = request.getRequestDispatcher("signinFailed.html");
            view.forward(request, response);
        }

        //System.out.println("mail: " + mail + " password: " + password + "session ID = "+session.getId());

    }
}
