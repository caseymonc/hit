package reports.visitors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.CoreObjectModel;
import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.StorageUnit;
import model.entities.Unit;

public class NoticesVisitor implements Visitor {

	/** A list of all groups with inconsistent */
	private List<ProductGroup> groups;
	
	/** A map of all inconsistent products in each group*/
	private Map<ProductGroup, List<Product>> incorrectProducts;
	
	public NoticesVisitor(){
		groups = new ArrayList<ProductGroup>();
		incorrectProducts = new HashMap<ProductGroup, List<Product>>();
	}
	
	@Override
	public void visitStorageUnit(StorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitProductGroup(ProductGroup group) {
		if(group.getThreeMonthSupply().getUnits() == Unit.count)
			return;
		
		CoreObjectModel model = CoreObjectModel.getInstance();
		NoticesProductVisitor productVisitor = new NoticesProductVisitor(group);
		model.getProductManager().accept(productVisitor, group);
		
		boolean hasInconsistencies = false;
		if(productVisitor.hasInconsistencies()){
			groups.add(group);
			incorrectProducts.put(group, productVisitor.getInconsistentProducts());
			hasInconsistencies = true;
		}
		
		Collection<ProductGroup> children = group.getAllProductGroup();
		if(children != null){
			for(ProductGroup child : children){
				if(groups.contains(child)){
					if(!hasInconsistencies){
						groups.add(group);
						incorrectProducts.put(group, new ArrayList<Product>());
						hasInconsistencies = true;
					}
					incorrectProducts.get(group).addAll(incorrectProducts.get(child));
				}
			}
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
	public List<Product> getInconsistentProducts(ProductGroup group){
		return incorrectProducts.get(group);
	}

	

}
