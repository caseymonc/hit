package model.entities;

import model.persistence.PersistentItem;

/** ProductGroup
 * A user-defined group of Products.Product Groups are used by users to 
 * aggregate related Products so they can be managed as a collection.
 * For example, if a user has four different kinds of toothpaste in storage, 
 * they could create a Product Group named Toothpaste, and put all of the 
 * toothpaste Products in that Product Group.
 */

public class ProductGroup extends ProductContainer implements PersistentItem{
	private Size threeMonthSupply;

	/**
	 * Constructor
	 * @param name The name of the ProductGroup
	 * @param container The container of the ProductGroup resides in
	 * @param threeMonthSupply The three month supply of the ProductGroup
	 */
	public ProductGroup(String name, ProductContainer container, Size threeMonthSupply){
		super(name, container);
		
		assert(container != null);
		
		this.setThreeMonthSupply(threeMonthSupply);
	}
	
	/**
	 * Asks whether a ProductGroup can be created with these values
	 * @param name The name of the ProductGroup
	 * @param container The container of the ProductGroup resides in
	 * @param threeMonthSupply The three month supply of the ProductGroup
	 * @return true if a ProductGroup can be created with these values
	 * @return false if a ProductGroup cannot be created with these values
	 */
	public static boolean canCreate(String name, ProductContainer container, Size threeMonthSupply){
		try{
			new ProductGroup(name, container, threeMonthSupply);
			return true;
		}catch(IllegalArgumentException e){
			return false;
		}
	}
	
	/**
	 * Set the threeMonthSupply of this ProductGroup
	 * @param threeMonthSupply
	 */
	public void setThreeMonthSupply(Size threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}

	/**
	 * Get the Three month supply
	 * @return
	 */
	public Size getThreeMonthSupply() {
		return threeMonthSupply;
	}
}
