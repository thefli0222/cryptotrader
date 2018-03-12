/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.samples.model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author frelinde
 */
@Stateless
public class PortfolioEJB {

    @PersistenceContext
    EntityManager em;

    public void create(String email, String currency, int amount, double pWB) {
        Portfolio p = new Portfolio(email, currency);
        p.setAmount(amount);
        p.setPricewhenbought(pWB);
        /*if (tryNewCurrency(email, currency).size() > 0) {

        } else {
            //Portfolio p = new Portfolio(email, currency, amount, pWB);
        }*/
        em.persist(p);
    }

    public List findWithEmail(String email) { 
        return em.createQuery("SELECT c FROM Portfolio c WHERE c.portfolioPK.email LIKE :email").setParameter("email", email)
                .getResultList();
    }

    public List tryNewCurrency(String email, String currency) {
        return em.createQuery("SELECT c FROM Portfolio c WHERE c.email LIKE :email AND c.currency = :currency")
                .setParameter("email", email)
                .setParameter("currency", currency)
                .setMaxResults(1)
                .getResultList();
    }
    
    public int getMaxFromCurrency(String currency) {
         
        //System.out.println((int)em.createQuery("SELECT value FROM Stocks WHERE currency LIKE 'BTC' ORDER by time DESC").getResultList().get(0));
        System.out.println("hej");
        return 1;
        
        //return 1;
    }
}
