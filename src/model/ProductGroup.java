package model;

/** ProductGroup
 * A user-defined group of Products.Product Groups 
 * are used by users to aggregate related
 * Products so they can be managed as a collection.
 * For example, if a user has four different
 * kinds of toothpaste in storage, they could 
 * create a Product Group named Toothpaste,
 * and put all of the toothpaste Products in that Product Group.
 */

public class ProductGroup extends ProductContainer implements PersistentItem{
	/** String provided by the user that describes the Product Group.
	 * @Constraint Must be non-empty and unique within the parent Product Container.*/
	private String name;
	
	/** The total amount of Products in this Product Group required for
	 * a 3-month supply. For example, a value of "100 count" means that we must
	 * have at least 100 of the Products in this Product Group to have a 3-month supply. A
	 * value of "500 lbs" means that we must have at least 500 pounds of the Products in this
	 * Product Group to have a 3-month supply. A value of "48 quarts" means that we must
	 * have at least 48 quarts of the Products in this Product Group to have a 3-month supply.
	 * 
	 * @Constraint The magnitude can be any non-negative float value. Zero means "unspecified".
	 * The unit of measurement can be any of the following: count, pounds, ounces,
	 * grams, kilograms, gallons, quarts, pints, fluid ounces, liters. If the unit of
	 * measurement is "count", the magnitude must be an integer (i.e., no fraction).*/
	private Size threeMonthSupply;
	
	/** Product Container that contains this Product Group.
	 * @Constraint Must be non-empty. A ProductGroup is always 
	 * contained within one ProductContainer.*/
	private ProductContainer container;
	
	public ProductGroup(String name, Size threeMonthSupply, ProductContainer parent){
		this.setName(name);
		this.setThreeMonthSupply(threeMonthSupply);
		this.setContainer(parent);
	}
	
	public void addProductGroup(){
		
	}
	
	
	
	public void edit(String name, Size threeMonthSupply){
		setName(name);
		setThreeMonthSupply(threeMonthSupply);
	}
	
	public void delete(PersistentStore store){
		
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
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setThreeMonthSupply(Size threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}
	
	public Size getThreeMonthSupply() {
		return threeMonthSupply;
	}

	public void setContainer(ProductContainer container) {
		this.container = container;
	}

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
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		
	}
	
	
}
