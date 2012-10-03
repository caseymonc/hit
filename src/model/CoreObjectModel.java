/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import model.entities.*;
import model.managers.*;
import model.controllers.*;
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
	
	//private ItemController itemController;
	//private ProductController productController;
	//private StorageUnitController storageUnitController;
	//private ProductGroupController productGroupController;

	private ProductManager productManager;
	private ProductGroupManager productGroupManager;
	private StorageUnitManager storageUnitManager;
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
		storageUnitManager = new StorageUnitManager();
		itemManager = new ItemManager();
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public ProductGroupManager getProductGroupManager() {
		return productGroupManager;
	}

	public StorageUnitManager getStorageUnitManager() {
		return storageUnitManager;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}
	
	public ProductController getProductController() {
		return new ProductController();
	}

	public ProductGroupController getProductGroupController() {
		return ProductGroupController.getInstance();
	}

	public StorageUnitController getStorageUnitController() {
		return StorageUnitController.getInstance();
	}

	public ItemController getItemController() {
		return new ItemController();
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
