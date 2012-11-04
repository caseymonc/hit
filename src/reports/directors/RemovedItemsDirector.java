package reports.directors;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.CoreObjectModel;
import model.entities.Item;
import model.entities.Product;
import model.managers.ItemManager;

import reports.visitors.RemovedItemsVisitor;
import gui.reports.Builder;
import gui.reports.Cell;
import gui.reports.Row;
import gui.reports.Table;

public class RemovedItemsDirector extends Director {

	private Date since;
	
	public RemovedItemsDirector(Builder builder, Date since) {
		super(builder);
		this.since = since;
	}

	public void createReport() {
		RemovedItemsVisitor visitor = new RemovedItemsVisitor(since);
		RemovedItemsVisitor currentVisitor = new RemovedItemsVisitor(since);

		ItemManager manager = CoreObjectModel.getInstance().getItemManager();
		manager.acceptRemoved(visitor);
		manager.accept(currentVisitor);
		
		Map<Product, Set<Item>> removedItems = visitor.getRemovedItems();
		Map<Product, Set<Item>> currentItems = visitor.getRemovedItems();
		
		getBuilder().drawTitle("Removed Items");
		
		Table table = new Table();
		table.addRow(getTitleRow());
		for(Product product : removedItems.keySet()){
			table.addRow(getRemovedItemRow(product, removedItems.get(product), 
													currentItems.get(product)));
		}
		
		getBuilder().drawTable(table);
		getBuilder().finish();
	}

	private Row getRemovedItemRow(Product product, Set<Item> removedItems, Set<Item> currentItems) {
		if(removedItems == null)
			removedItems = new HashSet<Item>();
		
		if(currentItems == null)
			currentItems = new HashSet<Item>();
		
		Row row = new Row();
		row.addCell(new Cell(product.getDescription()));
		row.addCell(new Cell(product.getSize().toString()));
		row.addCell(new Cell(product.getBarCode().toString()));
		row.addCell(new Cell(removedItems.size() + ""));
		row.addCell(new Cell(currentItems.size() + ""));
		return row;
	}

	private Row getTitleRow() {
		Row row = new Row();
		row.addCell(new Cell("Description"));
		row.addCell(new Cell("Size"));
		row.addCell(new Cell("Product Barcode"));
		row.addCell(new Cell("Removed"));
		row.addCell(new Cell("Current Supply"));
		return row;
	}
}
