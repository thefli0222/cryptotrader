/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.samples.model;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import org.glassfish.samples.TraderActionServlet;

@Stateless
public class TraderEJB {

    @PersistenceContext
    EntityManager em;

    public List getStocks() {
        /*return em.createQuery("SELECT c FROM Stocks c  WHERE c.time LIKE :time AND c.value = :value")
            .setParameter("value", 1)
            .setParameter("time", 1)
            .setMaxResults(100)
            .getResultList();
        return em.createQuery("SELECT c FROM Users c WHERE c.email LIKE :email").setParameter("email", "asd@asd.se")
                .setMaxResults(100)
                .getResultList();*/
        return em.createQuery("SELECT c FROM Stocks c")
                .getResultList();

    }
    
    public void create(String name, double value){
         Stocks s = new Stocks(name, 1, value);
         em.persist(s);
    } 
    
    public void create(String name, double value, int index){
         Stocks s = new Stocks(name, index, value);
         em.persist(s);
    } 

    public List getLatestPrice(String currency)
    {
        return em.createQuery("SELECT c FROM Stocks c WHERE c.stocksPK.currency LIKE :currency ORDER BY c.stocksPK.time DESC")
            .setParameter("currency", currency)
            .getResultList();
    }
    
    public int getLatestPriceNumber(String currency)
    {
        //get price
        List stockPrices = getLatestPrice(currency);
        Stocks sp = ((Stocks) stockPrices.get(0));
        int price = sp.getValue();
        
        
        return price;
    }
    
    public double getBalance(String user)
    {
        List l = em.createQuery("SELECT u.balance FROM Users u WHERE u.email = :user")
            .setParameter("user", user)
            .getResultList();
        
        double balance = 0;
        
        if(!l.isEmpty())
        {
            balance = (double)l.get(0);
        }
        
        return balance;
    }
    
    private int setBalance(String user, double balance)
    {
        return em.createQuery("UPDATE Users SET balance = :balance WHERE email = :user ")
                .setParameter("balance", balance)
                .setParameter("user", user)
                .executeUpdate();
    }
    
    private boolean existsInPortfolio(String user, String currency)
    {
        List testList = em.createQuery("SELECT p FROM Portfolio p WHERE p.portfolioPK.email = :user AND p.portfolioPK.currency = :currency")
            .setParameter("user", user)
            .setParameter("currency", currency)
            .getResultList();
                    
        int count = testList.size();
        boolean doesExists = (count != 0);
        return doesExists;
    }
    
    private int getOwned(String user, String currency) {
        if(existsInPortfolio(user, currency))
        {
            List testList = em.createQuery("SELECT p FROM Portfolio p WHERE p.portfolioPK.email = :user AND p.portfolioPK.currency = :currency")
            .setParameter("user", user)
            .setParameter("currency", currency)
            .getResultList();
            
            Portfolio p = (Portfolio)testList.get(0); 
            int stockAmount = p.getAmount();
            return stockAmount;
        }
        else
        {
            return 0;
        }
        
    }

    private int updatePortfolio(String user, String currency, int amount, int priceOfCurrency)
    {
        return em.createQuery("UPDATE Portfolio p SET p.amount = p.amount + :amount WHERE p.portfolioPK.email = :user AND p.portfolioPK.currency = :currency")
            .setParameter("currency", currency)
            .setParameter("amount", amount)
            .setParameter("user", user)
            .executeUpdate();
    }
    
    
    public Portfolio update(Portfolio p){
        return em.merge(p);
    }
    
    public String buyStock( String currency, int amount, String user, TraderActionServlet tas)
    {   
        int priceOfCurrency = getLatestPriceNumber(currency);
        
        double balance = getBalance(user);
        
        double valueOfTransaction = priceOfCurrency * amount;

        if (valueOfTransaction <= balance)
        {
            //do transaction
            double newBalance = balance - valueOfTransaction;
            setBalance(user, newBalance);
            
            boolean currencyOwned = false;
            currencyOwned = existsInPortfolio(user, currency);
            
            
            if(!currencyOwned)
            {
                tas.addPortfolio(user, currency, amount, priceOfCurrency);                                       
            } 
            else
            {
               updatePortfolio(user, currency, amount, priceOfCurrency);
            }

            return "Bought " + amount + " " + currency + " for $" + priceOfCurrency + " each";
        }
        else 
        {
            //transaction failed
            return "Failed to buy, not enough money in account!";
        }
    }
    
    private int deleteFromPortfolio(String user, String currency) {
        return em.createQuery("DELETE FROM Portfolio p WHERE p.portfolioPK.email = :user AND p.portfolioPK.currency = :currency")
            .setParameter("currency", currency)
            .setParameter("user", user)
            .executeUpdate();
    }
    
    public String sellStock( String currency, int amount, String user, TraderActionServlet tas)
    {   
        int priceOfCurrency = getLatestPriceNumber(currency);
        
        double balance = getBalance(user);
        
        int amountOwned = getOwned(user, currency);
        
        double valueOfTransaction = priceOfCurrency * amount;

        if (amount < amountOwned)
        {
            //do transaction
            double newBalance = balance + valueOfTransaction;
            setBalance(user, newBalance);
            
            boolean currencyOwned = false;
            currencyOwned = existsInPortfolio(user, currency);
            
            
            if(!currencyOwned)
            {
                tas.addPortfolio(user, currency, amount, priceOfCurrency);                                       
            } 
            else
            {
               updatePortfolio(user, currency, -amount, priceOfCurrency);
            }

            return "Sold " + amount + " " + currency + " for $" + priceOfCurrency + " each";
        }
        //remove from database if user has 0 of something
        else if(amount == amountOwned)
        {
            //do transaction
            double newBalance = balance + valueOfTransaction;
            setBalance(user, newBalance);
            
            boolean currencyOwned = false;
            currencyOwned = existsInPortfolio(user, currency);
            
            
            if(!currencyOwned)
            {
                tas.addPortfolio(user, currency, amount, priceOfCurrency);                                       
            } 
            else
            {
               updatePortfolio(user, currency, -amount, priceOfCurrency);
            }
            
            deleteFromPortfolio(user, currency);

            return "Sold " + amount + " " + currency + " for $" + priceOfCurrency + " each";
        }
        else 
        {
            //transaction failed
            return "You can't sell that much!";
        }
    }
}
