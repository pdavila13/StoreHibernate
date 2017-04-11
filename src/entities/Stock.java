/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author pdavila
 */
public class Stock {
    
    private int stock_id;
    private int stock_total;

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public int getStock_total() {
        return stock_total;
    }

    public void setStock_total(int stock_total) {
        this.stock_total = stock_total;
    }
}
