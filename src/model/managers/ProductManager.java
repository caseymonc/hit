/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import model.entities.*;
import model.persistence.PersistentFactory;
import model.persistence.PersistentItem;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ProductDO;
import reports.visitors.ItemVisitor;
import reports.visitors.ProductVisitor;

/**
 *
 * @author davidpatty, davidmathis
 */
public class ProductManager implements PersistentItem {

	/**
	 * ID used for the persistent store. Do not delete.
	 */
	private static final long serialVersionUID = -3617796467364037057L;
	/**
	 * Map of products indexed by their BarCodes
	 */
	Map<BarCode, Product> productsByBarCode;
	/**
	 * Map of Item Collections indexed by their product
	 */
	Map<Product, Set<Item>> currentItemsByProduct;
	Map<Product, Set<Item>> allItemsByProduct;

	/**
	 * Constructor
	 *
	 * Creates a new ProductManager
	 */
	public ProductManager() {
		productsByBarCode = new HashMap();
		currentItemsByProduct = new HashMap();
		allItemsByProduct = new HashMap();
	}

	/**
	 * Determines if a product can be added to the Product Manager
	 *
	 * @param p - the product being added
	 *
	 * @return true if p can be added. Otherwise, return false.
	 */
	public boolean canAddProduct(Product p) {
		if (productsByBarCode.containsKey(p.getBarCode())) {
			return false;
		}

		return true;
	}

	/**
	 * Adds a product to the Product Manager
	 *
	 * @param p - the product being added
	 *
	 * @throws IllegalArgumentException
	 * @throws SQLException 
	 */
	public void addProduct(Product p) throws IllegalArgumentException, SQLException {
		assert (p != null);
		assert (canAddProduct(p));

		if (p == null || (canAddProduct(p) == false)) {
			throw new IllegalArgumentException("Not a valid Product");
		}
		DataObject pDO = p.getDataObject();
		PersistentFactory.getFactory().getProductDAO().create(pDO);
		p.setId(pDO.getId());
		productsByBarCode.put(p.getBarCode(), p);
		currentItemsByProduct.put(p, new HashSet<Item>());
		allItemsByProduct.put(p, new HashSet<Item>());
	}

	/**
	 * Checks to see if a product can be added to a container
	 *
	 * @param p - Product to be added
	 * @param c - The ProductContainer to which the product is being added.
	 */
	public boolean canAddProductToContainer(Product p, ProductContainer c) {

		if (p == null || c == null) {
			return false;
		}

		if (c.canAddProduct(p) == false) {
			return false;
		}

		return true;
	}

	/**
	 * Adds a product to a container
	 *
	 * @param p - Product to be added
	 * @param c - The ProductContainer to which the product is being added.
	 *
	 * @throws IllegalArgumentException
	 * @throws SQLException 
	 */
	public void addProductToContainer(Product p, ProductContainer c)
			throws IllegalArgumentException, SQLException {

		assert (p != null);
		assert (c != null);
		assert (canAddProductToContainer(p, c));

		if (p == null || c == null) {
			throw new IllegalArgumentException();
		}

		if (canAddProductToContainer(p, c) == false) {
			throw new IllegalArgumentException("Product cannot be added to container");
		}
		
		doAddProductToContainer(p, c);
		PersistentFactory.getFactory().getPpcDAO().create(p.getDataObject());
	}

	/**
	 * Checks to see if a product can be removed.
	 *
	 * @param p - The product being added to c
	 * @param c - The container that p is being removed from
	 * @return return true if p can be removed, else return true
	 */
	public boolean canRemoveProductFromContainer(Product p, ProductContainer c) {
		assert (p != null);
		assert (c != null);

		// does c have Items that point to p
		Collection<Item> items = c.getAllItems();

		for (Item item : items) {
			if (item.getProduct().equals(p)) {
				// their are items in c that point to p
				return false;
			}
		}

		return true;
	}

	/**
	 * Removes a product from a container.
	 *
	 * @param p - the product being removed from c
	 * @param c - the ProductContainer from which p is being removed
	 * @return return true if p can be removed, else return true
	 *
	 * @throws IllegalArgumentException if p or c are null
	 * @throws SQLException 
	 */
	public void removeProductFromContainer(Product p, ProductContainer c)
			throws IllegalArgumentException, SQLException {

		assert (p != null);
		assert (c != null);
		assert (canRemoveProductFromContainer(p, c));
		
		PersistentFactory.getFactory().getPpcDAO().delete((ProductDO)p.getDataObject(), c.getId());
		
		c.removeProduct(p);
		p.removeProductContainer(c);

		if (p.getContainers().isEmpty()) {
			assert (canRemoveProduct(p));
			removeProduct(p);
		}
	}

