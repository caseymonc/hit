package reports.visitors;

import model.entities.ProductGroup;

public interface ProductGroupVisitor {
	
	/**
	 * Do some action on the ProductGroup
	 * @param group The ProductGroup to visit
	 */
	void visitGroup(ProductGroup group);
}
