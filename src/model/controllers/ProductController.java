package model.controllers;

import java.util.Collection;
import java.util.Set;
import model.CoreObjectModel;
import model.Hint;
import model.entities.*;
import model.managers.ProductManager;

/**
 * @author davidmathis
 */
public class ProductController extends ModelController {

	/**
	 * The CoreObjectModel used to manage all data model implements the singleton that it can be
	 * shared between many different Controllers
	 */
	private static ProductController instance;

	private CoreObjectModel model;
	private ProductManager productManager;

	/**
	 * Constructor
	 *
	 * creates a new ProductController
	 */
	private ProductController() {
		model = CoreObjectModel.getInstance();
		productManager = model.getProductManager();
	}
	
	public static void resetInstance() {
		instance = null;
	}
	
	/**
	 * gets the ProductController instance
	 * 
	 * @return an instance of ProductController
	 */
	public static ProductController getInstance() {
		if (instance == null) {
			instance = new ProductController();
		}

		return instance;
	}

	/**
	 * Get the product by its BarCode
	 *
	 * @param barcode - The BarCode of the product being retrieved
	 * @return the product whose BarCode is equal to barcode or null if such a product doesn't
	 * exist.
	 */
	public Product getProductByBarCode(BarCode barcode) {
		return productManager.getProductByBarCode(barcode);
	}

	/**
	 * Determines if a product can be added to the Product Manager
	 *
	 * @param p - the product being added
	 *
	 * @return true if p can be added. Otherwise, return false.
	 */
	public boolean canAddProduct(Product p) {
		return productManager.canAddProduct(p);
	}

	/**
	 * Adds a product to the Product Manager
	 *
	 * @param p - the product being added
	 *
	 * @throws IllegalArgumentException
	 */
	public void addProduct(Product p) throws IllegalArgumentException {
		assert (p != null);
		assert (canAddProduct(p));

		if (p == null || (canAddProduct(p) == false)) {
			throw new IllegalArgumentException("Not a valid Product");
		}

		productManager.addProduct(p);
		this.setChanged();
		this.notifyObservers(new Hint(p, Hint.Value.Add));
	}

