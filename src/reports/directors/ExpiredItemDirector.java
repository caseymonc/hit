package reports.directors;

import java.util.Set;

import model.CoreObjectModel;
import model.entities.Item;
import model.managers.ItemManager;
import reports.visitors.ExpiredItemsVisitor;
import gui.reports.Builder;

public class ExpiredItemDirector extends Director {

	public ExpiredItemDirector(Builder builder){
		super(builder);
	}
	
	@Override
	public void createReport() {
		ExpiredItemsVisitor visitor = new ExpiredItemsVisitor();
		
		ItemManager manager = CoreObjectModel.getInstance().getItemManager();
		manager.accept(visitor);
		
		Set<Item> expiredItems = visitor.getExpiredItems();
		
		getBuilder().drawTitle("Expired Items");
		
		
	}

	@Override
	public void createReport(int months) {
		
	}

}
