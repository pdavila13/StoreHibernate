/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author pdavila
 */
@Entity
@Table(name="categories")  
public class Category {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private int _1_category_id;
    
    @Column(name="category_name")
    private String _2_category_name;
    
    @OneToMany(mappedBy="_7_belongs", fetch = FetchType.EAGER)
    private List<Product> _3_category_product = new ArrayList<>();

    public Category() {
        //
    }

    public Category(String _2_category_name) {
        this._2_category_name = _2_category_name;
    }
    
    public int get1_category_id() {
        return _1_category_id;
    }

    public void set1_category_id(int _1_category_id) {
        this._1_category_id = _1_category_id;
    }

    public String get2_category_name() {
        return _2_category_name;
    }

    public void set2_category_name(String _2_category_name) {
        this._2_category_name = _2_category_name;
    }
    
    public List<Product> get3_category_product() {
        return _3_category_product;
    }

    public void set3_category_product(List<Product> _3_category_product) {
        this._3_category_product = _3_category_product;
    }

    @Override
    public String toString() {
        return _2_category_name;
    }
}
