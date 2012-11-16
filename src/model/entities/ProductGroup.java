package model.entities;

import model.persistence.DataObjects.DataObject;
import reports.visitors.ProductGroupVisitor;
import reports.visitors.Visitor;

/** ProductGroup
 * A user-defined group of Products.Product Groups are used by users to 
 * aggregate related Products so they can be managed as a collection.
 * For example, if a user has four different kinds of toothpaste in storage, 
 * they could create a Product Group named Toothpaste, and put all of the 
 * toothpaste Products in that Product Group.
 */

public class ProductGroup extends ProductContainer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2396336823994810333L;
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
	
	public void update(ProductContainer unit) {
		super.update(unit);
		if(!(unit instanceof ProductGroup)){
			throw new IllegalArgumentException();
		}
		
		ProductGroup group = (ProductGroup)unit;
		this.setThreeMonthSupply(group.getThreeMonthSupply());
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
		
		@Override
		public void removeProductFromContainer(Product product, ProductContainer container){
		super.removeProductFromContainer(product, container);
				this.getStorageUnit().removeProductFromContainerByProduct(product);
	}
		
	@Override
	public void removeProduct(Product product){
			super.removeProduct(product);
			this.getStorageUnit().removeProductFromContainerByProduct(product);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visitProductGroup(this);
	}

	public Size getCurrentSupply() {
		Size size = new Size(this.getThreeMonthSupply().getUnits(), 0);
		for(Item item : this.getAllItems()){
			size.add(item.getProduct().getSize());
		}
		
		for(ProductGroup group : this.getAllProductGroup()){
			size.add(group.getCurrentSupply());
		}
		return size;
	}

	@Override
	public DataObject getDataObject() {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public void accept(ProductGroupVisitor visitor) {
		visitor.visitGroup(this);
		for(ProductGroup group : this.getAllProductGroup()){
			group.accept(visitor);
		}
	}*/
}
