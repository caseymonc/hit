package reports.visitors;

import java.util.HashSet;
import java.util.Set;

import model.entities.Product;
import model.entities.ProductGroup;

public class NoticesProductVisitor implements ProductVisitor {

	private ProductGroup group;
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
	
	public boolean hasInconsistencies(){
		return inconsistentProducts.size() != 0;
	}
	
	public Set<Product> getInconsistentProducts(){
		return inconsistentProducts;
	}

}
