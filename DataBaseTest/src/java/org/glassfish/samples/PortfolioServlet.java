/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.samples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;
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
import org.glassfish.samples.model.Portfolio;
import org.glassfish.samples.model.PortfolioEJB;
import org.glassfish.samples.model.Stocks;
import org.glassfish.samples.model.StocksPK;
import org.glassfish.samples.model.TraderEJB;

/**
 *
 * @author fredrik
 */
@WebServlet(name = "PortfolioServlet", urlPatterns = {"/PortfolioServlet"})
public class PortfolioServlet extends HttpServlet {

    @PersistenceUnit
    EntityManagerFactory emf;

    @EJB
    PortfolioEJB ejb;
    @EJB
    TraderEJB tEjb;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String message = "";
        System.out.println("before get stocks");
        HttpSession session = request.getSession();
        System.out.println("sess ID = " + session.getId());

        System.out.println("Mail online = " + session.getAttribute("mailOn"));

        List currency = ejb.findWithEmail((String)session.getAttribute("mailOn"));
        System.out.println("after get stocks");
        
        if(session.getAttribute("mailOn") != null){
                 request.setAttribute("SignIn", "Sign Out");
        }else{
              request.setAttribute("SignIn", "Sign in");
        }
   
        

        ArrayList<Double> values = new ArrayList<Double>();
        ArrayList<String> lables = new ArrayList<String>();

        int totalChange = 0;
        int change = 0;

        for (int i = 0; i < currency.size(); i++) {
            Portfolio p = ((Portfolio) currency.get(i));

            for (int x = 0; x < tEjb.getLatestPrice(p.getPortfolioPK().getCurrency()).size(); x++) {
                if (values.size() > x) {
                    values.set(x, values.get(x) + ((Stocks) tEjb.getLatestPrice(p.getPortfolioPK().getCurrency()).get(x)).getValue() * p.getAmount());
                } else {
                    values.add((double) (((Stocks) tEjb.getLatestPrice(p.getPortfolioPK().getCurrency()).get(x)).getValue() * p.getAmount()));
                }
            }
            change = (int) (((Stocks) tEjb.getLatestPrice(p.getPortfolioPK().getCurrency()).get(0)).getValue() - p.getPricewhenbought());
            totalChange += change * p.getAmount();
            message += createModule(p.getPortfolioPK().getCurrency(), ((Stocks) tEjb.getLatestPrice(p.getPortfolioPK().getCurrency()).get(0)).getValue(), p.getAmount(), change, false);
            //message += createModule(p.getCurrency(), 20, p.getAmount(), 100);
        }

        /*values.add(50.0);
        values.add(25.0);
        values.add(33.0);
        values.add(75.0);*/
        if(values.size() > 0){
            message += createModule("All", values.get(0).intValue(), 0, totalChange, true);
        }

        Date d = new java.util.Date();
        for (int x = 0; x < 24 * 7; x++) {
            d.setTime(System.currentTimeMillis() - 3600 * x * 1000);
            String[] split = d.toString().split(" ");
            String out = split[1] + " " + split[2] + " " + split[3];
            lables.add(out);
        }

        String[] temp = createGraph(values, lables);
        request.setAttribute("message", message); // This will be available as ${message}
        request.setAttribute("labels", temp[0]); // This will be available as ${message}
        request.setAttribute("data", temp[1]); // This will be available as ${message}
        request.getRequestDispatcher("/WEB-INF/PortfolioJsp.jsp").forward(request, response);

        /*String email = request.getParameter("email");
        /*String mail = request.getParameter("mail");
        String pw = request.getParameter("pw");
        Users u = new Users(mail,pw);
        ejb.create(mail, pw);

        RequestDispatcher view = request.getRequestDispatcher("myportfolio.html");
        view.forward(request, response);
        System.out.println("Post");
        System.out.println(ejb.findWithEmail(email).size());*/
    }

    protected String createModule(String currency, double value, int ownedAmount, int change, boolean isLast) {
        //return "heJ";
        String colour = "text-success";
        if (change < 0) {
            colour = "text-danger";
        }

        if (isLast) {
            return "<tr class=" + '"' + "" + '"' + ">"
                    + "<td>" + "" + "</td>"
                    + "<td class=" + '"' + "text-white" + '"' + ">" + "" + "</td>"
                    + "<td class=" + '"' + "text-white" + '"' + ">" + "" + "</td>"
                    + "<td class=" + '"' + "text-white" + '"' + ">" + "" + "</td>"
                    + "<th class=" + '"' + "" + '"' + ">" + "Total:" + "</td>"
                    + "<td>" + "$" + value + "</td>"
                    + "<td class=" + '"' + colour + '"' + ">" + "$" + change + "</td>"
                    + "<tr>";

        }

        return "<tr>"
                + "<td>" + currency + "</td>"
                + "<td>" + "$" + value + "</td>"
                + "<td class=" + '"' + colour + '"' + ">" + "$" + change + "</td>"
                + "<td class=" + '"' + colour + '"' + ">" + change / (value - change) * 100 + "%</td>"
                + "<td>" + ownedAmount + "</td>"
                + "<td>" + "$" + value * ownedAmount + "</td>"
                + "<td class=" + '"' + colour + '"' + ">" + "$" + change * ownedAmount + "</td>"
                + "<tr>";

    }

    protected String[] createGraph(ArrayList<Double> values, ArrayList<String> lables) {
        //return "heJ";

        String lableString = "labels: [";
        String valuesString = "data: [";
        for (int x = 0; x < values.size(); x++) {
            if (valuesString.equals("data: [")) {
                valuesString += values.get(values.size() - x - 1);
                lableString += '"' + lables.get(lables.size() - x - 1 - (lables.size() - values.size())) + '"';
            } else {
                valuesString += "," + values.get(values.size() - x - 1);
                lableString += "," + '"' + lables.get(lables.size() - x - 1 - (lables.size() - values.size())) + '"';
            }
        }
        valuesString += "]";
        lableString += "]";

        String[] temp = new String[2];

        temp[0] = lableString;
        temp[1] = valuesString;
        return temp;

    }
}
