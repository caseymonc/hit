/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.directors;

import common.util.DateUtils;
import gui.reports.Builder;
import gui.reports.Row;
import gui.reports.Table;
import gui.reports.TestBuilder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import model.BarCodeGenerator;
import model.CoreObjectModel;
import model.entities.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author dmathis
 */
public class ProductStatsDirectorTest {
	
	private static CoreObjectModel COM;
	private static BarCodeGenerator bcGenerator;
	private static StorageUnit unit1;
	
	public ProductStatsDirectorTest() {
		
	}
	
	@BeforeClass
	public static void setUpClass() {	
		COM = CoreObjectModel.getInstance();
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
		bcGenerator = BarCodeGenerator.getInstance();
		// create a Product Container
		unit1 = new StorageUnit("Unit 1");
		COM.getStorageUnitController().addStorageUnit(unit1);
	}
	
	@After
	public void tearDown() {
		COM.resetInstance();
	}

	@Test
	public void testTableWithNoProducts() {
		
		/***************************************************************************************/
		/*                                                                                     */
		/*   Test a table products are added                                                   */
		/*                                                                                     */
		/***************************************************************************************/

		ArrayList<Object> report = getReport(new Date(), 1);
		assertFalse(report.isEmpty());  // report is not empty
		assertTrue(report.get(0) instanceof String);  // report should have a title
		assertTrue(report.get(1) instanceof Table);  // report should have a table
		assertTrue(report.size() == 2);  // report should only have a title and a table
		
		Table table = (Table)report.get(1);
		assertTrue(table.size() == 1);  // report should have a row for the headings only
		
		/***************************************************************************************/
		/*                                                                                     */
		/*   Test a table after all products are removed                                       */
		/*                                                                                     */
		/***************************************************************************************/
		
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.count, 1);
		Product product = new Product("Product 1", barcode, 1, 1, size);
		addProduct(product);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.MARCH, 1, 0, 0, 0);
		Date endDate = calendar.getTime(); // end of report period
		
		removeProduct(product);
		
		report = getReport(endDate, 1);
		assertFalse(report.isEmpty());  // report is not empty
		assertTrue(report.get(0) instanceof String);  // report should have a title
		assertTrue(report.get(1) instanceof Table);  // report should have a table
		assertTrue(report.size() == 2);  // report should only have a title and a table
		
		table = (Table)report.get(1);
		assertTrue(table.size() == 1);  // report should have a row for the headings only
	}
	
	@Test
	public void testProductWithNoItems() {
		// add product
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.pounds, (float)1.2);
		addProduct(new Product("Product", barcode, 1, 1, size));
		
		// get report
		ArrayList<Object> report = getReport(new Date(), 1);
		
		// report is not empty
		assertFalse(report.isEmpty());
		
		// report should have a title
		assertTrue(report.get(0) instanceof String);
		
		// report should have a table
		assertTrue(report.get(1) instanceof Table);
		
		Table table = (Table)report.get(1);
	
		// report should have a row for the headings and for the product.
		assertTrue(table.size() == 2);
		
		Row row = table.getRow(1);
		
		// check all columns in the product row
		assertTrue(row.getCell(0).toString().equals("Product"));				// Description
		assertTrue(row.getCell(1).toString().equals("1111"));					// BarCode
		assertTrue(row.getCell(2).toString().equals("1.2 Pounds"));				// Size
		assertTrue(row.getCell(3).toString().equals("1"));						// 3 Month Supply
		assertTrue(row.getCell(4).toString().equals("0/0"));					// Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("0/0"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/0"));					// Used/Added Supply
		assertTrue(row.getCell(7).toString().equals("1"));						// Shelf Life
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("0 days/0 days"));			// Avg/Max Cur Age
	}
	
	@Test
	public void testProductWithOneItem() {
		// add product
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.pounds, (float)1.2);
		Product product = new Product("Product", barcode, 1, 1, size);
		addProduct(product);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, Calendar.NOVEMBER, 4, 1, 0, 0);	// let the item be in system for one day
		addItem(calendar.getTime(), null, product);
		
		// get report
		calendar.set(2011, Calendar.NOVEMBER, 5, 1, 0, 0);
		ArrayList<Object> report = getReport(calendar.getTime(), 1);
		Table table = (Table)report.get(1);
		Row row = table.getRow(1);
		
		// check all columns in the product row
		assertTrue(row.getCell(0).toString().equals("Product"));				// Description
		assertTrue(row.getCell(1).toString().equals("1111"));					// BarCode
		assertTrue(row.getCell(2).toString().equals("1.2 Pounds"));				// Size
		assertTrue(row.getCell(3).toString().equals("1"));						// 3 Month Supply
		assertTrue(row.getCell(4).toString().equals("1/1"));					// Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("1/1"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/1"));					// Used/Added Supply
		assertTrue(row.getCell(7).toString().equals("1"));						// Shelf Life
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("1 days/1 days"));			// Avg/Max Cur Age
	}
	
	@Test
	public void testProductWithManyItems() {
		// add product
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.pounds, (float)1.2);
		Product product = new Product("Product", barcode, 1, 1, size);
		addProduct(product);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.JANUARY, 31, 0, 0, 0);
		Date endDate = calendar.getTime();
		calendar.add(Calendar.DATE, -30);
		Date tmpDate = calendar.getTime();

		// add 10 items per day for 30 days
		while(tmpDate.before(endDate)){
			for(int i = 0; i < 10; i++){
				addItem(tmpDate, null, product);
			}
			calendar.add(Calendar.DATE, 1);
			tmpDate = calendar.getTime();
		}
		
		// get report
		ArrayList<Object> report = getReport(endDate, 1);
		Table table = (Table)report.get(1);
		Row row = table.getRow(1);
		
		// check all columns in the product row
		assertTrue(row.getCell(0).toString().equals("Product"));				// Description
		assertTrue(row.getCell(1).toString().equals("1111"));					// BarCode
		assertTrue(row.getCell(2).toString().equals("1.2 Pounds"));				// Size
		assertTrue(row.getCell(3).toString().equals("1"));						// 3 Month Supply
		assertTrue(row.getCell(4).toString().equals("300/159.7"));				// Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("10/300"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/300"));					// Used/Added Supply
		assertTrue(row.getCell(7).toString().equals("1"));						// Shelf Life
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("15.5 days/30 days"));		// Avg/Max Cur Age
	}
	
	@Test
	public void testStandardTimeToDaylightSavings(){		
		/************************************************************************************/
		/*                                                                                  */
		/*  Time change (March 13, 2011 2:00am) occurs in the middle of the report period   */
		/*                                                                                  */
		/************************************************************************************/
		
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.pounds, (float)1.2);
		Product product = new Product("Product 1", barcode, 1, 1, size);
		addProduct(product);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, Calendar.APRIL, 1, 0, 0, 0);
		Date endDate = calendar.getTime(); // end of report period
		
		calendar.set(2011, Calendar.MARCH, 13, 2, 0, 0); // exact moment of time change
		Date date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.MARCH, 13, 1, 59, 0); // before time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.MARCH, 13, 2, 1, 0); // after time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		ArrayList<Object> report = getReport(endDate, 1);
		Table table = (Table)report.get(1);
		Row row = table.getRow(1);
		
		// check all columns in the product row
		assertTrue(row.getCell(0).toString().equals("Product 1"));				// Description
		assertTrue(row.getCell(1).toString().equals("1111"));					// BarCode
		assertTrue(row.getCell(2).toString().equals("1.2 Pounds"));				// Size
		assertTrue(row.getCell(3).toString().equals("1"));						// 3 Month Supply
		assertTrue(row.getCell(4).toString().equals("3/3"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("3/3"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/3"));					// Used/Added Supply
		assertTrue(row.getCell(7).toString().equals("1"));						// Shelf Life
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("19 days/19 days"));		// Avg/Max Cur Age
		
		
		/**************************************************************************************/
		/*                                                                                    */
		/*  Time change (March 13, 2011 2:00am) occurs at the beginning of the report period  */
		/*                                                                                    */
		/**************************************************************************************/
		
		barcode = new BarCode("2222");
		size = new Size(Unit.pounds, (float)1.2);
		product = new Product("Product 2", barcode, 1, 1, size);
		addProduct(product);
		
		calendar.set(2011, Calendar.APRIL, 13, 0, 0, 0);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2011, Calendar.MARCH, 13, 2, 0, 0); // exact moment of time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.MARCH, 13, 1, 59, 0); // before time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.MARCH, 13, 2, 1, 0); // after time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(2);
		
		// check all columns in the product row
		assertTrue(row.getCell(0).toString().equals("Product 2"));				// Description
		assertTrue(row.getCell(1).toString().equals("2222"));					// BarCode
		assertTrue(row.getCell(2).toString().equals("1.2 Pounds"));				// Size
		assertTrue(row.getCell(3).toString().equals("1"));						// 3 Month Supply
		assertTrue(row.getCell(4).toString().equals("3/3"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("3/3"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/3"));					// Used/Added Supply
		assertTrue(row.getCell(7).toString().equals("1"));						// Shelf Life
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("31 days/31 days"));		// Avg/Max Cur Age
		
		/**************************************************************************************/
		/*                                                                                    */
		/*  Time change (March 13, 2011 2:00am) occurs at the end of the report period        */
		/*                                                                                    */
		/**************************************************************************************/
		
		barcode = new BarCode("3333");
		size = new Size(Unit.pounds, (float)1.2);
		product = new Product("Product 3", barcode, 1, 1, size);
		addProduct(product);
		
		calendar.set(2011, Calendar.MARCH, 13, 2, 0, 0);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2011, Calendar.MARCH, 13, 2, 0, 0); // exact moment of time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.MARCH, 13, 1, 59, 0); // before time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.MARCH, 13, 2, 1, 0); // after time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(3);
		
		// check all columns in the product row
		assertTrue(row.getCell(0).toString().equals("Product 3"));				// Description
		assertTrue(row.getCell(1).toString().equals("3333"));					// BarCode
		assertTrue(row.getCell(2).toString().equals("1.2 Pounds"));				// Size
		assertTrue(row.getCell(3).toString().equals("1"));						// 3 Month Supply
		assertTrue(row.getCell(4).toString().equals("3/3"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("3/3"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/3"));					// Used/Added Supply
		assertTrue(row.getCell(7).toString().equals("1"));						// Shelf Life
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("0 days/0 days"));			// Avg/Max Cur Age
	}
	
	@Test
	public void testDaylightSavingsToStandardTime(){		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Time change (November 6, 2011, 2:00am) occurs in the middle of the report period   */
		/*                                                                                     */
		/***************************************************************************************/
		
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.pounds, (float)1.2);
		Product product = new Product("Product 1", barcode, 1, 1, size);
		addProduct(product);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, Calendar.DECEMBER, 1, 0, 0, 0);
		Date endDate = calendar.getTime(); // end of report period
		
		calendar.set(2011, Calendar.NOVEMBER, 6, 2, 0, 0); // exact moment of time change
		Date date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.NOVEMBER, 6, 1, 59, 0); // before time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.NOVEMBER, 6, 2, 1, 0); // after time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		ArrayList<Object> report = getReport(endDate, 1);
		Table table = (Table)report.get(1);
		Row row = table.getRow(1);
		
		// check all columns in the product row
		assertTrue(row.getCell(0).toString().equals("Product 1"));				// Description
		assertTrue(row.getCell(1).toString().equals("1111"));					// BarCode
		assertTrue(row.getCell(2).toString().equals("1.2 Pounds"));				// Size
		assertTrue(row.getCell(3).toString().equals("1"));						// 3 Month Supply
		assertTrue(row.getCell(4).toString().equals("3/3"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("3/3"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/3"));					// Used/Added Supply
		assertTrue(row.getCell(7).toString().equals("1"));						// Shelf Life
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("25 days/25 days"));		// Avg/Max Cur Age
		
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Time change (November 6, 2011, 2:00am) occurs at beginning of the report period    */
		/*                                                                                     */
		/***************************************************************************************/
		
		barcode = new BarCode("2222");
		size = new Size(Unit.pounds, (float)1.2);
		product = new Product("Product 2", barcode, 1, 1, size);
		addProduct(product);
		
		calendar.set(2011, Calendar.DECEMBER, 6, 2, 0, 0);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2011, Calendar.NOVEMBER, 6, 2, 0, 0); // exact moment of time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.NOVEMBER, 6, 1, 59, 0); // before time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.NOVEMBER, 6, 2, 1, 0); // after time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(2);
		
		// check all columns in the product row
		assertTrue(row.getCell(0).toString().equals("Product 2"));				// Description
		assertTrue(row.getCell(1).toString().equals("2222"));					// BarCode
		assertTrue(row.getCell(2).toString().equals("1.2 Pounds"));				// Size
		assertTrue(row.getCell(3).toString().equals("1"));						// 3 Month Supply
		assertTrue(row.getCell(4).toString().equals("3/3"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("3/3"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/2"));					// Used/Added Supply
		assertTrue(row.getCell(7).toString().equals("1"));						// Shelf Life
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("30 days/30 days"));		// Avg/Max Cur Age
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Time change (November 6, 2011, 2:00am) occurs at the end of the report period      */
		/*                                                                                     */
		/***************************************************************************************/
		
		barcode = new BarCode("3333");
		size = new Size(Unit.pounds, (float)1.2);
		product = new Product("Product 3", barcode, 1, 1, size);
		addProduct(product);
		
		calendar.set(2011, Calendar.NOVEMBER, 6, 2, 0, 0);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2011, Calendar.NOVEMBER, 6, 2, 0, 0); // exact moment of time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.NOVEMBER, 6, 1, 59, 0); // before time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		calendar.set(2011, Calendar.NOVEMBER, 6, 2, 1, 0); // after time change
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(3);
		
		// check all columns in the product row
		assertTrue(row.getCell(0).toString().equals("Product 3"));				// Description
		assertTrue(row.getCell(1).toString().equals("3333"));					// BarCode
		assertTrue(row.getCell(2).toString().equals("1.2 Pounds"));				// Size
		assertTrue(row.getCell(3).toString().equals("1"));						// 3 Month Supply
		assertTrue(row.getCell(4).toString().equals("3/3"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("3/3"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/3"));					// Used/Added Supply
		assertTrue(row.getCell(7).toString().equals("1"));						// Shelf Life
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("0 days/0 days"));			// Avg/Max Cur Age
	}

	@Test
	public void testAddProductDuringReportPeriod(){
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Product added during the middle of the report period                               */
		/*                                                                                     */
		/***************************************************************************************/
		
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.count, 1);
		Product product = new Product("Product 1", barcode, 1, 1, size);
		addProduct(product);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.FEBRUARY, 1, 0, 0, 0);
		Date endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.JANUARY, 10, 2, 0, 0);
		Date date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		ArrayList<Object> report = getReport(endDate, 1);
		Table table = (Table)report.get(1);
		Row row = table.getRow(1);
		
		// check all columns in the product row
		assertTrue(row.getCell(2).toString().equals("1 Count"));				// Size
		assertTrue(row.getCell(4).toString().equals("1/1"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("1/1"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/1"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("22 days/22 days"));		// Avg/Max Cur Age
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Product added at the beginning of the report period                                */
		/*                                                                                     */
		/***************************************************************************************/
		
		barcode = new BarCode("2222");
		product = new Product("Product 2", barcode, 1, 1, size);
		addProduct(product);
		
		calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.FEBRUARY, 1, 0, 0, 0);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.JANUARY, 1, 0, 0, 0);
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(2);
		
		// check all columns in the product row
		assertTrue(row.getCell(4).toString().equals("1/1"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("1/1"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/1"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("31 days/31 days"));		// Avg/Max Cur Age
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Product added at the end of the report period                                      */
		/*                                                                                     */
		/***************************************************************************************/
		
		barcode = new BarCode("333333");
		product = new Product("Product 3", barcode, 1, 1, size);
		addProduct(product);
		
		calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.FEBRUARY, 1, 0, 0, 0);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.JANUARY, 31, 23, 59, 59);
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(3);
		
		// check all columns in the product row
		assertTrue(row.getCell(4).toString().equals("1/1"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("1/1"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/1"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("1 days/1 days"));			// Avg/Max Cur Age
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Product added at the end of the report period                                      */
		/*                                                                                     */
		/***************************************************************************************/
		
		barcode = new BarCode("4444");
		product = new Product("Product 4", barcode, 1, 1, size);
		addProduct(product);
		
		calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.FEBRUARY, 1, 0, 0, 1);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.FEBRUARY, 1, 0, 0, 0);
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(4);
		
		// check all columns in the product row
		assertTrue(row.getCell(4).toString().equals("1/1"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("1/1"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/1"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("0 days/0 days"));			// Avg/Max Cur Age
	}
	
	@Test
	public void testAddProductBeforeReportPeriod(){
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Product added one day before the report period                                     */
		/*                                                                                     */
		/***************************************************************************************/
		
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.count, 1);
		Product product = new Product("Product 1", barcode, 1, 1, size);
		addProduct(product);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.MARCH, 1, 0, 0, 0);
		Date endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.JANUARY, 31, 23, 59, 59);
		Date date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		ArrayList<Object> report = getReport(endDate, 1);
		Table table = (Table)report.get(1);
		Row row = table.getRow(1);
		
		// check all columns in the product row
		assertTrue(row.getCell(2).toString().equals("1 Count"));				// Size
		assertTrue(row.getCell(4).toString().equals("1/1"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("1/1"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/0"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("29 days/29 days"));		// Avg/Max Cur Age
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Product added one second before the report period                                  */
		/*                                                                                     */
		/***************************************************************************************/
		
		barcode = new BarCode("2222");
		product = new Product("Product 2", barcode, 1, 1, size);
		addProduct(product);
		
		calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.MARCH, 1, 0, 0, 1);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.FEBRUARY, 1, 0, 0, 0);
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(2);
		
		// check all columns in the product row
		assertTrue(row.getCell(4).toString().equals("1/1"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("1/1"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/0"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("29 days/29 days"));		// Avg/Max Cur Age
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Product added many days before the report period                                   */
		/*                                                                                     */
		/***************************************************************************************/
		
		barcode = new BarCode("3333");
		product = new Product("Product 3", barcode, 1, 1, size);
		addProduct(product);
		
		calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.MARCH, 1, 0, 0, 1);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.JANUARY, 1, 0, 0, 0);
		date = calendar.getTime();
		addItem(date, null, product);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(3);
		
		// check all columns in the product row
		assertTrue(row.getCell(4).toString().equals("1/1"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("1/1"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/0"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("29 days/29 days"));		// Avg/Max Cur Age
	}
	
	@Test
	public void testRemoveItems(){
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Product had item added and removed during the report perior                        */
		/*                                                                                     */
		/***************************************************************************************/
		
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.count, 1);
		Product product = new Product("Product 1", barcode, 1, 1, size);
		addProduct(product);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.MARCH, 1, 0, 0, 0);
		Date endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.FEBRUARY, 10, 0, 0, 0);
		Date entrydate = calendar.getTime();
		Item item1 = addItem(entrydate, null, product);
		
		calendar.set(2012, Calendar.FEBRUARY, 11, 0, 0, 0);
		Date exitDate = calendar.getTime();
		removeItem(item1, exitDate);
		
		// get report
		ArrayList<Object> report = getReport(endDate, 1);
		Table table = (Table)report.get(1);
		Row row = table.getRow(1);
		
		// check all columns in the product row
		assertTrue(row.getCell(4).toString().equals("0/0.1"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("0/1"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("1/1"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("1 days/1 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("0 days/0 days"));			// Avg/Max Cur Age
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Product had item removed during the report period                                  */
		/*                                                                                     */
		/***************************************************************************************/
		
		barcode = new BarCode("2222");
		product = new Product("Product 2", barcode, 1, 1, size);
		addProduct(product);
		
		calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.MARCH, 1, 0, 0, 0);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.JANUARY, 10, 0, 0, 0);
		entrydate = calendar.getTime();
		item1 = addItem(entrydate, null, product);
		
		calendar.set(2012, Calendar.FEBRUARY, 2, 0, 0, 0);
		exitDate = calendar.getTime();
		removeItem(item1, exitDate);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(2);
		
		// check all columns in the product row
		assertTrue(row.getCell(4).toString().equals("0/0.1"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("0/1"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("1/0"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("1 days/1 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("0 days/0 days"));			// Avg/Max Cur Age
		
		/***************************************************************************************/
		/*                                                                                     */
		/*  Product had item added and removed before the report period                        */
		/*                                                                                     */
		/***************************************************************************************/
		
		barcode = new BarCode("3333");
		product = new Product("Product 3", barcode, 1, 1, size);
		addProduct(product);
		
		calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.MARCH, 1, 0, 0, 0);
		endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.JANUARY, 10, 0, 0, 0);
		entrydate = calendar.getTime();
		item1 = addItem(entrydate, null, product);
		
		calendar.set(2012, Calendar.JANUARY, 11, 0, 0, 0);
		exitDate = calendar.getTime();
		removeItem(item1, exitDate);
		
		// get report
		report = getReport(endDate, 1);
		table = (Table)report.get(1);
		row = table.getRow(3);
		
		// check all columns in the product row
		assertTrue(row.getCell(4).toString().equals("0/0"));				    // Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("0/0"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/0"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("0 days/0 days"));			// Avg/Max Cur Age
	}
	
	@Test
	public void testLargeAmountsOfItems() {
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.count, 1);
		Product product = new Product("Product 1", barcode, 1, 1, size);
		addProduct(product);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.MARCH, 1, 0, 0, 0);
		Date endDate = calendar.getTime(); // end of report period
		
		calendar.set(2012, Calendar.FEBRUARY, 1, 0, 0, 0);
		Date entrydate = calendar.getTime();
		
		for(int i = 0; i < 10000; i++){
			addItem(entrydate, null, product);
		}
		
		// get report
		ArrayList<Object> report = getReport(endDate, 1);
		Table table = (Table)report.get(1);
		Row row = table.getRow(1);
		
		// check all columns in the product row
		assertTrue(row.getCell(4).toString().equals("10000/10000"));			// Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("10000/10000"));			// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/10000"));				// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("29 days/29 days"));		// Avg/Max Cur Age
	}
	
	@Test
	public void testReportPeriodLengths(){
		BarCode barcode = new BarCode("1111");
		Size size = new Size(Unit.count, 1);
		Product product = new Product("Product 1", barcode, 1, 1, size);
		addProduct(product);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(2012, Calendar.MARCH, 1, 0, 0, 0);
		Date endDate = calendar.getTime(); // end of report period
		
		calendar.add(Calendar.MONTH, -100);
		Date entrydate = calendar.getTime();
		
		for(int i = 0; i < 10; i++){
			addItem(entrydate, null, product);
		}
		
		// get report
		ArrayList<Object> report = getReport(endDate, 100);
		Table table = (Table)report.get(1);
		Row row = table.getRow(1);
		
		// check all columns in the product row
		assertTrue(row.getCell(4).toString().equals("10/10"));					// Cur/Avg Supply
		assertTrue(row.getCell(5).toString().equals("10/10"));					// Min/Max Supply
		assertTrue(row.getCell(6).toString().equals("0/10"));					// Used/Added Supply
		assertTrue(row.getCell(8).toString().equals("0 days/0 days"));			// Avg/Max Used Age
		assertTrue(row.getCell(9).toString().equals("3043 days/3043 days"));	// Avg/Max Cur Age
	}
	
	private ArrayList<Object> getReport(Date endDate, int months) {
		ArrayList<Object> report = new ArrayList();
		Builder builder = new TestBuilder(report);
		ProductStatsDirector director = new ProductStatsDirector(builder);
		director.createReport(endDate, months);
		return report;
	}
	
	private Item addItem(Date entryDate, Date exitDate, Product product){
		BarCode barCode = bcGenerator.generateBarCode();
		Item item = new Item(barCode, entryDate, exitDate, product, unit1);
		COM.getItemController().addItem(item, unit1);
		return item;
	}
	
	private void addProduct(Product product){
		COM.getProductController().addProductToContainer(product, unit1);
	}
	
	private void removeProduct(Product product){
		COM.getProductController().removeProductFromContainer(product, unit1);
	}

	private void removeItem(Item item, Date exitDate) {
		COM.getItemController().removeItem(item, exitDate);
	}
}
