package reports.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.StorageUnit;

public class NoticesPreOrderVisitor implements Visitor{

	private List<ProductGroup> groups;
	private NoticesVisitor visitor;
	private Map<ProductGroup, List<Product>> incorrectProducts;
	
	public NoticesPreOrderVisitor(NoticesVisitor visitor){
		this.visitor = visitor;
		this.groups = new ArrayList<ProductGroup>();
		this.incorrectProducts = new HashMap<ProductGroup, List<Product>>();
	}
	
	@Override
	public void visitStorageUnit(StorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitProductGroup(ProductGroup group) {
		if(visitor.getProductGroups().contains(group)){
			groups.add(group);
			incorrectProducts.put(group, visitor.getInconsistentProducts(group));
		}
	}
	
	public List<ProductGroup> getGroups(){
		return groups;
	}
	
	public List<Product> getProductsForGroup(ProductGroup group){
		return incorrectProducts.get(group);
	}

}