	/**
	

		
	/**
	 * Edits a product using the data stored in newProduct.
	 * 
	 * @param productBarCode - the product being edited
	 * @param newProduct - contains the data used to update the product being edited
	 * @throws IllegalArgumentException 
	 * @throws SQLException 
	 */
	public void editProduct(BarCode productBarCode, Product newProduct)
			throws IllegalArgumentException, SQLException {
		assert (productBarCode != null);
		assert (productBarCode.toString().equals(""));
		assert (newProduct != null);

		if (productBarCode == null || productBarCode.toString().equals("")
				|| productBarCode == null) {
			throw new IllegalArgumentException();
		}

		Product product = this.getProductByBarCode(productBarCode);

		product.setDescription(newProduct.getDescription());
		product.setShelfLife(newProduct.getShelfLife());
		product.setSize(newProduct.getSize());
		product.setThreeMonthSupply(newProduct.getThreeMonthSupply());
		
		PersistentFactory.getFactory().getProductDAO().update(product.getDataObject());
	}

	/**
	 * Determines if a product can be removed from the ProductManager
	 *
	 * @param p
	 * @return true if no items belong to p, otherwise return false.
	 */
	public boolean canRemoveProduct(Product p) {
		assert (p != null);

		Set<Item> items = currentItemsByProduct.get(p);

		for (Item item : items) {
			if (item.getExitDate() == null) {
				// active items still belong to p so it cannot be 
				// removed from the system
				return false;
			}
		}

		return true;
	}

	/**
	 * Removes a product from the ProductManager
	 *
	 * @param p - the product being removed
	 * @throws IllegalArgumentException
	 * @throws SQLException 
	 */
	public void removeProduct(Product p) throws IllegalArgumentException, SQLException {
		assert (p != null);
		assert (canRemoveProduct(p));

		if (p == null) {
			throw new IllegalArgumentException();
		}

		PersistentFactory.getFactory().getProductDAO().delete(p.getDataObject());
		
		productsByBarCode.remove(p.getBarCode());
		currentItemsByProduct.remove(p);
	}

	/**
	 * Adds an Item-Product relationship to itemsByProduct
	 *
	 * @param p
	 * @param i
	 *
	 * @throws IllegalArgumentException
	 */
	public void addItemToProduct(Product p, Item i)
			throws IllegalArgumentException {

		assert (p != null);
		assert (i != null);
		assert (currentItemsByProduct.containsKey(p));
		assert (allItemsByProduct.containsKey(p));

		if (p == null || i == null) {
			throw new IllegalArgumentException();
		}

		if (!currentItemsByProduct.containsKey(p) || !allItemsByProduct.containsKey(p)) {
			throw new IllegalArgumentException("The product doesn't exist");
		}
		
		doAddItemToProduct(p, i);
	}

	/**
	 * Removes an Item-Product relationship from itemsByProduct
	 *
	 * @param p
	 * @param i
	 *
	 * @throws IllegalArgumentException
	 */
	public void removeItemFromProduct(Product p, Item i) throws IllegalArgumentException {
		assert (p != null);
		assert (i != null);
		assert (currentItemsByProduct.containsKey(p));

		if (p == null || i == null) {
			throw new IllegalArgumentException();
		}

		if (currentItemsByProduct.containsKey(p)) {
			currentItemsByProduct.get(p).remove(i);

			// A product's creation date is equal to the earliest entry
			// date of any its items.
			//
			// This code may not be necessary
			if (p.getCreationDate().compareTo(i.getEntryDate()) > 0) {
				Collection<Item> items = currentItemsByProduct.get(p);

				Date min = new Date();
				for (Item item : items) {
					if (min.compareTo(item.getEntryDate()) > 0) {
						min = item.getEntryDate();
					}
				}

				p.setCreationDate(min);
			}
		} else {
			throw new IllegalArgumentException("The product doesn't exist");
		}
	}

	/**
	 * Determines if a product exists
	 *
	 * @param barcode - the BarCode of the product being searched for.
	 * @return true if the product is contained in the ProductMap. Otherwise, return false.
	 */
	public boolean productExists(BarCode barcode) {
		assert (barcode != null);

		return productsByBarCode.containsKey(barcode);
	}

	/**
	 * Get the product by its BarCode
	 *
	 * @param barcode - The BarCode of the product being retrieved
	 * @return the product whose BarCode is equal to barcode or null if such a product doesn't
	 * exist.
	 */
	public Product getProductByBarCode(BarCode barcode) {

		assert (barcode != null);
		return productsByBarCode.get(barcode);
	}

	/**
	 * Get the products in a ProductContainer
	 *
	 * @param c - The ProductContainer whose products will be returned
	 * @return the Products that belong to c
	 */
	public Collection<Product> getProductsByContainer(ProductContainer c) {

		assert (c != null);

		return c.getAllProducts();
	}

	/**
	 * Get the Containers that contain a Product
	 *
	 * @param p - The Product whose containers will be returned
	 * @return the ProductContainers that contain p
	 */
	public Set<ProductContainer> getContainersByProduct(Product p) {

		assert (p != null);

		return p.getContainers();
	}

