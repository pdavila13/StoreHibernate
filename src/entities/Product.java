/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pdavila
 */
public class Product {
    
    private int product_id;
    private String product_name;
    private String product_trademark;
    private String product_model;
    private double prodcut_price;
    
    private Stock stored;
    private List<Category> belongs = new ArrayList<>();
    private ArrayList<Client> sold =  new ArrayList<>();

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_trademark() {
        return product_trademark;
    }

    public void setProduct_trademark(String product_trademark) {
        this.product_trademark = product_trademark;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    public double getProdcut_price() {
        return prodcut_price;
    }

    public void setProdcut_price(double prodcut_price) {
        this.prodcut_price = prodcut_price;
    }

    public Stock getStored() {
        return stored;
    }

    public void setStored(Stock stored) {
        this.stored = stored;
    }

    public List<Category> getBelongs() {
        return belongs;
    }

    public void setBelongs(List<Category> belongs) {
        this.belongs = belongs;
    }

    public ArrayList<Client> getSold() {
        return sold;
    }

    public void setSold(ArrayList<Client> sold) {
        this.sold = sold;
    }  
}
