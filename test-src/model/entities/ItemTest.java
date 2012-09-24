/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

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
	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
}
