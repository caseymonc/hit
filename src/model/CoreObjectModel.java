/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.entities.BarCode;
import model.entities.Item;
import model.entities.Product;
import model.managers.*;
import model.persistence.PersistentItem;

/** CoreObjectModel
 * This class stores the rootUnit which is the root of a tree 
 * structure that points to every object in the HIT system. It
 * also contains a Map of all Products and a Map of all Items.
 * These maps make is easier to iterate through all products
 * and items.
 *
 * @author davidpatty
 */
public class CoreObjectModel implements PersistentItem{
	
	private static CoreObjectModel _instance;
	private ProductManager productManager;
	private ProductGroupManager productGroupManager;
	private StorageUnitManager storageManager;
	private ItemManager itemManager;
  
	public static CoreObjectModel getInstance()
	{
	   if(_instance == null)
	   {
		   _instance = new CoreObjectModel();
	   }
	   return _instance;
	}
	private CoreObjectModel()
	{
		productManager = new ProductManager();
		productGroupManager = new ProductGroupManager();
		storageManager = new StorageUnitManager();
		itemManager = new ItemManager();
	}
	
		
	/** finds and returns the product associated with the unique barcode
	 * 
	 * @param barcode unique for each product
	 * @return Product
	 */
	public Product findProductByBarCode(BarCode barcode) 
	{
			return productManager.getProductByBarCode(barcode);
	}

	/** finds and returns the Item associated with the unique barcode
	 * 
	 * @param barcode unique for each item
	 * @return Item
	 */
	public Item findItemByBarCode(BarCode barcode) {
			return itemManager.getItemByBarCode(barcode);
	}


}
