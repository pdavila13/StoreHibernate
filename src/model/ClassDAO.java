/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.HibernateUtil;

/**
 *
 * @author pdavila
 */
public class ClassDAO<T> {
    
    private Session session;
    private Transaction tx;
    
    private Class p;
    
    public ClassDAO() {
        //
    }
    
    public ClassDAO(Class<T> p) {
        this.p = p;
        this.session = session;
    }
    
    public long store(T object) throws HibernateException {
        long id = 0;
        
        try {
            initOperation();
            id = Long.parseLong(String.valueOf(session.save(object)));
            tx.commit();
        } catch (HibernateException he) {
            tryException(he);
            throw he;
        } finally {
            session.close();
        }
        return id;
    }
    
    public void update(T object) throws HibernateException {
        try {
            initOperation();
            session.update(object);
            tx.commit();
        } catch (HibernateException he) {
            tryException(he);
            throw he;
        } finally {
            session.close();
        }
    }
    
    public void destroy(T object) throws HibernateException {
        try {
            initOperation();
            session.delete(object);
            tx.commit();
        } catch (HibernateException he) {
            tryException(he);
            throw he;
        } finally {
            session.close();
        }
    }
    
    public T obtain(int idObject) throws HibernateException {
        T object = null;
        try {
            initOperation();
            object = (T) session.get(p, idObject);
        } finally {
            session.close();
        }
        return object;
    }
    
    public List<T> obtainList() throws HibernateException {
        ArrayList<T> list = null;
        try {
            initOperation();
            list = (ArrayList) session.createQuery("from " + p.getSimpleName()).list();
        } finally {
            session.close();
        }
        return list;
    }
    
    public void initOperation() throws HibernateException {
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
    }
    
    private void tryException(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Error a la capa d'accés a dades", he);
    }
}
