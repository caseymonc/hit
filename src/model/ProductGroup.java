package model;

import java.util.List;

/** ProductGroup
 * A user-defined group of Products.Product Groups are used by users to 
 * aggregate related Products so they can be managed as a collection.
 * For example, if a user has four different kinds of toothpaste in storage, 
 * they could create a Product Group named Toothpaste, and put all of the 
 * toothpaste Products in that Product Group.
 */

public class ProductGroup extends ProductContainer implements PersistentItem{

	 /** String provided by the user that describes the Product Group.
	 * Must be non-empty and unique within the parent Product Container.
	 */
	private String name;
	
	/** The total amount of Products in this Product Group required for
	 * a 3-month supply. For example, a value of "100 count" means that we
	 * must have at least 100 of the Products in this Product Group to have 
	 * a 3-month supply. A value of "500 lbs" means that we must have at least 
	 * 500 pounds of the Products in this Product Group to have a 3-month 
	 * supply. A value of "48 quarts" means that we must have at least 48 quarts
	 * of the Products in this Product Group to have a 3-month supply.
	 * 
	 * The magnitude can be any non-negative float value. Zero means "unspecified".
	 * The unit of measurement can be any of the following: count, pounds, ounces,
	 * grams, kilograms, gallons, quarts, pints, fluid ounces, liters. If the unit of
	 * measurement is "count", the magnitude must be an integer (i.e., no fraction).
	 */
	private Size threeMonthSupply;
	
	/** Product Container that contains this Product Group.
	 * Must be non-empty. A ProductGroup is always contained in one ProductContainer.
	 */
	private ProductContainer container;
	
	/** Constructor
	 * 
	 * @param name
	 * @param threeMonthSupply
	 * @param parent
	 */
	public ProductGroup(String name, Size threeMonthSupply, ProductContainer parent){
		this.setName(name);
		this.setThreeMonthSupply(threeMonthSupply);
		this.setContainer(parent);
	}	
	
	/** Edits the name and three month supply of a ProductGroup
	 * 
	 * @param name - The new name of the ProductGroup
	 * @param threeMonthSupply - The new threeMonthSupply of the product group
	 */
	public void edit(String name, Size threeMonthSupply){
		setName(name);
		setThreeMonthSupply(threeMonthSupply);
	}
	
	public String sqlCreateStatement() {
		String query = "CREATE TABLE product_groups(" +
				"product_group_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT," +
				"threeMonthSupply TEXT," +
				"parent_id INTEGER" + 
				");";
		return query;
	}
	
	
	/*
	 * Getters and Setters
	 */
	
	/** Sets the name of the ProductGroup
	 * 
	 * @param name - The new name of the ProductGroup
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Gets the name of the ProductGroup
	 * 
	 * @return the name of the Product Group
	 */
	public String getName() {
		return name;
	}
	
	/** Sets the threeMonthSupply of the ProductGroup
	 * 
	 * @param threeMonthSupply - The new threeMonthSupply of the ProductGroup
	 */
	public void setThreeMonthSupply(Size threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}
	
	/** Gets the threeMonthSupply of the ProductGroup
	 * 
	 * @return the threeMonthSupply of the Product Group
	 */
	public Size getThreeMonthSupply() {
		return threeMonthSupply;
	}

	/** Sets the container of the ProductGroup
	 * 
	 * @param container - The new container of the ProductGroup
	 */
	public void setContainer(ProductContainer container) {
		this.container = container;
	}

	/** Gets the container of the ProductGroup
	 * 
	 * @return the container of the Product Group
	 */
	public ProductContainer getContainer() {
		return container;
	}

	@Override
	public void addItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canAddProductContainer(ProductContainer productContainer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addProductContainer(ProductContainer productContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProductContainer(ProductContainer productContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item getItemByBarCode(BarCode barcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getItemByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductContainer> getAllProductContainers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductContainer getProductContainerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
