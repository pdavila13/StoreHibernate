/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storehibernate;

import controller.Controller;
import entities.Category;
import entities.Client;
import entities.Product;
import entities.Stock;
import model.ClassDAO;
import model.Model;
import views.View;

/**
 *
 * @author pdavila
 */
public class StoreHibernate {
    
    static Model model = new Model();
    static View view = new View();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Controller(view, model); 
    }
    
}
