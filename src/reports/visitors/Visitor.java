package reports.visitors;

import model.entities.ProductGroup;
import model.entities.StorageUnit;

public interface Visitor {
	public void visitStorageUnit(StorageUnit unit);
	
	public void visitProductGroup(ProductGroup group);
}
