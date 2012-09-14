/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author davidpatty
 */
public class RootUnit extends ProductContainer {
     
	/**
	 *  singleton constructor
	 */
     private RootUnit() {
	
	}
	
	/**
	 * 
	 */
	private static RootUnit _instance;
	
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
