/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storehibernate;

import entities.Category;
import entities.Client;
import entities.Product;
import entities.Stock;
import java.util.ArrayList;
import java.util.List;
import model.ClassDAO;

/**
 *
 * @author pdavila
 */
public class StoreHibernateTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ClassDAO ClassProductDAO = new ClassDAO(Product.class);
        ClassDAO ClassCategoryDAO = new ClassDAO(Category.class);
        
        Product productRecovered = null;
     
        long destroy_product_id = 0;
        
        //Created
        Stock s1 = null;
        Stock s2 = null;
        Stock s3 = null;
        
        Category c1 = null;
        Category c2 = null;
        Category c3 = null;
        
        List<Client> cl1 = null;
        
        //Created
        Product p1 = new Product("Processador","INTEL","7500",153,s1,c1,cl1);
        Product p2 = new Product("Motherboard","ASUS","970",197,s2,c2,cl1);
        Product p3 = new Product("Hard disk","WD","ST96304826",76,s3,c3,cl1);
        
        //Save
        destroy_product_id = ClassProductDAO.store(p1);
        ClassProductDAO.store(p2);
        ClassProductDAO.store(p3);
        

        //Edit and update
        p2.set3_product_trademark("MSI");
        ClassProductDAO.update(p2);
               
        //Recovered
        productRecovered = (Product) ClassProductDAO.obtain(Integer.valueOf(String.valueOf(destroy_product_id)));
        System.out.println("Recuperem a " + productRecovered.get2_product_name());

        //Delete
        ClassProductDAO.destroy(productRecovered);    
        
        //Obstain list
        List<Product> listProducts = ClassProductDAO.obtainList();
        System.out.println("Hi ha " + listProducts.size() + " productes a la base de dades");
        
        for (Product p : listProducts) {
            System.out.println(" -> " + p.get2_product_name());
        }
        
        System.exit(0);
    }
}
