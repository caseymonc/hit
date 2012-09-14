/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author davidpatty
 */
public class CoreObjectModel {
	
	/**
	 *  This is the instance of the Root of the product tree
	 */
	private RootUnit rootUnit;
	
	/** 
	 *  Holds all of the products in the program
	 */
	private ProductMap allTheProducts;
	
	/** 
	 *  Holds all of the items in the program
	 */
	private ItemMap allTheItems;

	/** finds and returns the product associated with the unique barcode
	 * 
	 * @param barcode unique for each product
	 * @return Product
	 */
	public Product findProductByBarCode(BarCode barcode) {
		return null;
	}
	
	/** finds and returns the Item associated with the unique barcode
	 * 
	 * @param barcode unique for each item
	 * @return Item
	 */
	public Item findItemByBarCode(BarCode barcode) {
		return null;
	}


}
