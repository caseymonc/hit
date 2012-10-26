package reports.visitors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import model.entities.Item;

public class RemovedItemsVisitor implements ItemVisitor {

	/** A list of all items removed after since*/
	private Set<Item> removedItems = new HashSet<Item>();
	
	/** The date from which to get all removed items*/
	private Date since;
	
	/**
	 * Constructor
	 * @param since The date from which to get all removed items
	 */
	public RemovedItemsVisitor(Date since){
		this.since = since;
	}
	
	@Override
	public void visitItem(Item item) {
		if(item.getExitDate() != null && item.getExitDate().after(since)){
			removedItems.add(item);
		}
	}

	/**
	 * Get a set of all items removed after since
	 * @return
	 */
	public Set<Item> getRemovedItems(){
		return removedItems;
	}
}
