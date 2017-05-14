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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="clients")  
public class Client {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_id")
    private int _1_client_id;
    
    @Column(name="client_official_id")
    private String _2_client_official_id;
    
    @Column(name="client_fullName")
    private String _3_client_fullName;
    
    @Column(name="client_email")
    private String _4_client_email;
    
    @Column(name="client_address")
    private String _5_client_address;
    
    @Column(name="client_telephoneNumber")
    private String _6_client_telephoneNumber;
    
    @ManyToMany(mappedBy="_8_sold", fetch = FetchType.EAGER)
    private List<Product> _7_buy = new ArrayList<>();

    public Client() {
        //
    }
    
    public Client(String _2_client_official_id, String _3_client_fullName, String _4_client_email, String _5_client_address, String _6_client_telephoneNumber) {
        this._2_client_official_id = _2_client_official_id;
        this._3_client_fullName = _3_client_fullName;
        this._4_client_email = _4_client_email;
        this._5_client_address = _5_client_address;
        this._6_client_telephoneNumber = _6_client_telephoneNumber;
    }
    
    public int get1_client_id() {
        return _1_client_id;
    }

    public void set1_client_id(int _1_client_id) {
        this._1_client_id = _1_client_id;
    }

    public String get2_client_official_id() {
        return _2_client_official_id;
    }

    public void set2_client_official_id(String _2_client_official_id) {
        this._2_client_official_id = _2_client_official_id;
    }

    public String get3_client_fullName() {
        return _3_client_fullName;
    }

    public void set3_client_fullName(String _3_client_fullName) {
        this._3_client_fullName = _3_client_fullName;
    }

    public String get4_client_email() {
        return _4_client_email;
    }

    public void set4_client_email(String _4_client_email) {
        this._4_client_email = _4_client_email;
    }

    public String get5_client_address() {
        return _5_client_address;
    }

    public void set5_client_address(String _5_client_address) {
        this._5_client_address = _5_client_address;
    }

    public String get6_client_telephoneNumber() {
        return _6_client_telephoneNumber;
    }

    public void set6_client_telephoneNumber(String _6_client_telephoneNumber) {
        this._6_client_telephoneNumber = _6_client_telephoneNumber;
    }
    
    public List<Product> get7_buy() {
        return _7_buy;
    }

    public void set7_buy(List<Product> _7_buy) {
        this._7_buy = _7_buy;
    }
    
    @Override
    public String toString() {
        return " " + _2_client_official_id;
    } 
}
