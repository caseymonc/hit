package reports.visitors;

import model.entities.Item;

public interface ItemVisitor {
	void visitItem(Item item);
}
