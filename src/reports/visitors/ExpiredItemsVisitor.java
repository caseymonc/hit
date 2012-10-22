package reports.visitors;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import model.entities.Item;

public class ExpiredItemsVisitor implements ItemVisitor{

	private Set<Item> expiredItems = new HashSet<Item>();
	
	
	@Override
	public void visitItem(Item item) {
		Calendar now = Calendar.getInstance();
		Calendar expDate = Calendar.getInstance();
		expDate.setTime(item.getExpirationDate());
		if(expDate.before(now)){
			expiredItems.add(item);
		}
	}
	
	public Set<Item> getExpiredItems(){
		return expiredItems;
	}

}
