/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;

/**
 *
 * @author pdavila
 */
public class Client {
    
    private int client_id;
    private String client_official_id;
    private String client_fullName;
    private String client_email;
    private String client_address;
    private String client_telephoneNumber;
    
    private ArrayList<Product> buy = new ArrayList<>();

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_official_id() {
        return client_official_id;
    }

    public void setClient_official_id(String client_official_id) {
        this.client_official_id = client_official_id;
    }

    public String getClient_fullName() {
        return client_fullName;
    }

    public void setClient_fullName(String client_fullName) {
        this.client_fullName = client_fullName;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public String getClient_telephoneNumber() {
        return client_telephoneNumber;
    }

    public void setClient_telephoneNumber(String client_telephoneNumber) {
        this.client_telephoneNumber = client_telephoneNumber;
    }

    public ArrayList<Product> getBuy() {
        return buy;
    }

    public void setBuy(ArrayList<Product> buy) {
        this.buy = buy;
    }
}
