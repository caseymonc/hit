package model.entities;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Date;

import model.BarCodeGenerator;
import model.managers.StorageUnitManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class ProductContainerTest {
	private static StorageUnit unit;
	private static ProductGroup group1;
	private static ProductGroup group2;
	private static Product product;
	private static Item item;
	private static Item item2;
	private static Product product2;
	private static Item item3;
	private static Item item4;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		unit = new StorageUnit("Unit");
		
		group1 = new ProductGroup("Group 1", unit, new Size(Unit.gallons, 5));
		group2 = new ProductGroup("Group 2", unit, new Size(Unit.gallons, 5));
		
		product = new Product("Product", BarCodeGenerator.getInstance().generateBarCode(),
				0, 0, new Size(Unit.count, 1));

		item = new Item(BarCodeGenerator.getInstance().generateBarCode()
				, new Date(), new Date(), product, unit);
		
		item2 = new Item(BarCodeGenerator.getInstance().generateBarCode()
				, new Date(), new Date(), product, unit);
		
		product2 = new Product("Product", BarCodeGenerator.getInstance().generateBarCode(),
				0, 0, new Size(Unit.count, 1));
		
		item3 = new Item(BarCodeGenerator.getInstance().generateBarCode()
				, new Date(), new Date(), product2, unit);
		
		item4 = new Item(BarCodeGenerator.getInstance().generateBarCode()
				, new Date(), new Date(), product2, unit);
	}

	@After
	public void tearDown() throws Exception {
		unit = null;
		group1 = null;
		group2 = null;
		product = null;
		item = null;
		item2 = null;
		product2 = null;
		item3 = null;
		item4 = null;
	}
	
	@Test
	public void testConstructor(){
		try{
			StorageUnit unit = new StorageUnit(null);
			assertFalse(true);
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
		
		try{
			StorageUnit unit = new StorageUnit("");
			assertFalse(true);
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
	}
	
	@Test
	public void addItemTest(){
		unit.addProductGroup(group1);
		unit.addProductGroup(group2);
				
		unit.addItem(item);
		assertTrue(unit.getItemsByProduct(item.getProduct()).contains(item));
		
		unit.addItem(item2);
		assertTrue(unit.getItemsByProduct(item2.getProduct()).contains(item2));
		
		group2.addProduct(product2);
		unit.setProductForContainer(product2, group2);
		
		unit.addItem(item3);
		
		assertTrue(group2.getItemsByProduct(product2).contains(item3));
		
		unit.addItem(item4);
		
		assertTrue(group2.getItemsByProduct(product2).contains(item4));
	}
	
	@Test
	public void removeItemTest(){
		unit.addItem(item);
		unit.addItem(item2);
		
		unit.removeItem(item);
		assertFalse(unit.getItemsByProduct(item.getProduct()).contains(item));
	}
	
	@Test
	public void addProductTest(){
		setupTree();
		group2.addProduct(product);

		
		assertTrue(unit.getProductGroupByProduct(product) == group2);
		assertTrue(group2.getAllItems().contains(item));
		assertTrue(group2.getAllItems().contains(item2));
		
		unit.addProduct(product);
		unit.addProduct(product2);
		
		assertTrue(unit.getProductGroupByProduct(product) == unit);
		assertTrue(unit.getProductGroupByProduct(product2) == unit);
		assertTrue(unit.getAllItems().contains(item));
		assertTrue(unit.getAllItems().contains(item2));
		assertTrue(unit.getAllItems().contains(item3));
		assertTrue(unit.getAllItems().contains(item4));
		
		group1.addProduct(product);
		assertTrue(unit.getProductGroupByProduct(product) == group1);
		assertTrue(group1.getAllItems().contains(item));
		assertTrue(group1.getAllItems().contains(item2));
		
	}
	
	@Test
	public void removeProductTest(){
		setupTree();
		
		assertFalse(unit.canRemoveProduct(product));
		unit.removeItem(item);
		unit.removeItem(item2);
		assertTrue(unit.canRemoveProduct(product));
		
		unit.removeProduct(product);
		assertFalse(unit.getAllProducts().contains(product));
		
		assertFalse(group2.canRemoveProduct(product2));
		group2.removeItem(item3);
		group2.removeItem(item4);
		assertTrue(group2.canRemoveProduct(product2));
		
		group2.removeProduct(product2);
		assertFalse(group2.getAllProducts().contains(product2));
	}
	
	@Test
	public void addProductGroupTest(){
		setupTree();
		assertTrue(unit.getAllProductGroup().contains(group1));
		
		ProductGroup group3 = new ProductGroup("Group 3", unit, new Size(Unit.gallons, 5));
		ProductGroup group4 = new ProductGroup("Group 3", unit, new Size(Unit.gallons, 5));
		
		group1.addProductGroup(group3);
		assertFalse(group1.canAddProductGroup(group4));
		
		assertTrue(group3.canAddProductGroup(group4));
		group3.addProductGroup(group4);
		assertTrue(group3.getAllProductGroup().contains(group4));
	}
	
	@Test
	public void removeProductGroupTest(){
		setupTree();
		
		assertTrue(unit.canRemoveProductGroup(group1));
		assertFalse(unit.canRemoveProductGroup(group2));
		
		unit.removeProductGroup(group1);
		assertFalse(unit.getAllProductGroup().contains(group1));
		
		try{
			unit.removeProductGroup(group2);
			assertFalse(true);
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
		
		group2.removeItem(item3);
		group2.removeItem(item4);
		
		assertTrue(unit.canRemoveProductGroup(group2));
		unit.removeProductGroup(group2);
		assertFalse(unit.getAllProductGroup().contains(group2));
	}
	
	@Test
	public void getObjectByTest(){
		setupTree();
		assertTrue(unit.getProductGroupByName(group1.getName()) == group1);
		assertTrue(unit.getProductGroupByName(group2.getName()) == group2);
		assertTrue(unit.getProductGroupByName(group1.getName()) != group2);
		assertTrue(unit.getItemByBarCode(item.getBarCode()) == item);
		assertTrue(unit.getItemByBarCode(item2.getBarCode()) == item2);
		assertTrue(unit.getItemByBarCode(item.getBarCode()) != item2);
		assertTrue(unit.getItemsByProduct(product).contains(item));
		assertTrue(unit.getItemsByProduct(product).contains(item2));
		assertTrue(!unit.getItemsByProduct(product).contains(item3));
		assertTrue(unit.getProductGroupByProduct(product) == unit);
		assertTrue(unit.getProductGroupByProduct(product2) == group2);
		assertTrue(group1.getContainer() == unit);
	}
	
	@Test
	public void setNameTest(){
		assertFalse(unit.canSetName(""));
		assertFalse(unit.canSetName(null));
		assertTrue(unit.canSetName("Name"));
	}
	
	private void setupTree(){
		unit.addProductGroup(group1);
		unit.addProductGroup(group2);
				
		unit.addItem(item);		
		unit.addItem(item2);
		
		group2.addProduct(product2);
		unit.setProductForContainer(product2, group2);
		
		unit.addItem(item3);
		unit.addItem(item4);
		
	}
}
