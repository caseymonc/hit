/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import java.util.Date;
import model.BarCodeGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import model.managers.ItemManager;

/**
 *
 * @author davidpatty
 */
public class ItemTest {
	
	ItemManager IM;
	StorageUnit unit;
	ProductGroup group;
	Product product;
	
	public ItemTest() {
		IM = new ItemManager();
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
	
	//Barcode	Must be a valid UPC barcode
	@Test
	public void testValidBarcode() 
	{
		unit = new StorageUnit("Unit 1");
		group = new ProductGroup("Group 1", unit, new Size(Unit.gallons, 5));
		
		product = new Product("Product", BarCodeGenerator.getInstance().generateBarCode(),
				0, 0, new Size(Unit.count, 1));

		Item item = new Item(BarCodeGenerator.getInstance().generateBarCode()
				, new Date(), new Date(), product, unit);
		
		Item item2 = new Item(BarCodeGenerator.getInstance().generateBarCode()
				, new Date(), new Date(), product, unit);
		assertTrue(item.getBarCode().isValid());
		assertTrue(item2.getBarCode().isValid());
	}
	
	
	@Test
	public void test()
	{
		
	}
	

	//Entry Date	Must be non‐empty.
	//Entry Date	Cannot be in the future
	//Entry Date	Cannot be prior to 1/1/2000
	//Exit Time	This attribute is defined only if the Item has been removed from storage.
	//Exit Time	Cannot be in the future or prior to 12 AM on the Item’s Entry Date
	//Expiration Date	This attribute is defined only if the Product’s Shelf Life attribute has been specified.
	//Container	Empty if the Item has been removed from storage.
	//Container	Non‐empty if the Item has not been removed from storage. (Before it is removed, an Item is contained in one Product Container. After it is removed, it is contained in no Product Containers.)
}
