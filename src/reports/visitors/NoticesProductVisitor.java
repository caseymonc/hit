package reports.visitors;

import java.util.HashSet;
import java.util.Set;

import model.entities.Product;
import model.entities.ProductGroup;

public class NoticesProductVisitor implements ProductVisitor {

	/** The group that we were visiting*/
	private ProductGroup group;
	
	/** The inconsistend products in group*/
	private Set<Product> inconsistentProducts;
	
	public NoticesProductVisitor(ProductGroup group){
		this.group = group;
		inconsistentProducts = new HashSet<Product>();
	}
	
	@Override
	public void visitProduct(Product product) {
		if(product.getThreeMonthSize().getUnits() !=
			group.getThreeMonthSupply().getUnits()){
			inconsistentProducts.add(product);
		}
	}
	
	/**
	 * Ask whether there were inconsistent products in group
	 * @return true if there were inconsistent products
	 * @return false if there were no inconsistent products
	 */
	public boolean hasInconsistencies(){
		return inconsistentProducts.size() != 0;
	}
	
	/**
	 * Get a set of all inconsistent products in group
	 * @return Set of all inconsistent products in group
	 */
	public Set<Product> getInconsistentProducts(){
		return inconsistentProducts;
	}

}
