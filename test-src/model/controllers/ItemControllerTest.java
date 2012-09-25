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

import model.*;
//import model.BarCodeGenerator;
import model.entities.*;
import model.managers.*;
import model.controllers.*;

//import model.entities.Item;
//import model.entities.Product;
//import model.entities.ProductGroup;
//import model.entities.Size;
//import model.entities.StorageUnit;
//import model.entities.Unit;

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
		b = BarCodeGenerator.getInstance().generateBarCode();
		su = new StorageUnit("Unit 1");
		
		g = new ProductGroup("Group 1", su, new Size(Unit.gallons, 5));

		p = new Product("Product", BarCodeGenerator.getInstance().generateBarCode(),
			    0, 0, new Size(Unit.count, 1));
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
	//Container	Non-empty if the Item has not been removed from storage. (Before it is removed, 
	//             an Item is contained in one Product Container. After it is removed, it is contained in no Product Containers.)

	
	@Test
	public void ItemHasValidEntryDate() {

		//test empty date
		assertFalse(Item.canCreate(b, null, new Date(), p, g));

		//Entry Date	cannot be in the future
		Calendar c = Calendar.getInstance();
		c.set(2014,1,1);
		assertFalse(Item.canCreate(b, c.getTime(), null, p, g));

		//Entry Date	Cannot be prior to 1/1/2000
		c.set(1999,1,1);
		assertFalse(Item.canCreate(b, c.getTime(), null, p, g));

		//all is valid
		c.set(2002,1,1);
		assertTrue(Item.canCreate(b, c.getTime(), null, p, g));

	} 

	@Test
	public void ItemHasValidExitDate() {
		Item i = new Item(b, new Date(), null, p, g);
		i.remove();
		assertTrue(i.hasValidExitDate());
	}


	//Container	Empty if the Item has been removed from storage.
	//Container	Non-empty if the Item has not been removed from storage. (Before it is removed, 
	//             an Item is contained in one Product Container. After it is removed, it is contained
	//             in no Product Containers.)
	@Test
	public void ItemHasValidContainer() {
		Item i = new Item(b, new Date(), null, p, g);
		assertFalse(i.getContainer() == null);
		i.remove();
		assertTrue(i.getContainer() == null);
	}
	/**
	 *  When a new Item is added to the system, it is placed in a particular Storage Unit 
	 *  (called the “target Storage Unit”). The new Item is added to the same Product Container
	 *  that contains the Item’s Product within the target Storage Unit. If the Item’s Product 
	 *  is not already in a product Container within the target Storage Unit, the Product
	 *  is placed in the Storage Unit at the root level.
	 */	 
	@Test
	public void ItemAddsToCorrectPlace() {
		CoreObjectModel COM = CoreObjectModel.getInstance();
		StorageUnitController SC = COM.getStorageUnitController();
		ProductGroupController PGC = COM.getProductGroupController();
		ItemController IC = COM.getItemController();
		
		
		StorageUnit rootUnit = new StorageUnit("SUnit");
		SC.addStorageUnit(rootUnit);
		ProductGroup destination1 = new ProductGroup("ContainsProduct",rootUnit, new Size(Unit.count, 2));
		PGC.addProductGroup(destination1, rootUnit);
		destination1.addProduct(p);
		rootUnit.setProductForContainer(p, destination1);
		
		Item i = new Item(b, new Date(), null, p, null);
		IC.addItem(i,rootUnit);
		assertFalse(i.getContainer() == rootUnit);//why does this fail?
		assertFalse(rootUnit.getAllProducts().contains(p));
		assertTrue(i.getContainer() == destination1);
		
		BarCode b2 = BarCodeGenerator.getInstance().generateBarCode();
		Product p2 = new Product("NewProduct", BarCodeGenerator.getInstance().generateBarCode(),0,5,new Size(Unit.quarts, 5));
		Item i2 = new Item(b2, new Date(), null, p2, rootUnit);
		rootUnit.setProductForContainer(p2,rootUnit);

		//should be in the destination storage unit
		IC.addItem(i2, rootUnit);
		assertTrue(i2.getContainer() == rootUnit);
		assertTrue(rootUnit.getAllProducts().contains(p2));
		assertFalse(i2.getContainer() == destination1);
	}
	
	
}
