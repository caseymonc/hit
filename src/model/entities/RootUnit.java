/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entities;

import model.entities.ProductContainer;
import model.entities.Item;
import model.entities.Product;
import java.util.List;

/** RootUnit
 * RootUnit is a special ProductContainer that is the root of the tree 
 * structure that contains all other ProductContainers, Products, and 
 * Items in HIT. Only one instance of RootUnit can exist in the entire
 * system.
 *
 * @author davidpatty
 */
public class RootUnit extends ProductContainer implements PersistentItem{
     
	/**
	 *  Constructor
	 */
     private RootUnit() {
	
	}
	
	/** Stores the instance of RootUnit.
	 * If _instance is set, the Instance() method will not be called.
	 * This prevents the creation of multiple RootUnits.
	 */
	private static RootUnit _instance;
	
	/** Method that is called instead of the Constructor.
	 * Calls the Constructor to build a new RootUnit only if _instance 
	 * is not set.
	 * 
	 * @return the _instance of the RootUnit
	 */
	public static RootUnit Instance() {
		
		if(RootUnit._instance == null) {
			_instance = new RootUnit();
		}
		
		return RootUnit._instance;		
	}

	@Override
	public void addItem(Item item) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void removeItem(Item item) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void addProduct(Product product) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void removeProduct(Product product) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public boolean canAddProductContainer(ProductContainer productContainer) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void addProductContainer(ProductContainer productContainer) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void removeProductContainer(ProductContainer productContainer) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Item getItemByBarCode(BarCode barcode) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Product getItemByName(String name) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<Item> getAllItems() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<Product> getAllProducts() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<ProductContainer> getAllProductContainers() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ProductContainer getProductContainerByName(String name) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getName() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void setName(String name) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
     
}
