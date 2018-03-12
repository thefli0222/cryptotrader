/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.samples;

import java.io.IOException;

import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.glassfish.samples.model.Portfolio;
import org.glassfish.samples.model.PortfolioEJB;



import org.glassfish.samples.model.TraderEJB;


/**
 *
 * @author arv
 */
@WebServlet(name = "TraderActionServlet", urlPatterns = {"/TraderActionServlet"})
public class TraderActionServlet extends HttpServlet {

    @EJB
    TraderEJB ejb;

    @EJB
    PortfolioEJB portEjb;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String amount = request.getParameter("amount");
        String buy = request.getParameter("buy");
        String sell = request.getParameter("sell");
        
        String currency = request.getParameter("currency");
        
        boolean isBuying = false;
        boolean isSelling = false;
        
        if(buy != null)
        {
            isBuying = buy.equals("BUY");
        }
        
        if(sell != null)
        {
            isSelling = sell.equals("SELL");
        }
        
        System.out.println("buy: " + isBuying);
        System.out.println("sell: " + isSelling);
        System.out.println("currency: " + currency);
        System.out.println("Amount: " + amount);
        
        
        String action = isBuying? "buying": (isSelling? "selling": "failed");
        
        String responseString = "Done " + action + " " + amount + " " + currency;
        HttpSession session = request.getSession();
        
        String user = (String)session.getAttribute("mailOn");
        
        System.out.print("Logged in user : "+(String)session.getAttribute("mailOn"));
        // Portfolio p = (Portfolio) portEjb.findWithEmail((String)session.getAttribute("mailOn")).get(0);
       
        
        // System.out.print(p.toString());
        
        String responseMessage = "failed";
        
        if(isBuying)
        {
            responseMessage = ejb.buyStock( currency, Integer.parseInt(amount), user, this);
        }
        else if (isSelling)
        {
            responseMessage = ejb.sellStock( currency, Integer.parseInt(amount), user, this);
        }
          
        
        request.setAttribute("message", responseMessage);
        request.getRequestDispatcher("/WEB-INF/TradingJsp.jsp").forward(request, response);
    }
    
    public void addPortfolio(String user, String currency, int amount, double priceOfCurrency)
    {
        portEjb.create(user, currency, amount, priceOfCurrency);
    }
}
