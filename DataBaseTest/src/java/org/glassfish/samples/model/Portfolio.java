/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.samples.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arv
 */
@Entity
@Table(name = "PORTFOLIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Portfolio.findAll", query = "SELECT p FROM Portfolio p")
    , @NamedQuery(name = "Portfolio.findByEmail", query = "SELECT p FROM Portfolio p WHERE p.portfolioPK.email = :email")
    , @NamedQuery(name = "Portfolio.findByCurrency", query = "SELECT p FROM Portfolio p WHERE p.portfolioPK.currency = :currency")
    , @NamedQuery(name = "Portfolio.findByAmount", query = "SELECT p FROM Portfolio p WHERE p.amount = :amount")
    , @NamedQuery(name = "Portfolio.findByPricewhenbought", query = "SELECT p FROM Portfolio p WHERE p.pricewhenbought = :pricewhenbought")})
public class Portfolio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PortfolioPK portfolioPK;
    @Column(name = "AMOUNT")
    private Integer amount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRICEWHENBOUGHT")
    private Double pricewhenbought;

    public Portfolio() {
    }

    public Portfolio(PortfolioPK portfolioPK) {
        this.portfolioPK = portfolioPK;
    }

    public Portfolio(String email, String currency) {
        this.portfolioPK = new PortfolioPK(email, currency);
    }

    public PortfolioPK getPortfolioPK() {
        return portfolioPK;
    }

    public void setPortfolioPK(PortfolioPK portfolioPK) {
        this.portfolioPK = portfolioPK;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPricewhenbought() {
        return pricewhenbought;
    }

    public void setPricewhenbought(Double pricewhenbought) {
        this.pricewhenbought = pricewhenbought;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (portfolioPK != null ? portfolioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Portfolio)) {
            return false;
        }
        Portfolio other = (Portfolio) object;
        if ((this.portfolioPK == null && other.portfolioPK != null) || (this.portfolioPK != null && !this.portfolioPK.equals(other.portfolioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.glassfish.samples.model.Portfolio[ portfolioPK=" + portfolioPK + " ]";
    }
    
}
