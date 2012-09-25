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
	

}
