/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.managers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import model.entities.Item;
import model.entities.ProductContainer;
import model.entities.BarCode;
import model.entities.Product;
import model.entities.Size;
import model.entities.StorageUnit;
import model.entities.Unit;

/**
 * 
 * @author davidpatty, davidmathis
 */
public class ProductManager {

	/**
	 * Map of products indexed by their BarCodes
	 */
	Map<BarCode, Product> productsByBarCode;

	/**
	 * Map of Product Sets indexed by their Product Containers
	 */
	Map<ProductContainer, Set<Product>> productsByContainer;

	/**
	 * Map of ProductContainer Sets indexed by Product
	 */
	Map<Product, Set<ProductContainer>> containersByProduct;

	/**
	 * Constructor
	 * 
	 * Creates a new ProductManager
	 */
	public ProductManager() {
		productsByBarCode = new HashMap<BarCode, Product>();
		productsByContainer = new HashMap<ProductContainer, Set<Product>>();
		containersByProduct = new HashMap<Product, Set<ProductContainer>>();
	}
	
	/**
	 * Adds a product
	 * 
	 * @param p - Product to be added
	 * @param c - The ProductContainer to which the product is being added.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void addProductToContainer(Product p, ProductContainer c)
			throws IllegalArgumentException {

		if (p == null || c == null) {
			throw new IllegalArgumentException();
		}
		
		/*
		// get the storageUnit of c
		ProductContainer storageUnit = c;
		
		
		while(!(storageUnit instanceof StorageUnit)){
			storageUnit = c.getContainer();
		}
		
		// make sure product isn't already in the storageUnit of c
		if(storageUnit.hasProduct(p)){
			throw new IllegalArgumentException();
		} */
		
		// add product to productsByBarCode
		if(!productsByBarCode.containsKey(p.getBarCode())){
			productsByBarCode.put(p.getBarCode(), p);
		}

		// add product and container to productsByContainer
		if (productsByContainer.containsKey(c)) {
			productsByContainer.get(c).add(p);
		} else {
			Set<Product> prodSet = new HashSet<Product>();
			prodSet.add(p);
			productsByContainer.put(c, prodSet);
		}

		// add product and container to containersByProduct
		if (containersByProduct.containsKey(p)) {
			containersByProduct.get(p).add(c);
		} else {
			Set<ProductContainer> contSet = new HashSet<ProductContainer>();
			contSet.add(c);
			containersByProduct.put(p, contSet);
		}
		
		// add container to the product's set of containers
		p.addProductContainer(c);
	}
	
	/** Checks to see if a product can be removed.
	 * 
	 * @param p
	 * @param c
	 * @return return true if p can be removed, else return true
	 */
	public boolean canRemoveProductFromContainer(Product p, ProductContainer c){
		
		// is no container selected
		
		// is the product container the root?
		
		// does c have Items that point to p
		List<Item> items = c.getAllItems();
		
		for(Item item : items){
			if(item.getProduct().equals(p)){
				// their are items in c that point to p
				return false;
			}
		}
		
		return true;
	}
	
	/** Checks to see if a product can be removed.
	 * 
	 * @param p - the product being removed from c
	 * @param c - the ProductContainer from which p is being removed
	 * @return return true if p can be removed, else return true
	 * 
	 * @throws IllegalArgumentException if p is null
	 */
	public void removeProductFromContainer(Product p, ProductContainer c) throws IllegalArgumentException{
		
		if(p == null){
			throw new IllegalArgumentException();
		}
		
		// if no container is selected
		
		// if the container is the root
		
		// else
		c.removeProduct(p);
		p.removeProductContainer(c);
		productsByContainer.get(c).remove(p); // may be redundant
		containersByProduct.get(p).remove(c); // may be redundant
	}
	
	/** Checks to see if the new information being used to edit a product is valid
	 * 
	 * @param product
	 * @param newProduct
	 * @return true if oldProduct can be updated to newProduct
	 */
	public boolean canEditProduct(Product product, Product newProduct){

		// the description must be non-empty
		if(newProduct.getDescription() == ""){
			return false;
		}
		
		// the shelfLife must be non-negative
		if(newProduct.getShelfLife() < 0){
			return false;
		}
		
		// the threeMonthSupply must be non-negative
		if(newProduct.getThreeMonthSupply() < 0){
			return false;
		}
		
		// the size should already be valid.  A valid size should never be created.
		
		Size size = newProduct.getSize();
		// the size must be positive and cannot be zero
		if(size.getSize() <= 0){
			return false;
		}
		
		// if the unit of the size is count, the magnitude must be 1
		if(size.getUnits() == Unit.count && size.getSize() != 1){
			return false;
		}
		
		return true;
	}
	
	/** Edits a product by setting oldProduct to newProduct
	 * 
	 * @param product - the product being updated
	 * @param newProduct - the new product with the updated values
	 */
	public void editProduct(Product product, Product newProduct) throws IllegalArgumentException{
		if(product == null || newProduct == null){
			throw new IllegalArgumentException();
		}
		
		product.setDescription(newProduct.getDescription());
		product.setShelfLife(newProduct.getShelfLife());
		product.setSize(newProduct.getSize());
		product.setThreeMonthSupply(newProduct.getThreeMonthSupply());
	}
	
	
	
	/** Determines if a product exists
	 * 
	 * @param barcode - the BarCode of the product being searched for.
	 * @return true if the product is contained in the ProductMap. Otherwise,
	 *         return false.
	 */
	public boolean productExists(BarCode barcode) {
		return productsByBarCode.containsKey(barcode);
	}

	/** Get the product by its BarCode
	 * 
	 * @param barcode
	 *            - The BarCode of the product being retrieved
	 * @return the product whose BarCode is equal to barcode.
	 */
	public Product getProductByBarCode(BarCode barcode) {
		return productsByBarCode.get(barcode);
	}
	
	/** Get the products in a ProductContainer
	 * 
	 * @param c - The ProductContainer whose products will be returned
	 * @return the Products that belong to c
	 */
	public Set<Product> getProductsByContainer(ProductContainer c) {
		return productsByContainer.get(c);
	}
	
	/** Get the Containers that contain a Product
	 * 
	 * @param p - The Product whose containers will be returned
	 * @return the ProductContainers that contain p
	 */
	public Set<ProductContainer> getContainersByProduct(Product p) {
		return containersByProduct.get(p);
	}
}
