/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.controllers;

import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import model.BarCodeGenerator;
import model.entities.BarCode;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.Size;
import model.entities.StorageUnit;
import model.entities.Unit;

/**
 *
 * @author davidpatty
 */
public class ItemControllerTest {
	
	BarCode b;
	Product p;
	StorageUnit su;
	ProductGroup g;
	
	public ItemControllerTest() {
		
	}
	
	@BeforeClass
	public static void setUpClass() {

	}
	
	@AfterClass
	public static void tearDownClass() {
	
	}
	
	@Before
	public void setUp() {
	
	}
	
	@After
	public void tearDown() {
	
	}
	
	//Entry Date	Cannot be in the future
	//Entry Date	Cannot be prior to 1/1/2000
	//Exit Time	This attribute is defined only if the Item has been removed from storage.
	//Exit Time	Cannot be in the future or prior to 12 AM on the Item’s Entry Date
	//Expiration Date	This attribute is defined only if the Product’s Shelf Life attribute has been specified.
	//Container	Empty if the Item has been removed from storage.
	//Container	Non-empty if the Item has not been removed from storage. (Before it is removed, an Item is contained in one Product Container. After it is removed, it is contained in no Product Containers.)

	
	 @Test
	 public void ItemHasValidEntryDate() {
//		 BarCode barCode, Date entryDate, Date expirationDate, 
//			Product product, ProductContainer container
		b = BarCodeGenerator.getInstance().generateBarCode();
		su = new StorageUnit("Unit 1");
		
		g = new ProductGroup("Group 1", su, new Size(Unit.gallons, 5));

		p = new Product("Product", BarCodeGenerator.getInstance().generateBarCode(),
			    0, 0, new Size(Unit.count, 1));
		//test empty date
		try {			
			new Item(b, null, new Date(), p, g);
			//only passes if throws an exception
			assertTrue(false);
		} catch(IllegalArgumentException e) {
			assertTrue(e != null);
		}
		//Entry Date	cannot be in the future
		try {
			Calendar c = Calendar.getInstance();
			c.set(2014,1,1);
			new Item(b, c.getTime(), null, p, g);
			assertTrue(false);
		} catch(IllegalArgumentException e) {
			assertTrue(e != null);
		}
		
		try {
			//Entry Date	Cannot be prior to 1/1/2000
			Calendar c = Calendar.getInstance();
			c.set(1999,1,1);
			new Item(b, c.getTime(), null, p, g);
			//only passes if throws an exception
			assertTrue(false);
		} catch(IllegalArgumentException e) {
			assertTrue(e != null);
		}
		//everything is valid
		try {
			//Entry Date	Cannot be prior to 1/1/2000
			Calendar c = Calendar.getInstance();
			c.set(2002,1,1);
			new Item(b, c.getTime(), null, p, g);
			//only passes if throws an exception
		} catch(IllegalArgumentException e) {
			assertTrue(false);
		}
		
		
		
	 } 
	 
	 @Test
	 public void ItemHasValidExitDate() {
	 
	 }
	 
	 @Test
	 public void ItemHasValidContainer() {
	 
	 }
}
