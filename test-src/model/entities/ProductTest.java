/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import model.managers.*;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dmathis
 */
public class ProductTest {
    
    private static ProductManager productManager;
    
    
    public ProductTest() {
    }

    @Before
    public void setUp() {
        productManager = new ProductManager();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreationDate() {
        Product product1 = new Product("Test Product", new BarCode("123412341234"), 3, 3, new Size(Unit.count, 1));
        Date date1 = new Date();
        Date date2 = new Date();

        date2.setDate(20);
        
        assertTrue(product1.getCreationDate() != null);
        
        Item item1 = new Item(new BarCode("111111111111"), date1, date1, product1, null);
        Item item2 = new Item(new BarCode("111111111112"), date2, date2, product1, null);
        
        productManager.addProduct(product1);
        productManager.addItemToProduct(product1, item1);
        productManager.addItemToProduct(product1, item2);
        
        assertTrue(product1.getCreationDate().compareTo(date2) == 0);         
    }
    
    @Test
    public void testCreateProduct() {
       
        BarCode goodCode = new BarCode("111111111111");
        BarCode badCode = new BarCode("");
        Size goodSize1 = new Size(Unit.count, 1);
        Size goodSize2 = new Size(Unit.gallons, 1);
        Size badSize1 = new Size(Unit.gallons, 0);
        Size badSize2 = new Size(Unit.count, 2);
        
        assertFalse(Product.canCreate("", goodCode, 3, 3, goodSize1));
        assertFalse(Product.canCreate("Bad BarCode", badCode, 3, 3, goodSize1));
        assertFalse(Product.canCreate("Bad Size", goodCode, 3, 3, badSize1));       
        assertFalse(Product.canCreate("Bad Size", goodCode, 3, 3, badSize2));        
        assertFalse(Product.canCreate("Bad Shelf Life", goodCode, -1, 3, goodSize1));
        assertFalse(Product.canCreate("Bad 3-Month Supply", goodCode, 3, -1, goodSize1));
        
        assertTrue(Product.canCreate("Good Product", goodCode, 3, 3, goodSize1));
        assertTrue(Product.canCreate("Good Product", goodCode, 0, 3, goodSize1));
        assertTrue(Product.canCreate("Good Product", goodCode, 3, 0, goodSize1));
        assertTrue(Product.canCreate("Good Product", goodCode, 3, 0, goodSize2));
    }
    
    @Test
    public void AddProductToContainer(){
        StorageUnit unit = new StorageUnit("Test Unit");
        Size size = new Size(Unit.count, 1);
        
        ProductGroup group1 = new ProductGroup("Group 1", unit, size);
        ProductGroup group2 = new ProductGroup("Group 2", unit, size);
        
        unit.addProductGroup(group1);
        unit.addProductGroup(group2);
        
        Product product1 = new Product("Test Product", new BarCode("123412341234"), 3, 3, size);
        
        assertTrue(productManager.canAddProductToContainer(product1, group1));
        productManager.addProductToContainer(product1, group1);
        
        assertTrue(productManager.hasProduct(product1));
        assertTrue(product1.getContainers().contains(group1));
        
        assertTrue(productManager.canAddProductToContainer(product1, group2));
        productManager.addProductToContainer(product1, group2);
        
        assertTrue(product1.getContainers().contains(group2));
        assertFalse(product1.getContainers().contains(group1));
    }
}
