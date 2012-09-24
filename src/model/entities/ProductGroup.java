package model.entities;

import model.entities.Item;
import model.entities.Product;
import java.util.List;
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

	public ProductGroup(String name, ProductContainer container, Size threeMonthSupply){
		super(name, container);
		
		assert(container != null);
		
		this.setThreeMonthSupply(threeMonthSupply);
	}
	
	public void setThreeMonthSupply(Size threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}

	public Size getThreeMonthSupply() {
		return threeMonthSupply;
	}
}
