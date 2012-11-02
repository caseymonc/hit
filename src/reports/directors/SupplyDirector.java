package reports.directors;

import gui.reports.Builder;
import gui.reports.Table;
import gui.reports.Row;
import java.util.List;
import model.CoreObjectModel;
import model.entities.Product;
import model.managers.ProductManager;
import reports.visitors.NMonthVisitor;

public class SupplyDirector extends Director {
	
	/**
	 *  The N in NMonths
	 */
	int months;
	
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
		
		//Start Drawing the report
		getBuilder().drawTitle("Expired Items");
		
		Table table = new Table();
		table.addRow(getProductTitleRow());
		for(Product product : products){
			table.addRow(getProductRow(product));
		}
		
		getBuilder().drawTable(table);
	}
	
	/**
	 * 
	 * @return 
	 */
	private Row getProductTitleRow() {
		return new Row();
	}
	/**
	 * 
	 * @param product
	 * @return 
	 */
	private Row getProductRow(Product product) {
		return new Row();
	}
}
