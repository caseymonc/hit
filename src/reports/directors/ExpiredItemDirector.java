package reports.directors;

import java.util.List;
import java.util.Set;

import common.util.DateUtils;

import model.CoreObjectModel;
import model.entities.Item;
import model.entities.ProductContainer;
import model.entities.ProductGroup;
import model.managers.ItemManager;
import model.managers.StorageUnitManager;
import reports.visitors.ExpiredItemsVisitor;
import gui.reports.Builder;
import gui.reports.Cell;
import gui.reports.Row;
import gui.reports.Table;
import java.util.Date;

public class ExpiredItemDirector extends Director {

	public ExpiredItemDirector(Builder builder){
		super(builder);
	}

	public void createReport() {
		ExpiredItemsVisitor visitor = new ExpiredItemsVisitor();
		
		//Get the data by traversing the tree Pre-Order, Items sorted by 
		//Description and entry date, ProductContainers sorted by name
		StorageUnitManager manager = CoreObjectModel.getInstance().getStorageUnitManager();
		manager.acceptPreOrder(visitor);
		List<Item> expiredItems = visitor.getExpiredItems();
		
		//Start Drawing the report
		getBuilder().drawTitle("Expired Items");
		
		Table table = new Table();
		table.addRow(getTitleRow());
		for(Item item : expiredItems){
			table.addRow(getExpiredItemRow(item));
		}
		
		getBuilder().drawTable(table);
	}

	private Row getTitleRow() {
		Row row = new Row();
		row.addCell(new Cell("Description"));
		row.addCell(new Cell("Storage Unit"));
		row.addCell(new Cell("Product Group"));
		row.addCell(new Cell("Entry Date"));
		row.addCell(new Cell("Expire Date"));
		row.addCell(new Cell("Item Barcode"));
		return row;
	}

	private Row getExpiredItemRow(Item item) {
		Row row = new Row();
		row.addCell(new Cell(item.getProduct().getDescription()));
		ProductContainer container = item.getContainer();
		row.addCell(new Cell(container.getStorageUnit().getName()));
		if(container instanceof ProductGroup){
			row.addCell(new Cell(container.getName()));
		}else{
			row.addCell(new Cell(""));
		}
		row.addCell(new Cell(DateUtils.formatDate(item.getEntryDate())));
		row.addCell(new Cell(DateUtils.formatDate(item.getExpirationDate())));
		row.addCell(new Cell(item.getBarCode().toString()));
		return row;
	}
}
