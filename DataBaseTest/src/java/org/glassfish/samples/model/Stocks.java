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
@Table(name = "STOCKS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stocks.findAll", query = "SELECT s FROM Stocks s")
    , @NamedQuery(name = "Stocks.findByCurrency", query = "SELECT s FROM Stocks s WHERE s.stocksPK.currency = :currency")  
    , @NamedQuery(name = "Stocks.findByTime", query = "SELECT s FROM Stocks s WHERE s.stocksPK.time = :time")
    , @NamedQuery(name = "Stocks.findByValue", query = "SELECT s FROM Stocks s WHERE s.value = :value")})
public class Stocks implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected StocksPK stocksPK;
    @Column(name = "VALUE")
    private Integer value;

    public Stocks() {
    }

    public Stocks(StocksPK stocksPK) {
        this.stocksPK = stocksPK;
    }

    public Stocks(String currency, int time) {
        this.stocksPK = new StocksPK(currency, time);
    }

    public Stocks(String name, int i, double value) {
        this.value = (int)value;
        this.stocksPK = new StocksPK(name, i);
    }

    public StocksPK getStocksPK() {
        return stocksPK;
    }

    public void setStocksPK(StocksPK stocksPK) {
        this.stocksPK = stocksPK;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stocksPK != null ? stocksPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stocks)) {
            return false;
        }
        Stocks other = (Stocks) object;
        if ((this.stocksPK == null && other.stocksPK != null) || (this.stocksPK != null && !this.stocksPK.equals(other.stocksPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.glassfish.samples.model.Stocks[ stocksPK=" + stocksPK + " ]";
    }

}
