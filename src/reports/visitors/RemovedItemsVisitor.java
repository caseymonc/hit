package reports.visitors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.entities.Item;
import model.entities.Product;

public class RemovedItemsVisitor implements ItemVisitor {

	/** A list of all items removed after since*/
	private Map<Product, Set<Item>> removedItems = new HashMap<Product, Set<Item>>();
	
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
			addItem(item);
		}
	}

	private void addItem(Item item) {
		if(!removedItems.containsKey(item.getProduct())){
			removedItems.put(item.getProduct(), new HashSet<Item>());
		}
		
		removedItems.get(item.getProduct()).add(item);
	}

	/**
	 * Get a set of all items removed after since
	 * @return
	 */
	public Map<Product, Set<Item>> getRemovedItems(){
		return removedItems;
	}
}
