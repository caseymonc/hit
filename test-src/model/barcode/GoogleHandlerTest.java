/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.barcode;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author davidpatty
 */
public class GoogleHandlerTest {

	private GoogleHandler handler;
	
	public GoogleHandlerTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {

	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		handler = new GoogleHandler();		
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of lookup method, of class GoogleHandler.
	 */
	@Test
	public void testLookup() {
		String barcode1 = "8888425350403";
		String description1 = "";
		
		String barcode2 = "5060185162370";
		String description2 = "";
		
		System.out.println("lookup");

		String result = "";

		result = handler.lookup(barcode1);
		System.out.println("e:" +description1+"  r:"+result);
		
		result = handler.lookup(barcode2);
		System.out.println("e:" +description2+"  r:"+result);
		assertEquals(description2, result);
	}
}
