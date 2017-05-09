/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Category;
import entities.Product;
import entities.Stock;
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
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
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
    private ClassDAO<Stock> modelStock;
    private ClassDAO<Category> modelCategory;
    
    private TableColumn loadTableProduct;
    private TableColumn loadTableStock;
    private TableColumn loadTableCategory;
    
    private int filasel = -1;

    public Controller(View view, ClassDAO<Product> modelProduct, ClassDAO<Stock> modelStock, ClassDAO<Category> modelCategory) {
        this.view = view;
        this.modelProduct = modelProduct;
        this.modelStock = modelStock;
        this.modelCategory = modelCategory;
        
        loadTableProduct = loadTable((ArrayList) modelProduct.obtainList(), view.getjTableProduct(), Product.class);
        loadTableStock = loadTable((ArrayList) modelStock.obtainList(), view.getjTableStock(), Stock.class);
        loadTableCategory = loadTable((ArrayList) modelCategory.obtainList(), view.getjTableCategory(), Category.class);
        
        loadCombo((ArrayList)modelStock.obtainList(),view.getjComboBoxProductStock());
        loadCombo((ArrayList)modelCategory.obtainList(),view.getjComboBoxProductCategory());
        
        controlProduct();
        controlStock();
        controlCategory();
        
        exitButton();
    }
    
    private void controlProduct() {
        view.getjButtonProductCreate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(view.getjButtonProductCreate())) {
                    try {
                        if(!view.getjTextFieldProductId().getText().trim().equals("") || 
                            !view.getjTextFieldProductName().getText().trim().equals("") || 
                            !view.getjTextFieldProductTraceMark().getText().trim().equals("") || 
                            !view.getjTextFieldProductModel().getText().trim().equals("") || 
                            !view.getjTextFieldProductPrice().getText().trim().equals(""))
                        modelProduct.obtainList();

                        Product p = new Product(
                                view.getjTextFieldProductName().getText(),
                                view.getjTextFieldProductTraceMark().getText(),
                                view.getjTextFieldProductModel().getText(),
                                Double.valueOf(view.getjTextFieldProductPrice().getText()),
                                (Stock) view.getjComboBoxProductStock().getSelectedItem(),
                                (Category) view.getjComboBoxProductCategory().getSelectedItem()
                        );
                        modelProduct.store(p);
                        loadTable((ArrayList) modelProduct.obtainList(),view.getjTableProduct(),Product.class);
                    } catch(NumberFormatException ex) {
                        JOptionPane.showMessageDialog(view, "El precio tiene que ser entero","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No has introducido ningun producto", "ERROR",JOptionPane.ERROR_MESSAGE);
                }

                view.getjTextFieldProductId().setText("");
                view.getjTextFieldProductName().setText("");
                view.getjTextFieldProductTraceMark().setText("");
                view.getjTextFieldProductModel().setText("");
                view.getjTextFieldProductPrice().setText("");
                view.getjTextFieldStockTotal().setText("");
            }
        });
        
        view.getjButtonProductModify().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableColumnModel tcm = (TableColumnModel) view.getjTableProduct().getColumnModel();
                if (view.getjTableProduct().getSelectedRow() != -1) {
                    try {
                        view.getjTableProduct().addColumn(loadTableProduct);
                        DefaultTableModel tm = (DefaultTableModel) view.getjTableProduct().getModel();
                        Product modifyProduct = (Product) tm.getValueAt(view.getjTableProduct().getSelectedRow(), tm.getColumnCount() -1);
                        modifyProduct.set2_product_name(view.getjTextFieldProductName().getText());
                        modifyProduct.set3_product_trademark(view.getjTextFieldProductTraceMark().getText());
                        modifyProduct.set4_product_model(view.getjTextFieldProductModel().getText());
                        modifyProduct.set5_product_price(Double.valueOf(view.getjTextFieldProductPrice().getText()));
                        modifyProduct.set6_stored((Stock) view.getjComboBoxProductStock().getSelectedItem());
                        modifyProduct.set7_category((Category) view.getjComboBoxProductCategory().getSelectedItem());

                        view.getjTableProduct().removeColumn(loadTableProduct);
                        modelProduct.update(modifyProduct);
                        view.getjTableProduct().addColumn(loadTableProduct);
                        loadTableProduct = loadTable((ArrayList) modelProduct.obtainList(),view.getjTableProduct(),Product.class);
                        loadCombo((ArrayList)modelStock.obtainList(),view.getjComboBoxProductStock());
                        loadCombo((ArrayList)modelCategory.obtainList(),view.getjComboBoxProductCategory());
                    } catch(NumberFormatException ex) {
                        JOptionPane.showMessageDialog(view, "El precio tiene que ser entero","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto para modificarlo", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
                    
                view.getjTextFieldProductId().setText("");
                view.getjTextFieldProductName().setText("");
                view.getjTextFieldProductTraceMark().setText("");
                view.getjTextFieldProductModel().setText("");
                view.getjTextFieldProductPrice().setText("");
            }
        });
        
        view.getjButtonProductDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableColumnModel tcm = (TableColumnModel) view.getjTableProduct().getColumnModel();
                if (view.getjTableProduct().getSelectedRow() != -1) {
                    DefaultTableModel tm = (DefaultTableModel) view.getjTableProduct().getModel();
                    
                    Product deleteProduct = (Product) tm.getValueAt(view.getjTableProduct().getSelectedRow(), tm.getColumnCount() -1);
                    view.getjTableProduct().removeColumn(loadTableProduct);
                    modelProduct.destroy(deleteProduct);
                    
                    view.getjTableProduct().addColumn(loadTableProduct);
                    loadTableProduct = loadTable((ArrayList) modelProduct.obtainList(),view.getjTableProduct(),Product.class);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccciona un producto para eliminarlo", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                
                view.getjTextFieldProductId().setText("");
                view.getjTextFieldProductName().setText("");
                view.getjTextFieldProductTraceMark().setText("");
                view.getjTextFieldProductModel().setText("");
                view.getjTextFieldProductPrice().setText("");
            }
        });
        
        
        view.getjTableProduct().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (view.getjTableProduct().getSelectedRow() != -1) {
                    super.mouseClicked(e);
                    DefaultTableModel model = (DefaultTableModel) view.getjTableProduct().getModel();
                    view.getjTextFieldProductId().setText(model.getValueAt(Integer.valueOf(view.getjTableProduct().getSelectedRow()), 0).toString());
                    view.getjTextFieldProductName().setText(model.getValueAt(view.getjTableProduct().getSelectedRow(), 1).toString());
                    view.getjTextFieldProductTraceMark().setText(model.getValueAt(view.getjTableProduct().getSelectedRow(), 2).toString());
                    view.getjTextFieldProductModel().setText(model.getValueAt(view.getjTableProduct().getSelectedRow(), 3).toString());
                    view.getjTextFieldProductPrice().setText(model.getValueAt(Integer.valueOf(view.getjTableProduct().getSelectedRow()), 4).toString());
                    view.getjComboBoxProductStock().setSelectedItem(model.getValueAt(view.getjTableProduct().getSelectedRow(), 5).toString());
                    view.getjComboBoxProductCategory().setSelectedItem(model.getValueAt(view.getjTableProduct().getSelectedRow(), 6).toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto de la tabla", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }); 
        
        clearTextFieldProduct();
    }
    
    private void controlStock() {
        view.getjButtonStockCreate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(view.getjButtonStockCreate())) {
                    try {
                        if(!view.getjTextFieldStockId().getText().trim().equals("") ||
                           !view.getjTextFieldStockTotal().getText().trim().equals(""))
                        modelStock.obtainList();

                        Stock s = new Stock(
                                Integer.valueOf(view.getjTextFieldStockTotal().getText())
                        );
                        modelStock.store(s);

                        loadTable((ArrayList) modelStock.obtainList(),view.getjTableStock(),Stock.class);
                        loadCombo((ArrayList)modelStock.obtainList(),view.getjComboBoxProductStock());
                    } catch(NumberFormatException ex) {
                        JOptionPane.showMessageDialog(view, "El stock total tiene que ser un número entero","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No has introducido ningun producto en stock", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
                
                view.getjTextFieldStockTotal().setText("");
            }
        });
        
        view.getjButtonStockModify().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableColumnModel tcm = (TableColumnModel) view.getjTableStock().getColumnModel();
                if (view.getjTableStock().getSelectedRow() != -1) {
                    try {
                        view.getjTableStock().addColumn(loadTableStock);
                        DefaultTableModel tm = (DefaultTableModel) view.getjTableStock().getModel();
                        Stock modifyStock = (Stock) tm.getValueAt(view.getjTableStock().getSelectedRow(), tm.getColumnCount() -1);
                        modifyStock.set2_stock_total(Integer.valueOf(view.getjTextFieldStockTotal().getText()));

                        view.getjTableStock().removeColumn(loadTableStock);
                        modelStock.update(modifyStock);
                        view.getjTableStock().addColumn(loadTableStock);
                        loadTableStock = loadTable((ArrayList) modelStock.obtainList(),view.getjTableStock(),Stock.class);
                    } catch(NumberFormatException ex) {
                        JOptionPane.showMessageDialog(view, "El total tiene que ser un número entero","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un producto en stcok para modificarlo", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
                
                view.getjTextFieldStockTotal().setText("");
            }
        });
        
        view.getjButtonStockDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableColumnModel tcm = (TableColumnModel) view.getjTableStock().getColumnModel();
                if (view.getjTableStock().getSelectedRow() != -1) {
                    DefaultTableModel tm = (DefaultTableModel) view.getjTableStock().getModel();
                    
                    Stock deleteStock = (Stock) tm.getValueAt(view.getjTableStock().getSelectedRow(), tm.getColumnCount() -1);
                    view.getjTableStock().removeColumn(loadTableStock);
                    modelStock.destroy(deleteStock);
                    
                    view.getjTableStock().addColumn(loadTableStock);
                    loadTableStock = loadTable((ArrayList) modelStock.obtainList(),view.getjTableStock(),Stock.class);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccciona un producto en stock para eliminarlo", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                
                view.getjTextFieldStockTotal().setText("");
            }
        });
        
        view.getjTableStock().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (view.getjTableStock().getSelectedRow() != -1) {
                    super.mouseClicked(e);
                    DefaultTableModel model = (DefaultTableModel) view.getjTableStock().getModel();
                    view.getjTextFieldStockId().setText(model.getValueAt(Integer.valueOf(view.getjTableStock().getSelectedRow()), 0).toString());
                    view.getjTextFieldStockTotal().setText(model.getValueAt(Integer.valueOf(view.getjTableStock().getSelectedRow()), 1).toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un stock de la tabla", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        view.getjButtonStockClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.getjTextFieldStockId().setText("");
                view.getjTextFieldStockTotal().setText("");
            }
        });
        
        clearTextFieldStock();
    }
    
    private void controlCategory() {
        view.getjButtonCategoryCreate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource().equals(view.getjButtonCategoryCreate())) {
                    if(!view.getjTextFieldCategoryId().getText().trim().equals("") ||
                       !view.getjTextFieldCategoryName().getText().trim().equals(""))
                    modelCategory.obtainList();

                    Category c = new Category(
                            view.getjTextFieldCategoryName().getText()
                    );
                    modelCategory.store(c);

                    loadTable((ArrayList) modelCategory.obtainList(),view.getjTableCategory(),Category.class);
                    loadCombo((ArrayList)modelCategory.obtainList(),view.getjComboBoxProductCategory());
                } else {
                    JOptionPane.showMessageDialog(null, "No has introducido ninguna categoria", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
                
                view.getjTextFieldCategoryName().setText("");
            }
        });
        
        view.getjButtonCategoryModify().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableColumnModel tcm = (TableColumnModel) view.getjTableCategory().getColumnModel();
                if (view.getjTableCategory().getSelectedRow() != -1) {
                    view.getjTableCategory().addColumn(loadTableCategory);
                    
                    DefaultTableModel tm = (DefaultTableModel) view.getjTableCategory().getModel();
                    Category modifyCategory = (Category) tm.getValueAt(view.getjTableCategory().getSelectedRow(), tm.getColumnCount() -1);
                    modifyCategory.set2_category_name(view.getjTextFieldCategoryName().getText());

                    view.getjTableCategory().removeColumn(loadTableCategory);
                    modelCategory.update(modifyCategory);
                    view.getjTableCategory().addColumn(loadTableCategory);
                    loadTableCategory = loadTable((ArrayList) modelCategory.obtainList(),view.getjTableCategory(),Category.class);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un categoria para modificarla", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
                
                view.getjTextFieldCategoryName().setText("");
            }
        });
        
        view.getjButtonCategoryDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableColumnModel tcm = (TableColumnModel) view.getjTableCategory().getColumnModel();
                if (view.getjTableCategory().getSelectedRow() != -1) {
                    DefaultTableModel tm = (DefaultTableModel) view.getjTableCategory().getModel();
                    
                    Category deleteCategory = (Category) tm.getValueAt(view.getjTableCategory().getSelectedRow(), tm.getColumnCount() -1);
                    view.getjTableCategory().removeColumn(loadTableCategory);
                    modelCategory.destroy(deleteCategory);
                    
                    view.getjTableCategory().addColumn(loadTableCategory);
                    loadTableCategory = loadTable((ArrayList) modelCategory.obtainList(),view.getjTableCategory(),Category.class);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccciona una categoria para eliminarla", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                
                view.getjTextFieldCategoryName().setText("");
            }
        });
        
        view.getjTableCategory().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (view.getjTableCategory().getSelectedRow() != -1) {
                    super.mouseClicked(e);
                    DefaultTableModel model = (DefaultTableModel) view.getjTableCategory().getModel();
                    view.getjTextFieldCategoryId().setText(model.getValueAt(Integer.valueOf(view.getjTableCategory().getSelectedRow()), 0).toString());
                    view.getjTextFieldCategoryName().setText(model.getValueAt(view.getjTableCategory().getSelectedRow(), 1).toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una categoria de la tabla", "ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        view.getjButtonCategoryClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.getjTextFieldCategoryId().setText("");
                view.getjTextFieldCategoryName().setText("");
            }
        });
        
        clearTextFieldCategory();
    }
    
    public void clearTextFieldProduct() {
        view.getjButtonProductClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.getjTextFieldProductId().setText("");
                view.getjTextFieldProductName().setText("");
                view.getjTextFieldProductTraceMark().setText("");
                view.getjTextFieldProductModel().setText("");
                view.getjTextFieldProductPrice().setText("");
            }
        });
    }
    
    public void clearTextFieldStock() {
        view.getjButtonStockClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.getjTextFieldStockId().setText("");
                view.getjTextFieldStockTotal().setText("");
            }
        });
    }
    
    public void clearTextFieldCategory() {
        view.getjButtonCategoryClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.getjTextFieldCategoryId().setText("");
                view.getjTextFieldCategoryName().setText("");
            }
        });
    }
    
    public void exitButton() {
        view.getjButtonExit().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    /**
     * Mètode que carrega els objectes continguts a l'ArrayList i el mostra a la JTable. La classe indica de quin tipo són els objectes de l'ArrayList
     * Si volem que es pugue modificar les dades directament des de la taula hauríem d'usar el model instància de la classe ModelCanvisBD, que varia d'una BD a una altra
     * Esta versió afegix a la darrera columna de la taula l'objecte mostrat a la mateixa de manera que el podrem recuperar fàcilment per fer updates, deletes, etc...
     * Esta columna extra no apareix a la taula ja que la borrem, però la retornem per poder-la afegir quan sigue necessari
     */ 
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
        model = new DefaultTableModel(data, columnNames);
        table.setModel(model);

        //Borro la darrera columna per a que no aparegue a la vista, però abans la guardo en una variable que al final serà el que retorna el mètode
        TableColumnModel tcm = table.getColumnModel();
        TableColumn columna = tcm.getColumn(tcm.getColumnCount() - 1);
        tcm.removeColumn(columna);

        //Fixo l'amplada de les columnes que sí es mostren
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setMaxWidth(250);
        }
        
        return columna;
    }
    
    //per carregar un JComboBox a partir d'un ArrayList que conté les dades
    private void loadCombo(ArrayList resultSet, JComboBox combo) {
        combo.setModel(new DefaultComboBoxModel((resultSet != null ? resultSet.toArray() : new Object[]{})));
    }
    
    private static class OrdenarMetodeClasseAlfabeticament implements Comparator {
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

    private static class OrdenarCampClasseAlfabeticament implements Comparator {
        public int compare(Object o1, Object o2) {
            return (int) (((Field) o1).getName().compareToIgnoreCase(((Field) o2).getName()));
        }
    }
}
