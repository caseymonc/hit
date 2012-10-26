package reports.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.CoreObjectModel;
import model.entities.Product;
import model.entities.ProductGroup;

public class NoticesVisitor implements ProductGroupVisitor {

	/** A list of all groups with inconsistent */
	private List<ProductGroup> groups;
	
	/** A map of all inconsistent products in each group*/
	private Map<ProductGroup, Set<Product>> incorrectProducts;
	
	public NoticesVisitor(){
		groups = new ArrayList<ProductGroup>();
		incorrectProducts = new HashMap<ProductGroup, Set<Product>>();
	}
	
	@Override
	public void visitGroup(ProductGroup group) {
		CoreObjectModel model = CoreObjectModel.getInstance();
		NoticesProductVisitor productVisitor = new NoticesProductVisitor(group);
		model.getProductManager().accept(productVisitor, group);
		
		if(productVisitor.hasInconsistencies()){
			groups.add(group);
			incorrectProducts.put(group, productVisitor.getInconsistentProducts());
		}
	}
	
	/**
	 * Asks whether the visitor found inconsistencies
	 * @return
	 */
	public boolean hasInconsistencies(){
		return groups.size() != 0;
	}
	
	/**
	 * Get all of the groups with inconsistencies
	 * @return a List of all of the groups with inconsistencies
	 */
	public List<ProductGroup> getProductGroups(){
		return groups;
	}
	
	/**
	 * Get a Set of all of the inconsistent products in a particular
	 * ProductGroup
	 * @param group the ProductGroup to get the set from
	 * @return a Set of all of the inconsistent products in a particular
	 * ProductGroup
	 */
	public Set<Product> getInconsistentProducts(ProductGroup group){
		return incorrectProducts.get(group);
	}

}
