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
public class UsersEJB {

    @PersistenceContext
    EntityManager em;

   public List getEmail() {
        return em.createNamedQuery("Users.findAll").getResultList();
    }
   
    public void create(String m, String pw){
         Users u = new Users(m, pw);
         em.persist(u);
    } 
    
    public List findWithName(String name) {
        return em.createQuery("SELECT c FROM Users c WHERE c.email LIKE :email").setParameter("email", name)
                .setMaxResults(1)
                .getResultList();
    }
      
      public List getPW(String name) {
        return em.createQuery("SELECT c.pw FROM Users c WHERE c.email LIKE :email").setParameter("email", name)
                .setMaxResults(1)
                .getResultList();
      }

    public List tryLogin(String name, String password)
    {
        return em.createQuery("SELECT c FROM Users c WHERE c.email LIKE :email AND c.pw = :pw")
              .setParameter("email", name)
              .setParameter("pw", password)
              .setMaxResults(1)
              .getResultList();
    }
    
    public List tryAdminLogin(String name, String password)
    {
        return em.createQuery("SELECT c FROM Admins c WHERE c.username LIKE :username AND c.pw = :pw")
              .setParameter("username", name)
              .setParameter("pw", password)
              .setMaxResults(1)
              .getResultList();
    }
}
