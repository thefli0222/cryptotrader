/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.samples;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
import org.glassfish.samples.model.Stocks;
import org.glassfish.samples.model.StocksPK;

import org.glassfish.samples.model.TraderEJB;
import org.glassfish.samples.model.Users;

/**
 *
 * @author arv
 */
@WebServlet(name = "TraderServlet", urlPatterns = {"/TraderServlet"})
public class TraderServlet extends HttpServlet {

    @EJB
    TraderEJB ejb;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        if (session.getAttribute("mailOn") != null) {
            request.setAttribute("SignIn", "Sign Out");
        } else {
            request.setAttribute("SignIn", "Sign in");
        }
        
        String graphs = "";
        
        if(ejb.getStocks().size() < 1){
            String[] s = {"Bitcoin", "Ethereum", "Ripple", "DogeCoin", "Dat076", "CryptoCoin"};
            Random r = new Random();
            for(int x=0; x < 6; x++){
                    int Low = r.nextInt(1000);
                    int High = (int)(Low*r.nextFloat())+Low;
               for(int y=0; y < 24; y++) {
                    r = new Random();
                    double Result = r.nextInt(High-Low) + Low;
                    ejb.create(s[x], Result, y);
               }
            }
        }
        String lastName = "";
        for(int x = 0; x < ejb.getStocks().size(); x++){
            if(((Stocks)ejb.getStocks().get(x)).getStocksPK().getCurrency().equals(lastName)){
                
            } else {
                lastName = ((Stocks)ejb.getStocks().get(x)).getStocksPK().getCurrency();
                graphs += createGraph(((Stocks)ejb.getStocks().get(x)).getStocksPK().getCurrency());
            }
        }
            
        request.setAttribute("message", addStocks("No"));
        request.setAttribute("graphs", graphs); 
        long timeStart = System.currentTimeMillis();
        request.getRequestDispatcher("/WEB-INF/TradingJsp.jsp").forward(request, response);
        System.out.println((System.currentTimeMillis() - timeStart));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println("sess ID = " + session.getId());

        System.out.println("Mail online = " + session.getAttribute("mailOn"));

        String searched = request.getParameter("searchVal");
        
        String graphs = "";
        
        String lastName = "";
        for(int x = 0; x < ejb.getStocks().size(); x++){
            if(((Stocks)ejb.getStocks().get(x)).getStocksPK().getCurrency().equals(lastName)){
                
            } else {
                lastName = ((Stocks)ejb.getStocks().get(x)).getStocksPK().getCurrency();
                graphs += createGraph(((Stocks)ejb.getStocks().get(x)).getStocksPK().getCurrency());
            }
        }
        
        System.out.println("Hej" + graphs);
        
        request.setAttribute("message", addStocks(searched));
        request.setAttribute("graphs", graphs);
        request.getRequestDispatcher("/WEB-INF/TradingJsp.jsp").forward(request, response);

    }

    protected String createModule(String currency, int value) {
        return "<div class=\"col-md-4\">" + "\n"
                + "<div class=\"card mb-4 box-shadow\">" + "\n"
                + "<div class=\"cardcss\">" + "\n"
                + "<p class=\"buystocks\">" + currency + "</p>" + "\n"
                + "<canvas class=\"my-4 chartjs-render-monitorn\" id=\"" + currency + "\" width=\"800\" height=\"400\" style=\"display: block; height: 800px; width: 400px;\"></canvas>"
                + "<div class=\"d-flex justify-content-between align-items-center\">" + "\n"
                + "<form class=\"trade-field\"  method=\"post\" action=\"TraderActionServlet\" >" + "\n"
                + "<label for=\"inputEmail\" class=\"sr-only\">Amount</label>" + "\n"
                + "<div class=\"row, centre\">" + "\n"
                + "<div class=\"btn-group\">" + "\n"
                + "<input name=\"amount\" type=\"number\" min=\"0\" id=\"inputEmail\" class=\"form-control, col-md-6\" placeholder=\"Amount\" required=\"\" autofocus=\"\">" + "\n"
                + "<input type=\"hidden\" name=\"currency\" value=\"" + currency + "\"/>" + "\n"
                + "<input class=\"btn btn-sm btn-outline-secondary\" type=\"submit\" name=\"buy\" value=\"BUY\">" + "\n"
                + "<input class=\"btn btn-sm btn-outline-secondary\" type=\"submit\" name=\"sell\" value=\"SELL\">" + "\n"
                + "</div>" + "\n"
                + "</div>" + "\n"
                + "</form>" + "\n"
                + "<small class=\"text-muted\">" + "$" + value + "</small>" + "\n"
                + "</div>" + "\n"
                + "</div>" + "\n"
                + "</div>" + "\n"
                + "</div>" + "\n";
    }

    protected String createGraph(String currency) {
        
        ArrayList<Double> values = new ArrayList<Double>();
        ArrayList<String> lables = new ArrayList<String>();
        
        List<Stocks> stocks = ejb.getLatestPrice(currency);
        
        for (int x = 0; x < stocks.size() && x<10; x++) {
                if (values.size() > x) {
                    values.set(x, values.get(x) + ((Stocks) stocks.get(x)).getValue());
                } else {
                    values.add((double) (((Stocks) stocks.get(x)).getValue()));
                }
            }
        
        for (int x = 0; x < 24; x++) {
            lables.add("");
        }
        
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
        
        String colour = "#007bff";
        
        if(values.size() >  1){
            if(values.get(0) < values.get(values.size()-1)){
                colour = "#d81a20";
            } else {
                colour = "#1cdb58";
            }
        }

        return "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js\"></script>"
                + "<script>"
                    + "var ctx = document.getElementById(\"" + currency + "\").getContext('2d');"
                    + "var " + currency + " = new Chart(ctx, {"
                        + "type: 'line',"
                        + "data: {"
                        + temp[0] + ","
                            + "datasets: [{"
                            + temp[1] + ","
                            + "lineTension: 0,"
                            + "backgroundColor: 'transparent',"
                            + "borderColor: '" + colour + "',"
                            + "borderWidth: 4,"
                            + "pointBackgroundColor: '" + colour + "'"
                        + "}]"
                    + "},"
                    + "options: {"
                        +"animation: {"
                                        +"duration: 0"
                                    +"},"
                        + "scales: {"
                            + "yAxes: [{"
                                + "ticks: {"
                                + "beginAtZero: false"
                                + "}"
                            + "}]"
                        + "},"
                        + "legend: {"
                            + "display: false,"
                        + "}"
                    + "}"
                + "});"
                + "</script>";
    }

    protected String addStocks(String stock) {
        boolean searchTerms = false;
        if (!stock.equals("No")) {
            searchTerms = true;
        }

        String message = "";
        System.out.println("before get stocks");
        List stocks = ejb.getStocks();
        System.out.println("after get stocks");
        //needed to add modules
        message = "<div class=\"row\">";

        List<String> visitedStocks = new ArrayList<String>();

        for (int i = 0; i < stocks.size(); i++) {
            //get currency
            Stocks s = ((Stocks) stocks.get(i));
            StocksPK pk = s.getStocksPK();
            String currency = pk.getCurrency();

            //get price
            List stockPrices = ejb.getLatestPrice(currency);
            Stocks sp = ((Stocks) stockPrices.get(0));
            int price = sp.getValue();

            if (!visitedStocks.contains(currency) && !searchTerms) {
                message = message + createModule(currency, price);
            } else if (!visitedStocks.contains(currency) && stock.equals(currency) && searchTerms) {
                message = message + createModule(currency, price);
            }
            visitedStocks.add(currency);
        }
        return message;
    }

}
