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
	
	private BarCode b;
	private Product p;
	private StorageUnit su;
	private ProductGroup g;
	private CoreObjectModel COM;
	private StorageUnitController SC;
	private ProductGroupController PGC;
	private ItemController IC;
	
	public ItemControllerTest() {
		COM = CoreObjectModel.getInstance();
		SC = COM.getStorageUnitController();
		PGC = COM.getProductGroupController();
		IC = COM.getItemController();
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
	public void ItemHasValidProduct() {
		assertFalse(Item.canCreate(b, new Date(), null, null, g));
	}
	
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
	 *  (called the "target Storage Unit"). The new Item is added to the same Product Container
	 *  that contains the Item’s Product within the target Storage Unit. If the Item’s Product 
	 *  is not already in a product Container within the target Storage Unit, the Product
	 *  is placed in the Storage Unit at the root level.
	 */	 
	@Test
	public void ItemAddsToCorrectPlace() {		
		StorageUnit rootUnit = new StorageUnit("SUnit");
		SC.addStorageUnit(rootUnit);
		ProductGroup destination1 = new ProductGroup("ContainsProduct",rootUnit, new Size(Unit.count, 2));
		PGC.addProductGroup(destination1, rootUnit);
		destination1.addProduct(p);
		rootUnit.setProductForContainer(p, destination1);
		
		Item i = new Item(b, new Date(), null, p, null);
		IC.addItem(i,rootUnit);
		//i's container should not be rootUnit
		assertFalse(i.getContainer() == rootUnit);
		//i's container should be destination1
		assertTrue(i.getContainer() == destination1);
		//rootUnit should not contain the product
		assertFalse(rootUnit.getAllProducts().contains(p));
		//rootUnit should not contain the product
		assertTrue(destination1.getAllProducts().contains(p));
		
		Product p2 = new Product("NewProduct", new BarCode("222222222222"),0,5,new Size(Unit.quarts, 5));
		Item i2 = new Item(new BarCode("111111111111"), new Date(), null, p2, rootUnit);
		rootUnit.setProductForContainer(p2,rootUnit);
		IC.addItem(i2, rootUnit);
		
		//i2 should be in the destination storage unit
		assertTrue(i2.getContainer() == rootUnit);
		//i2 should not be in destianation1
		assertFalse(i2.getContainer() == destination1);
		//rootUnit should contain p2
		assertTrue(rootUnit.getAllProducts().contains(p2));
		//destination1 should not contain p2
		assertFalse(destination1.getAllProducts().contains(p2));
	}
		
	//An Item is contained in exactly one Product Container at a time (until it is removed, 
	//at which point it belongs to no Product Container at all)."
	@Test
	public void testMoveItems() {
		
		
		//set up original unit
		StorageUnit originalUnit = new StorageUnit("ItemStartsHere");
		SC.addStorageUnit(originalUnit);
		Item i = new Item(b, new Date(), null, p, null);
		IC.addItem(i,originalUnit);
		//set up second unit
		StorageUnit containingUnit = new StorageUnit("DropOn1");	
		ProductGroup moveToGroup = new ProductGroup("ContainsProduct",containingUnit, new Size(Unit.count, 1));
		SC.addStorageUnit(containingUnit);
		PGC.addProductGroup(moveToGroup, containingUnit);
		
		//starts in the original Unit
		assertTrue(i.getContainer() == originalUnit);
		assertTrue(originalUnit.canRemoveItem(i));
		assertTrue(moveToGroup.getContainer()!= null);
		assertTrue(moveToGroup.getStorageUnit() == containingUnit);
		
		IC.transferItem(i, moveToGroup);
		
		//Target Product Container = the Product Container the user dropped the Item on
		
		assertTrue(i.getContainer() == containingUnit);
		//Target Storage Unit = the Storage Unit containing the Target Product Container
		assertTrue(moveToGroup.getContainer() == containingUnit);
		
		// product is in target productContainer
		assertTrue(containingUnit.getAllProducts().contains(p));
		
		
		//add a new product group to MoveToUnit
		//add a new product to the new product group and to Original
		//add new product to the new product group and to Original
		//add some items of that product to the product group
		Product p2 = new Product("Second Product", new BarCode("222222222223"),0,2,new Size(Unit.count, 1));
		moveToGroup.addProduct(p2);
		containingUnit.setProductForContainer(p2,moveToGroup);
		originalUnit.addProduct(p2);
		originalUnit.setProductForContainer(p2,originalUnit);

		Item i2 = new Item(new BarCode("111111111112"), new Date(), null, p2, null);
		IC.addItem(i2, originalUnit);
		
		//we want to start out with the product in the base. 
		assertTrue(originalUnit.getAllProducts().contains(p2));


		IC.transferItem(i2, moveToGroup);

		//this time the product will end up in moveToGroup
		assertTrue(i2.getContainer() == moveToGroup);
		assertTrue(moveToGroup.getAllItems().contains(i2));
		
		//the product moved from the storageUnit base
		assertFalse(moveToGroup.getContainer().getAllProducts().contains(p2));
		assertTrue(moveToGroup.getAllProducts().contains(p2));
	}
	//When an item is removed
	@Test
	public void testRemoveItem() {
		Item i = new Item(b, new Date(), null, p, su);
		IC.addItem(i, su);
		
		IC.removeItem(i);
		ItemManager IM = IC.getItemManager();
		//1. The Item is removed from its containing Storage Unit.
		assertTrue(i.getContainer() == null);
		assertFalse(su.getAllItems().contains(i));
		assertTrue(su.getAllProducts().contains(i.getProduct()));
		//2. The Exit Time is stored in the Item.
		assertFalse(i.getExitDate() == null);
		//3. The Item is retained for historical purposes (i.e., for calculating statistics and reporting)."
		assertTrue(IM.itemIsInRemovedItems(i));
	}	
}
