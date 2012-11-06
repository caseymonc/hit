package reports.directors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.entities.Item;
import model.entities.Product;
import reports.visitors.ItemVisitor;

public class CurrentItemsVisitor implements ItemVisitor {

	private Map<Product, Set<Item>> currentItems = new HashMap<Product, Set<Item>>();

	@Override
	public void visitItem(Item item) {
		if(item.getExitDate() == null){
			addItem(item);
		}
	}
	
	private void addItem(Item item) {
		if(!currentItems.containsKey(item.getProduct())){
			currentItems.put(item.getProduct(), new HashSet<Item>());
		}
		
		currentItems.get(item.getProduct()).add(item);
	}
	
	public Map<Product, Set<Item>> getCurrentItems(){
		return currentItems;
	}

}
