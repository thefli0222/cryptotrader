/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.samples;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Johannes
 */
@WebServlet(name = "SignOutServlet", urlPatterns = {"/SignOutServlet"})
public class SignOutServlet extends HttpServlet {

    int i = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("mailOn");
        session.invalidate();

        System.out.println("i =" + i);
        if (i < 1) {
            i++;
            response.setIntHeader("Refresh", 1);
            RequestDispatcher view = request.getRequestDispatcher("homepage.html");
            view.forward(request, response);

        } else {
            response.sendRedirect("homepage.html");
        }
    }
}
