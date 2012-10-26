package reports.visitors;

import model.entities.Item;

public interface ItemVisitor {
	
	/**
	 * Do some action on the item
	 * @param item The item to visit
	 */
	void visitItem(Item item);
}
