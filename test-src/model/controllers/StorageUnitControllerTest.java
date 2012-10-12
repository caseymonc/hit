package model.controllers;

import static org.junit.Assert.*;

import java.util.Date;

import model.BarCodeGenerator;
import model.CoreObjectModel;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.Size;
import model.entities.StorageUnit;
import model.entities.Unit;
import model.managers.StorageUnitManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageUnitControllerTest {
	private static StorageUnitManager manager;
	private static CoreObjectModel coreObjectModel;
	private static StorageUnitController controller;
	private static StorageUnit unit;
	private static StorageUnit unit2;
	private static StorageUnit unit3;
	private static StorageUnit unit4;
	private static StorageUnit unit5;
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
		coreObjectModel = CoreObjectModel.getInstance();
		controller = coreObjectModel.getStorageUnitController();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		unit = new StorageUnit("Unit 1");
		unit2 = new StorageUnit("Unit 2");
		unit3 = new StorageUnit("Unit 3");
		unit4 = new StorageUnit("Unit 4");
		unit5 = new StorageUnit("Unit 1");
		
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
		if(controller.canDeleteStorageUnit(unit))
			controller.deleteStorageUnit(unit);
		if(controller.canDeleteStorageUnit(unit2))
			controller.deleteStorageUnit(unit2);
		if(controller.canDeleteStorageUnit(unit3))
			controller.deleteStorageUnit(unit3);
		if(controller.canDeleteStorageUnit(unit4))
			controller.deleteStorageUnit(unit4);
		if(controller.canDeleteStorageUnit(unit5))
			controller.deleteStorageUnit(unit5);	
	}
	
	@Test
	public void addStorageUnitTest(){
		assertTrue(controller.canAddStorageUnit(unit));
		controller.addStorageUnit(unit);
		assertFalse(controller.canAddStorageUnit(unit));
		
		assertTrue(controller.canAddStorageUnit(unit2));
		controller.addStorageUnit(unit2);
		assertFalse(controller.canAddStorageUnit(unit2));
		
		assertTrue(controller.canAddStorageUnit(unit3));
		controller.addStorageUnit(unit3);
		assertFalse(controller.canAddStorageUnit(unit3));
		
		assertTrue(controller.canAddStorageUnit(unit4));
		controller.addStorageUnit(unit4);
		assertFalse(controller.canAddStorageUnit(unit4));
		
		assertFalse(controller.canAddStorageUnit(unit));
		try{
			controller.addStorageUnit(unit);
			assertFalse(true);
		}catch(IllegalArgumentException e){
			assertFalse(false);
		}
		assertFalse(controller.canAddStorageUnit(unit));
		
	}
	
	@Test
	public void deleteStorageUnitTest(){
		addUnits();
		setupTree();
		
		assertFalse(controller.canDeleteStorageUnit(unit));
		unit.removeItem(item);
		unit.removeItem(item2);
		group2.removeItem(item3);
		group2.removeItem(item4);
		assertTrue(controller.canDeleteStorageUnit(unit));
		
		controller.deleteStorageUnit(unit);
		
		assertTrue(controller.canAddStorageUnit(unit));
	}
	
	@Test
	public void editStorageUnitTest(){
		addUnits();
		setupTree();
		
		StorageUnit unitTest = new StorageUnit("Unit 2");
		assertTrue(controller.canEditStorageUnit(unitTest, unit2));
		assertFalse(controller.canEditStorageUnit(unitTest, unit3));
		
		unitTest = new StorageUnit("This is a StorageUnit");
		assertTrue(controller.canEditStorageUnit(unitTest, unit2));
		controller.editStorageUnit(unitTest, unit2);
		assertEquals(unit2.getName(), "This is a StorageUnit");
		
		assertTrue(controller.getAllStorageUnits().contains(unit2));
		assertFalse(controller.getStorageUnitByName(unitTest.getName()) == unitTest);
	}
	
	public void addUnits(){
		controller.addStorageUnit(unit);
		controller.addStorageUnit(unit2);
		controller.addStorageUnit(unit3);
		controller.addStorageUnit(unit4);
	}
	
	private void setupTree(){
		unit.addProductGroup(group1);
		unit.addProductGroup(group2);
				
		unit.addItem(item);		
		unit.addItem(item2);
		
		group2.addProduct(product2);
		unit.setContainerByProduct(product2, group2);
		
		unit.addItem(item3);
		unit.addItem(item4);
		
	}
}
