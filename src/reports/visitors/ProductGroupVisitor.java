package reports.visitors;

import model.entities.ProductGroup;

public interface ProductGroupVisitor {
	void visitGroup(ProductGroup group);
}
