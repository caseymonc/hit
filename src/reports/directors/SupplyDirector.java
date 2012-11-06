package reports.directors;

import gui.reports.Builder;
import gui.reports.Cell;
import gui.reports.Table;
import gui.reports.Row;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.CoreObjectModel;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.Size;
import model.managers.ProductManager;
import model.managers.StorageUnitManager;
import reports.visitors.NMonthVisitor;

public class SupplyDirector extends Director {
	
	/**
	 *  The N in NMonths
	 */
	int months;
	
	final int TITLE_SIZE = 6;
	/**
	 * 
	 * @param builder 
	 */
	public SupplyDirector(Builder builder, int months) {
		super(builder);
		this.months = months;
	}

	
//	@Override //removed cause there is no createReport in Director
	public void createReport() {
		NMonthVisitor visitor = new NMonthVisitor(months);
		
		//Get the data by traversing the tree Pre-Order, Items sorted by 
		//Description and entry date, ProductContainers sorted by name
		ProductManager manager = CoreObjectModel.getInstance().getProductManager();

		//visitor stores the info from where he visits 
		manager.accept(visitor);
		List<Product> products = visitor.getProducts();
		Collections.sort(products, new Comparator<Product>(){
			public int compare(Product product1, Product product2) {
				return product1.getDescription().compareTo(product2.getDescription());
			}
		});
		
		StorageUnitManager suManager = CoreObjectModel.getInstance().getStorageUnitManager();
		suManager.acceptPreOrder(visitor);
		
		List<ProductGroup> groups = visitor.getProductGroups();
		
		//Start Drawing the report
		Builder builder = getBuilder();
		builder.drawTitle(this.months + "-Month Supply");
		
		builder.drawText("Products", TITLE_SIZE);

		Table table = new Table();
		table.addRow(getProductTitleRow());
		for(Product product : products){
			table.addRow(getProductRow(product));
		}
		
		builder.drawTable(table);

		builder.drawText("Product Groups", TITLE_SIZE);
				
		Table productGroupTable = new Table();
		productGroupTable.addRow(getProductGroupTitleRow());
		for(ProductGroup group : groups){
			productGroupTable.addRow(getProductGroupRow(group));
		}
		
		builder.drawTable(productGroupTable);
		builder.finish();
		builder.display();
	}
	
	private Row getProductGroupRow(ProductGroup group) {
		Row row = new Row();
		row.addCell(new Cell(group.getName()));
		row.addCell(new Cell(group.getStorageUnit().getName()));
		Size size = group.getThreeMonthSupply();
		row.addCell(new Cell(size.getSize() * this.months/3f + " " + size.getUnits().toString()));
		size = group.getCurrentSupply();
		row.addCell(new Cell(size.getSize() + " " + size.getUnits().toString()));
		return row;
	}


	private Row getProductGroupTitleRow() {
		Row row = new Row();
		row.addCell(new Cell("Product Group"));
		row.addCell(new Cell("Storage Unit"));
		row.addCell(new Cell(months + "-Month-Supply"));
		row.addCell(new Cell("Current Supply"));
		return row;
	}


	/**
	 * 
	 * @return 
	 */
	private Row getProductTitleRow() {
		Row row = new Row();
		row.addCell(new Cell("Description"));
		row.addCell(new Cell("Barcode"));
		row.addCell(new Cell(months + "-Month-Supply"));
		row.addCell(new Cell("Current Supply"));
		return row;
	}
	/**
	 * 
	 * @param product
	 * @return 
	 */
	private Row getProductRow(Product product) {
		Row row = new Row();
		row.addCell(new Cell(product.getDescription()));
		row.addCell(new Cell(product.getBarCode().toString()));
		Size size = product.getThreeMonthSize();
		row.addCell(new Cell(size.getSize() * months/3f + " " + size.getUnits().toString()));
		
		Collection<Item> items = CoreObjectModel.getInstance()
											.getProductManager()
											.getItemsByProduct(product);
		size = product.getSize();
		row.addCell(new Cell(items.size() * size.getSize() + " " + size.getUnits().toString()));
		return row;
	}
}
