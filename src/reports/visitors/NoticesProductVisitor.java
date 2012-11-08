package reports.visitors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.Unit;

public class NoticesProductVisitor implements ProductVisitor {

	/** The group that we were visiting*/
	private ProductGroup group;
	
	/** The inconsistent products in group*/
	private List<Product> inconsistentProducts;
	
	public NoticesProductVisitor(ProductGroup group){
		this.group = group;
		inconsistentProducts = new ArrayList<Product>();
	}
	
	@Override
	public void visitProduct(Product product) {
		if(!((product.getThreeMonthSize().getUnits().isVolume() &&
			group.getThreeMonthSupply().getUnits().isVolume()) ||
			(product.getThreeMonthSize().getUnits().isWeight() &&
			group.getThreeMonthSupply().getUnits().isWeight()) ||
			(product.getThreeMonthSize().getUnits() == Unit.count &&
			group.getThreeMonthSupply().getUnits() == Unit.count))){
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
	public List<Product> getInconsistentProducts(){
		Collections.sort(inconsistentProducts,  new Comparator(){
			@Override
			public int compare(Object o1, Object o2) {
				Product product1 = (Product)o1;
				Product product2 = (Product)o2;
				return product1.getDescription().compareTo(product2.getDescription());
			}
			
		});
		
		return inconsistentProducts;
	}

}
