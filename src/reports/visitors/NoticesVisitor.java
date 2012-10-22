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

	private List<ProductGroup> groups;
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
	
	public List<ProductGroup> getProductGroups(){
		return groups;
	}
	
	public Set<Product> getInconsistentProducts(ProductGroup group){
		return incorrectProducts.get(group);
	}

}