	/**
	 * determines if the manager has a product
	 * 
	 * @param p - the product being searched for
	 * @return true if the product is in the manager. Otherwise, return false.
	 * @throws IllegalArgumentException 
	 */
	public boolean hasProduct(Product p) throws IllegalArgumentException {
		assert (p != null);

		if (p == null) {
			throw new IllegalArgumentException("Product is not defined");
		}

		return productsByBarCode.containsKey(p.getBarCode());
	}

	/**
	 * gets all products in the manager
	 * 
	 * @return all of the products in the manager
	 */
	public Collection<Product> getAllProducts() {
		return productsByBarCode.values();
	}

	/**
	 * gets a collection of items that point to a product
	 * 
	 * @param p - the product whose items will be returned.
	 * @return a collection of items that point to p
	 * 
	 * @throws IllegalArgumentException 
	 */
	public Collection<Item> getItemsByProduct(Product p) throws IllegalArgumentException {
		assert (p != null);
		assert (hasProduct(p));

		if (p == null || !hasProduct(p)) {
			throw new IllegalArgumentException("Product does not exist");
		}

		return currentItemsByProduct.get(p);
	}

	/**
	 * gets a collection of all items (removed or current) that point to a product
	 * 
	 * @param p - the product whose items will be returned.
	 * @return a collection of items that point to p
	 * 
	 * @throws IllegalArgumentException 
	 */
	public Collection<Item> getAllItemsByProduct(Product p) throws IllegalArgumentException {
		assert (p != null);
		assert (hasProduct(p));
		
		if (p == null || !hasProduct(p)) {
			throw new IllegalArgumentException("Product does not exist");
		}

		return allItemsByProduct.get(p);
	}
	
	public void accept(ProductVisitor visitor) {
		for (Product product : productsByBarCode.values()) {
			visitor.visitProduct(product);
		}
	}

	public void accept(ItemVisitor visitor, Product product) {
		Collection<Item> items = this.getAllItemsByProduct(product);
		if (items != null) {
			for (Item item : items) {
				visitor.visitItem(item);
			}
		}
	}

	public void accept(ProductVisitor productVisitor, ProductGroup group) {
		Product[] products = this.getProductsByContainer(group).toArray(new Product[0]);
		Arrays.sort(products, new Comparator<Product>() {
			public int compare(Product o1, Product o2) {
				return o1.getDescription().compareTo(o2.getDescription());
			}
		});

		if (products != null) {
			for (Product product : products) {
				productVisitor.visitProduct(product);
			}
		}
	}

	@Override
	public DataObject getDataObject() {
		// TODO Auto-generated method stub
		return null;
	}

	public void doAddProductToContainer(Product p, ProductContainer c) 
							throws IllegalArgumentException, SQLException {
		assert (p != null);
		assert (c != null);
		assert (canAddProductToContainer(p, c));

		if (p == null || c == null) {
			throw new IllegalArgumentException();
		}

		if (canAddProductToContainer(p, c) == false) {
			throw new IllegalArgumentException("Product cannot be added to container");
		}

		if (productsByBarCode.containsKey(p.getBarCode()) == false) {
			productsByBarCode.put(p.getBarCode(), p);
			currentItemsByProduct.put(p, new HashSet<Item>());
			allItemsByProduct.put(p, new HashSet<Item>());
		}

		// Check to see if p is already contained somewhere in c's storage unit
		Set<ProductContainer> containerSet = p.getContainers();
		ProductContainer[] containers = containerSet.toArray(new ProductContainer[0]);
		System.out.println("Containers: " + containers);
		for (ProductContainer container : containers) {
			if (container.getStorageUnit().equals(c.getStorageUnit())) {
				container.moveProduct(p);
				p.removeProductContainer(container);
			}
		}
		assert (!p.getContainers().contains(c));

		c.getStorageUnit().setContainerByProduct(p, c);
		p.addProductContainer(c);
		c.addProduct(p);
	}

	public void doAddItemToProduct(Product p, Item i) {
		assert (p != null);
		assert (i != null);
		assert (currentItemsByProduct.containsKey(p));
		assert (allItemsByProduct.containsKey(p));

		if (p == null || i == null) {
			throw new IllegalArgumentException();
		}

		if (!currentItemsByProduct.containsKey(p) || !allItemsByProduct.containsKey(p)) {
			throw new IllegalArgumentException("The product doesn't exist");
		}
		
		if(i.getExitDate() == null){
			currentItemsByProduct.get(p).add(i);
		}
		allItemsByProduct.get(p).add(i);
		// A product's creation date is equal to the earliest entry
		// date of any its items.
		if (p.getCreationDate().compareTo(i.getEntryDate()) > 0) {
			p.setCreationDate(i.getEntryDate());
		}
		assert (p.getCreationDate().compareTo(i.getEntryDate()) <= 0);
	}
}
