/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glassfish.samples.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author arv
 */
@Embeddable
public class StocksPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "CURRENCY")
    private String currency;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME")
    private int time;

    public StocksPK() {
    }

    public StocksPK(String currency, int time) {
        this.currency = currency;
        this.time = time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (currency != null ? currency.hashCode() : 0);
        hash += (int) time;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StocksPK)) {
            return false;
        }
        StocksPK other = (StocksPK) object;
        if ((this.currency == null && other.currency != null) || (this.currency != null && !this.currency.equals(other.currency))) {
            return false;
        }
        if (this.time != other.time) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.glassfish.samples.model.StocksPK[ currency=" + currency + ", time=" + time + " ]";
    }
    
}
