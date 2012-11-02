package reports.visitors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.entities.Item;
import model.entities.Product;
import model.entities.ProductContainer;
import model.entities.ProductGroup;
import model.entities.StorageUnit;

public class ExpiredItemsVisitor implements Visitor{

	/** A set of all expired items*/
	private List<Item> expiredItems = new ArrayList<Item>();
	
	
	@Override
	public void visitStorageUnit(StorageUnit unit) {
		this.visitProductContainer(unit);
	}
	
	public void visitProductGroup(ProductGroup group){
		this.visitProductContainer(group);
	}
	
	public void visitProductContainer(ProductContainer cont){
		Collection<Item> itemCollection = cont.getAllItems();
		List<Item> items  = new ArrayList<Item>();
		Collections.sort(items, new Comparator<Item>(){
			public int compare(Item item1, Item item2) {
				Product product1 = item1.getProduct();
				Product product2 = item2.getProduct();
				if(!product1.getDescription().equals(product2.getDescription()))
					return product1.getDescription().compareTo(product2.getDescription());
				return item1.getEntryDate().compareTo(item2.getEntryDate());
			}
		});
		
		for(Item item : items)
			visitItem(item);
	}
	
	public void visitItem(Item item){
		Calendar now = Calendar.getInstance();
		Calendar expDate = Calendar.getInstance();
		expDate.setTime(item.getExpirationDate());
		if(expDate.before(now)){
			expiredItems.add(item);
		}
	}
	
	/**
	 * Get all of the expired items after
	 * running the algorithm
	 * @return A set of all expired items
	 */
	public List<Item> getExpiredItems(){
		return expiredItems;
	}

}
