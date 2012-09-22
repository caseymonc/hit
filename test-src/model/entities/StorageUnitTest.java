package model.entities;


import static org.junit.Assert.*;

import model.managers.StorageUnitManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageUnitTest {

	private static StorageUnitManager manager;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		manager = new StorageUnitManager();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testConstruct(){
		StorageUnit unit = new StorageUnit("Unit");
		assertEquals(unit.getName(), "Unit");
		assertEquals(unit.getContainer(), null);
		assertEquals(unit.getStorageUnit(), unit);
	}

	@Test
	public void testAddProductGroups(){
		StorageUnit unit = new StorageUnit("Unit");
		
		try{
			unit.setStorageUnit(unit);
			assertTrue(false);
		}catch(UnsupportedOperationException e){
			assertTrue(true);
		}
		
		ProductGroup group1 = new ProductGroup("Group 1", unit, new Size(Unit.gallons, 5));
		ProductGroup group2 = new ProductGroup("Group 2", unit, new Size(Unit.gallons, 5));
		ProductGroup group3 = new ProductGroup("Group 3", unit, new Size(Unit.gallons, 5));
		ProductGroup group4 = new ProductGroup("Group 4", unit, new Size(Unit.gallons, 5));
		ProductGroup group5 = new ProductGroup("Group 1", unit, new Size(Unit.gallons, 5));
		
		unit.addProductGroup(group1);
		unit.addProductGroup(group2);
		unit.addProductGroup(group3);
		unit.addProductGroup(group4);
		try{
			unit.addProductGroup(group5);
			assertTrue(false);
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
		
		assertTrue(unit.getProductGroupByName("Group 1") == group1);
		assertTrue(unit.getProductGroupByName("Group 2") == group2);
		assertTrue(unit.getProductGroupByName("Group 3") == group3);
		assertTrue(unit.getProductGroupByName("Group 4") == group4);
		assertFalse(group5 == unit.getProductGroupByName("Group 1"));
		assertFalse(group1 == unit.getProductGroupByName("Group 4"));
		assertFalse(group2 == unit.getProductGroupByName("Group 3"));
		assertFalse(group3 == unit.getProductGroupByName("Group 2"));
		assertFalse(group4 == unit.getProductGroupByName("Group 1"));
		
		assertTrue(unit.canAddProductGroup(new ProductGroup("Group 5", unit, new Size(Unit.gallons, 5))));
		assertFalse(unit.canAddProductGroup(new ProductGroup("Group 2", unit, new Size(Unit.gallons, 5))));
		
	}
	
	@Test
	public void testAddProduct(){
		StorageUnit unit1 = new StorageUnit("Unit 1");
	}
}
