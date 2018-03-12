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
 * @author Johannes
 */
@Stateless
public class BugsEJB {

    @PersistenceContext
    EntityManager em;

   public List getEmail() {
        return em.createNamedQuery("Bugs.findAll").getResultList();
    }
   
    public void create(String m, String message){
         Bugs b = new Bugs(m, message);
         em.persist(b);
    } 
}
