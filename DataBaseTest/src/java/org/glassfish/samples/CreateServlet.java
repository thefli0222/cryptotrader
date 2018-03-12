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
import org.glassfish.samples.model.Users;
import org.glassfish.samples.model.UsersEJB;


/**
 *
 * @author Johannes
 */
@WebServlet(name = "CreateServlet", urlPatterns = {"/CreateServlet"})
public class CreateServlet extends HttpServlet {

    @PersistenceUnit
    EntityManagerFactory emf;
    
    @EJB UsersEJB ejb;

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           
        String mail = request.getParameter("mail");
        String pw = request.getParameter("pw");
        
        Users u = new Users(mail,pw);
        ejb.create(mail, pw);
  
        RequestDispatcher view = request.getRequestDispatcher("signin.html");
        view.forward(request, response);
            
        System.out.println(mail);
        System.out.println(pw);
    }
}
