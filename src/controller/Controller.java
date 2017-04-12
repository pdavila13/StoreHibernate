/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Product;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import model.ClassDAO;
import views.View;

/**
 *
 * @author pdavila
 */
public class Controller {
    
    private View view;
    private ClassDAO<Product> modelProduct;
    private TableColumn loadTableProduct;
    private int filasel = -1;

    public Controller(View view, ClassDAO<Product> modelProduct) {
        this.view = view;
        this.modelProduct = modelProduct;
        
        loadTableProduct = loadTable((ArrayList) modelProduct.obtainList(), view.getProductTable(), Product.class);
        controlProduct();
        exitButton();
    }
    
    private static TableColumn loadTable(ArrayList resultSet, JTable table, Class<?> classe) {
        
        //variables locals
        Vector columnNames = new Vector();
        Vector data = new Vector();
        
        //Per poder actualitzar la BD des de la taula usaríem el model comentat
        //ModelCanvisBD model;
        DefaultTableModel model;
        
        //Anotem el nº de camps de la classe
        Field[] camps = classe.getDeclaredFields();
        
        //Ordenem els camps alfabèticament
        Arrays.sort(camps, new OrdenarCampClasseAlfabeticament());
        int ncamps = camps.length;
        
        //Recorrem els camps de la classe i posem els seus noms com a columnes de la taula
        //Com hem hagut de posar _numero_ davant el nom dels camps, mostrem el nom a partir de la 4ª lletra 
        for (Field f : camps) {
            columnNames.addElement(f.getName().substring(3));
        }
        //Afegixo al model de la taula una columna on guardaré l'objecte mostrat a cada fila (amago la columna al final per a que no aparegue a la vista)
        columnNames.addElement("objecte");
        
        //Si hi ha algun element a l'arraylist omplim la taula
        if (resultSet.size() != 0) {

            //Guardem els descriptors de mètode que ens interessen (els getters), més una columna per guardar l'objecte sencer
            Vector<Method> methods = new Vector(ncamps + 1);
            try {

                PropertyDescriptor[] descriptors = Introspector.getBeanInfo(classe).getPropertyDescriptors();
                Arrays.sort(descriptors, new OrdenarMetodeClasseAlfabeticament());
                for (PropertyDescriptor pD : descriptors) {
                    Method m = pD.getReadMethod();
                    if (m != null & !m.getName().equals("getClass")) {
                        methods.addElement(m);
                    }
                }

            } catch (IntrospectionException ex) {
                //Logger.getLogger(VistaActors.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (Object m : resultSet) {
                Vector row = new Vector(ncamps + 1);

                for (Method mD : methods) {
                    try {
                        row.addElement(mD.invoke(m));
                    } catch (IllegalAccessException ex) {
                        //Logger.getLogger(VistaActors.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        //Logger.getLogger(VistaActors.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        //Logger.getLogger(VistaActors.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                //Aquí guardo l'objecte sencer a la darrera columna
                row.addElement(m);
                
                //Finalment afegixo la fila a les dades
                data.addElement(row);
            }
        }

        //Utilitzem el model que permet actualitzar la BD des de la taula
        //model = new ModelCanvisBD(data, columnNames, Model.getConnexio(), columnNames.size() - 1);
        model=new DefaultTableModel(data, columnNames);
        table.setModel(model);

        //Borro la darrera columna per a que no aparegue a la vista, però abans la guardo en una variable que al final serà el que retorna el mètode
        TableColumnModel tcm = table.getColumnModel();
        TableColumn columna=tcm.getColumn(tcm.getColumnCount() - 1);
        tcm.removeColumn(columna);

        //Fixo l'amplada de les columnes que sí es mostren
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setMaxWidth(250);
        }
        
        return columna;
    }
    
    private void controlProduct() {
        view.getCreateProductButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(view.getCreateProductButton())) {
                    if(!view.getProductIdTextField().getText().trim().equals("") || 
                       !view.getProductNameTextField().getText().trim().equals("") || 
                       !view.getProductTraceMarkTextField().getText().trim().equals("") || 
                       !view.getProductModelTextField().getText().trim().equals("") || 
                       !view.getProductPriceTextField().getText().trim().equals(""))
                    modelProduct.obtainList();
                    
                    Product p = new Product(
                            view.getProductNameTextField().getText(),
                            view.getProductTraceMarkTextField().getText(),
                            view.getProductModelTextField().getText(),
                            Integer.valueOf(view.getProductPriceTextField().getText()));
                    modelProduct.store(p);
                    loadTable((ArrayList) modelProduct.obtainList(),view.getProductTable(),Product.class);
                } else {
                    JOptionPane.showMessageDialog(null, "No has introducido ningun producto", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
                
                view.getProductIdTextField().setText("");
                view.getProductNameTextField().setText("");
                view.getProductTraceMarkTextField().setText("");
                view.getProductModelTextField().setText("");
                view.getProductPriceTextField().setText("");
            }
        });
        
