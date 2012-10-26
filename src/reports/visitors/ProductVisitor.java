package reports.visitors;

import model.entities.Product;

public interface ProductVisitor {
	
	/**
	 * Do some action on the Product
	 * @param product The Product to visit
	 */
	void visitProduct(Product product);
}
