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
    
    private int _1_product_id;
    private String _2_product_name;
    private String _3_product_trademark;
    private String _4_product_model;
    private double _5_prodcut_price;
    
    private Stock _6_stored;
    private List<Category> _7_belongs = new ArrayList<>();
    private ArrayList<Client> _8_sold =  new ArrayList<>();

    public Product() {
        //
    }
    
    public Product(String _2_product_name, String _3_product_trademark, String _4_product_model, double _5_prodcut_price) {
        this._2_product_name = _2_product_name;
        this._3_product_trademark = _3_product_trademark;
        this._4_product_model = _4_product_model;
        this._5_prodcut_price = _5_prodcut_price;
    }

    public int get1_product_id() {
        return _1_product_id;
    }

    public void set1_product_id(int _1_product_id) {
        this._1_product_id = _1_product_id;
    }

    public String get2_product_name() {
        return _2_product_name;
    }

    public void set2_product_name(String _2_product_name) {
        this._2_product_name = _2_product_name;
    }

    public String get3_product_trademark() {
        return _3_product_trademark;
    }

    public void set3_product_trademark(String _3_product_trademark) {
        this._3_product_trademark = _3_product_trademark;
    }

    public String get4_product_model() {
        return _4_product_model;
    }

    public void set4_product_model(String _4_product_model) {
        this._4_product_model = _4_product_model;
    }

    public double get5_prodcut_price() {
        return _5_prodcut_price;
    }

    public void set5_prodcut_price(double _5_prodcut_price) {
        this._5_prodcut_price = _5_prodcut_price;
    }

    public Stock get6_stored() {
        return _6_stored;
    }

    public void set6_stored(Stock _6_stored) {
        this._6_stored = _6_stored;
    }

    public List<Category> get7_belongs() {
        return _7_belongs;
    }

    public void set7_belongs(List<Category> _7_belongs) {
        this._7_belongs = _7_belongs;
    }

    public ArrayList<Client> get8_sold() {
        return _8_sold;
    }

    public void set8_sold(ArrayList<Client> _8_sold) {
        this._8_sold = _8_sold;
    }
}