        view.getModifyProductButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableColumnModel tcm = (TableColumnModel) view.getProductTable().getColumnModel();
                if (view.getProductTable().getSelectedRow() != -1) {
                    view.getProductTable().addColumn(loadTableProduct);
                    DefaultTableModel tm = (DefaultTableModel) view.getProductTable().getModel();
                    Product modifyProduct = (Product) tm.getValueAt(view.getProductTable().getSelectedRow(), tm.getColumnCount() -1);
                    modifyProduct.set2_product_name(view.getProductNameTextField().getText());
                    modifyProduct.set3_product_trademark(view.getProductTraceMarkTextField().getText());
                    modifyProduct.set4_product_model(view.getProductModelTextField().getText());
                    modifyProduct.set5_product_price(Integer.valueOf(view.getProductPriceTextField().getText()));
                    
                    view.getProductTable().removeColumn(loadTableProduct);
                    modelProduct.update(modifyProduct);
                    view.getProductTable().addColumn(loadTableProduct);
                    loadTableProduct = loadTable((ArrayList) modelProduct.obtainList(),view.getProductTable(),Product.class);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto para modificarlo", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        view.getDeleteProductButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableColumnModel tcm = (TableColumnModel) view.getProductTable().getColumnModel();
                if (view.getProductTable().getSelectedRow() != -1) {
                    DefaultTableModel tm = (DefaultTableModel) view.getProductTable().getModel();
                    
                    Product deleteProduct = (Product) tm.getValueAt(view.getProductTable().getSelectedRow(), tm.getColumnCount() -1);
                    view.getProductTable().removeColumn(loadTableProduct);
                    modelProduct.destroy(deleteProduct);
                    
                    view.getProductTable().addColumn(loadTableProduct);
                    loadTableProduct = loadTable((ArrayList) modelProduct.obtainList(),view.getProductTable(),Product.class);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccciona un producto para eliminarlo", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        view.getProductTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (view.getProductTable().getSelectedRow() != -1) {
                    super.mouseClicked(e);
                    DefaultTableModel model = (DefaultTableModel) view.getProductTable().getModel();
                    view.getProductIdTextField().setText(model.getValueAt(Integer.valueOf(view.getProductTable().getSelectedRow()), 0).toString());
                    view.getProductNameTextField().setText(model.getValueAt(view.getProductTable().getSelectedRow(), 1).toString());
                    view.getProductTraceMarkTextField().setText(model.getValueAt(view.getProductTable().getSelectedRow(), 2).toString());
                    view.getProductModelTextField().setText(model.getValueAt(view.getProductTable().getSelectedRow(), 3).toString());
                    view.getProductPriceTextField().setText(model.getValueAt(Integer.valueOf(view.getProductTable().getSelectedRow()), 4).toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto de la tabla", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }); 
        
        clearTextFieldProduct();
    }
    
    public void clearTextFieldProduct() {
        view.getClearProductButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.getProductIdTextField().setText("");
                view.getProductNameTextField().setText("");
                view.getProductTraceMarkTextField().setText("");
                view.getProductModelTextField().setText("");
                view.getProductPriceTextField().setText("");
            }
        });
    }
    
    public void exitButton() {
        view.getExitButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    public static class OrdenarMetodeClasseAlfabeticament implements Comparator {
        public int compare(Object o1, Object o2) {

            Method mo1 = ((PropertyDescriptor) o1).getReadMethod();
            Method mo2 = ((PropertyDescriptor) o2).getReadMethod();

            if (mo1 != null && mo2 != null) {
                return (int) mo1.getName().compareToIgnoreCase(mo2.getName());
            }

            if (mo1 == null) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public static class OrdenarCampClasseAlfabeticament implements Comparator {
        public int compare(Object o1, Object o2) {
            return (int) (((Field) o1).getName().compareToIgnoreCase(((Field) o2).getName()));
        }
    }
}