	/**
	 * Adds a product to a container
	 *
	 * @param p - Product to be added
	 * @param c - The ProductContainer to which the product is being added.
	 *
	 * @throws IllegalArgumentException
	 */
	public void addProductToContainer(Product p, ProductContainer c)
			throws IllegalArgumentException {
		try {
			productManager.addProductToContainer(p, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Moves a product to a container
	 * 
	 * @param product - the product being moved
	 * @param targetContainer - the container the product is being moved to
	 * @param currentContainer - the container that the product is currently in
	 */
	public void moveProductToContainer(Product product, ProductContainer targetContainer, ProductContainer currentContainer) {
		if (targetContainer == currentContainer) {
			return;
		}

		StorageUnit targetUnit = targetContainer.getStorageUnit();
		ProductContainer currentContainerInTargetUnit =
				targetUnit.getContainerByProduct(product);

		boolean needsToBeRemovedFromStorageUnit =
				targetUnit != currentContainer.getStorageUnit();
		//Just add
		if (currentContainerInTargetUnit == null) {
			addProductToContainer(product, targetContainer);
		} else if (currentContainerInTargetUnit != targetContainer) {
			//Move all items in sibling container
			moveProductToTargetContainer(product, targetContainer,
					targetUnit, currentContainerInTargetUnit);
		}

		this.setChanged();
		this.notifyObservers(new Hint(product, Hint.Value.Move));
	}

	private void moveProductToTargetContainer(Product product,
			ProductContainer targetContainer, StorageUnit targetUnit,
			ProductContainer currentContainerInTargetUnit) {
		Set<Item> itemSet = currentContainerInTargetUnit.getItemsByProduct(product);
		addProductToContainer(product, targetContainer);
		if (itemSet != null) {
			Item[] items = new Item[itemSet.size()];
			int i = 0;
			for (Item item : itemSet) {
				items[i] = item;
				i++;
			}

			for (Item item : items) {
				model.getStorageUnitController().moveItem(item, targetUnit);
			}
		}
	}

	/**
	 * Checks to see if a product can be removed.
	 *
	 * @param p - The product being added to c
	 * @param c - The container that p is being removed from
	 * @return return true if p can be removed, else return true
	 */
	public boolean canRemoveProductFromContainer(Product p, ProductContainer c) {
		return productManager.canRemoveProductFromContainer(p, c);
	}

	/**
	 * Removes a product from a container.
	 *
	 * @param p - the product being removed from c
	 * @param c - the ProductContainer from which p is being removed
	 * @return return true if p can be removed, else return true
	 *
	 * @throws IllegalArgumentException if p or c are null
	 */
	public void removeProductFromContainer(Product p, ProductContainer c)
			throws IllegalArgumentException {
		productManager.removeProductFromContainer(p, c);
		this.setChanged();
		this.notifyObservers(new Hint(p, Hint.Value.Delete));
	}

	/**
	 * Determines if a product can be removed.
	 *
	 * @param p - the product being removed
	 * @return true if p can be removed. Otherwise, return false
	 */
	public boolean canRemoveProduct(Product p) {
		return productManager.canRemoveProduct(p);
	}

	/**
	 * Removed a product from the system.
	 *
	 * @param p
	 */
	public void removeProduct(Product p) {
		Set<ProductContainer> containers = p.getContainers();
		if (containers != null) {
			for (ProductContainer container : containers) {
				if (this.canRemoveProductFromContainer(p, container)) {
					this.removeProductFromContainer(p, container);
				}
			}
		}

		productManager.removeProduct(p);
	}

	/**
	 * Edits a product by updating its data to the data contained in newProduct
	 * 
	 * @param productBarCode - the barcode of the product being edited
	 * @param newProduct - contains the updated information for the product being edited
	 * 
	 * @throws IllegalArgumentException 
	 */
	public void EditProduct(BarCode productBarCode, Product newProduct)
			throws IllegalArgumentException {
		productManager.editProduct(productBarCode, newProduct);

		Product p = productManager.getProductByBarCode(productBarCode);
		this.setChanged();
		this.notifyObservers(new Hint(p, Hint.Value.Edit));
	}

	/**
	 * Tests whether or not a product can be edited.
	 *
	 * @param barcode - the new barcode string for the product
	 * @param desc - the new description for the product
	 * @param sizeVal - the new size value for the product
	 * @param sizeUnit - the new size unit for the product
	 * @param supply - the new 3 month supply for the product
	 * @param shelfLife - the new shelf life for the product
	 *
	 * @return true if a product can be edited. Otherwise return false.
	 */
	public boolean canEditProduct(String barcode, String description, String sizeVal,
			Unit sizeUnit, String supply, String shelfLife) {

		Product product = createProduct(barcode, description, sizeVal,
				sizeUnit, supply, shelfLife);

		if (product == null) {
			return false;
		}

		return true;
	}

	/**
	 * Tests whether or not a product can be added.
	 *
	 * @param barcode - the new barcode string for the product
	 * @param desc - the new description for the product
	 * @param sizeVal - the new size value for the product
	 * @param sizeUnit - the new size unit for the product
	 * @param supply - the new 3 month supply for the product
	 * @param shelfLife - the new shelf life for the product
	 *
	 * @return true if a product can be added. Otherwise return false.
	 */
	public boolean canAddProduct(String barcode, String description, String sizeVal,
			Unit sizeUnit, String supply, String shelfLife) {

		Product product = createProduct(barcode, description, sizeVal,
				sizeUnit, supply, shelfLife);

		if (product == null) {
			return false;
		}

		if (canAddProduct(product) == false) {
			return false;
		}

		return true;
	}

	/**
	 * Tests whether or not a product can be created.
	 *
	 * @param barcode
	 * @param description
	 * @param sizeVal
	 * @param sizeUnit
	 * @param supply
	 * @param shelfLife
	 *
	 * @return a product if the product can be created. Otherwise it returns null.
	 */
	private Product createProduct(String barcode, String description, String sizeVal,
			Unit sizeUnit, String supply, String shelfLife) {

		String _description = description;
		BarCode _barCode = new BarCode(barcode);
		float _sizeVal;
		int _supply;
		int _shelfLife;

		try {
			_sizeVal = Float.parseFloat(sizeVal);
			if (Float.isInfinite(_sizeVal) || Float.isNaN(_sizeVal) || _sizeVal < 1) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

		try {
			_shelfLife = Integer.parseInt(shelfLife);
		} catch (Exception e) {
			return null;
		}

		try {
			_supply = Integer.parseInt(supply);
		} catch (Exception e) {
			return null;
		}

		Size size = new Size(sizeUnit, _sizeVal);

		if (Product.canCreate(_description, _barCode, _shelfLife, _supply, size) == false) {
			return null;
		}

		Product product = new Product(_description, _barCode, _shelfLife, _supply, size);

		return product;
	}

	/**
	 * gets all of the products in the system
	 * 
	 * @return all products in the system 
	 */
	public Collection<Product> getAllProducts() {
		return productManager.getAllProducts();
	}

	/**
	 * gets all of the items that point to a product
	 * @param p - the product whose items will be returned
	 * @return - the items that point to p
	 * @throws IllegalArgumentException 
	 */
	public Collection<Item> getItemsByProduct(Product p) throws IllegalArgumentException {
		return productManager.getItemsByProduct(p);
	}

	/**
	 * Adds an item to a product
	 * 
	 * @param p - the product the item is being added to
	 * @param i - the item being added to the product
	 */
	public void addItemToProduct(Product p, Item i) {
		productManager.addItemToProduct(p, i);
	}

	/**
	 * Removes an item from a product
	 * 
	 * @param p - the product from whole the product is being removed
	 * @param i - the item being removed from the product
	 */
	public void removeItemFromProduct(Product p, Item i) {
		productManager.removeItemFromProduct(p, i);
	}

	/**
	 * Unremoves an item from a product
	 * 
	 * @param product - the product from whole the product is being removed
	 * @param item - the item being removed from the product 
	 */
	public void unRemoveItemFromProduct(Product product, Item item) {
		addItemToProduct(product, item);
	}
}
