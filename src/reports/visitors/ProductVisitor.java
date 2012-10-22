package reports.visitors;

import model.entities.Product;

public interface ProductVisitor {
	void visitProduct(Product product);
}
