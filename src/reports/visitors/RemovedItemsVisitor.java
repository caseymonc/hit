package reports.visitors;

import java.util.HashSet;
import java.util.Set;

import model.entities.Item;

public class RemovedItemsVisitor implements ItemVisitor {

	private Set<Item> removedItems = new HashSet<Item>();
	
	@Override
	public void visitItem(Item item) {
		if(item.getExitDate() != null){
			removedItems.add(item);
		}
	}

	public Set<Item> getExpiredItems(){
		return removedItems;
	}
}
