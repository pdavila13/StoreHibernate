/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author pdavila
 */
@Entity
@Table(name="stocks")   
public class Stock {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="stock_id")
    private int _1_stock_id;
    
    @Column(name="stock_total")
    private int _2_stock_total;
    
    public Stock() {
        //
    }
    
    public Stock(int _2_stock_total) {
        this._2_stock_total = _2_stock_total;
    }
    
    public int get1_stock_id() {
        return _1_stock_id;
    }

    public void set1_stock_id(int _1_stock_id) {
        this._1_stock_id = _1_stock_id;
    }

    public int get2_stock_total() {
        return _2_stock_total;
    }

    public void set2_stock_total(int _2_stock_total) {
        this._2_stock_total = _2_stock_total;
    }
    
    @Override
    public String toString() {
        return " " + _2_stock_total;
    }
}
