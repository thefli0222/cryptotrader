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
import org.glassfish.samples.model.UsersEJB;

/**
 *
 * @author Johannes
 */
@WebServlet(name = "LoginAdminServlet", urlPatterns = {"/LoginAdminServlet"})
public class LoginAdminServlet extends HttpServlet {

    @PersistenceUnit
    EntityManagerFactory emf;

    @EJB
    UsersEJB ejb;

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                
        String username = request.getParameter("username");
        String password = request.getParameter("pw");
        
        if (!ejb.tryAdminLogin(username, password).isEmpty()) {
            response.sendRedirect("AdminPageServlet");
        } else {
            RequestDispatcher view = request.getRequestDispatcher("admin.html");
            view.forward(request, response);
        }


        //System.out.println("mail: " + username + " password: " + password);

    }
}
