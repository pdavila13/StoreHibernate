/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entities.Category;
import entities.Client;
import entities.Product;
import entities.Stock;
import org.hibernate.Session;
import utils.HibernateUtil;

/**
 *
 * @author pdavila
 */
public class Model {
    private static final Session session = HibernateUtil.getSessionFactory().openSession();
    
    private Product product;
    private Stock stock;
    private Category category;
    private Client client;
    
    private ClassDAO<Product> productClassDAO = new ClassDAO<>(Product.class, session);
    private ClassDAO<Stock> stockClassDAO = new ClassDAO<>(Stock.class, session);
    private ClassDAO<Category> categoryClassDAO = new ClassDAO<>(Category.class, session);
    private ClassDAO<Client> clientClassDAO = new ClassDAO<>(Client.class, session);

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClassDAO<Product> getProductClassDAO() {
        return productClassDAO;
    }

    public void setProductClassDAO(ClassDAO<Product> productClassDAO) {
        this.productClassDAO = productClassDAO;
    }

    public ClassDAO<Stock> getStockClassDAO() {
        return stockClassDAO;
    }

    public void setStockClassDAO(ClassDAO<Stock> stockClassDAO) {
        this.stockClassDAO = stockClassDAO;
    }

    public ClassDAO<Category> getCategoryClassDAO() {
        return categoryClassDAO;
    }

    public void setCategoryClassDAO(ClassDAO<Category> categoryClassDAO) {
        this.categoryClassDAO = categoryClassDAO;
    }

    public ClassDAO<Client> getClientClassDAO() {
        return clientClassDAO;
    }

    public void setClientClassDAO(ClassDAO<Client> clientClassDAO) {
        this.clientClassDAO = clientClassDAO;
    }
    
    public void closeSession() {
        session.close();
    }
}
