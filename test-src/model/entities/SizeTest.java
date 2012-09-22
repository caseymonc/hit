package model.entities;

import static org.junit.Assert.*;
import org.junit.Test;

public class SizeTest {

	@Test
	public void testValidSize(){
		try{
			Size size = new Size(Unit.count, -1);
			assertFalse(true);
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
		
		try{
			Size size = new Size(Unit.count, 5.3f);
			assertFalse(true);
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
		
		Size size = new Size(Unit.count, 4);
		try{
			size.setSize(4.5f);
			assertFalse(true);
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
		
		try{
			size.setSize(-5);
			assertFalse(true);
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
	}
}
