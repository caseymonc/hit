package model.entities;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductGroupTest {
	@Test
	public void testThreeMonthSupply(){
		StorageUnit unit = new StorageUnit("Unit");
		ProductGroup group1 = new ProductGroup("Group 1", unit, new Size(Unit.gallons, 6));
		ProductGroup group2 = new ProductGroup("Group 2", unit, new Size(Unit.count, 5));
		ProductGroup group3 = new ProductGroup("Group 3", unit, new Size(Unit.kilograms, 5.6f));
		ProductGroup group4 = new ProductGroup("Group 4", unit, new Size(Unit.ounces, 3.4f));
	
		assertTrue(group1.getThreeMonthSupply().getUnits() == Unit.gallons);
		assertTrue(group1.getThreeMonthSupply().getSize() == 6);
		
		assertTrue(group2.getThreeMonthSupply().getUnits() == Unit.count);
		assertTrue(group2.getThreeMonthSupply().getSize() == 5);
		
		assertTrue(group3.getThreeMonthSupply().getUnits() == Unit.kilograms);
		assertTrue(group3.getThreeMonthSupply().getSize() == 5.6f);
		
		assertTrue(group4.getThreeMonthSupply().getUnits() == Unit.ounces);
		assertTrue(group4.getThreeMonthSupply().getSize() == 3.4f);
	}
}
