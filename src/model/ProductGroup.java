package model;

/** ProductGroup
 * A user-defined group of Products.  Product Groups 
 * are used by users to aggregate related
 * Products so they can be managed as a collection.  
 * For example, if a user has four different
 * kinds of toothpaste in storage, they could 
 * create a Product Group named “Toothpaste”,
 * and put all of the toothpaste Products in that Product Group.
 */

public class ProductGroup extends ProductContainer implements PersistentItem{
	private String name;
	private Size threeMonthSupply;
	private ProductContainer parent;
	
	public ProductGroup(String name, Size threeMonthSupply, ProductContainer parent){
		this.setName(name);
		this.setThreeMonthSupply(threeMonthSupply);
		this.setParent(parent);
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

	public void setParent(ProductContainer parent) {
		this.parent = parent;
	}

	public ProductContainer getParent() {
		return parent;
	}
	
	
}
