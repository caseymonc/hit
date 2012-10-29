package reports.visitors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.entities.Item;

public class ExpiredItemsVisitor implements ItemVisitor{

	/** A set of all expired items*/
	private List<Item> expiredItems = new ArrayList<Item>();
	
	
	@Override
	public void visitItem(Item item) {
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
