package reports.directors;

import gui.reports.Builder;
import gui.reports.Cell;
import gui.reports.Row;
import gui.reports.Table;
import java.util.Calendar;
import java.util.Date;
import model.CoreObjectModel;
import model.entities.Product;
import reports.visitors.ProductStatsVisitor;

public class ProductStatsDirector extends Director {
	
	public ProductStatsDirector(Builder builder) {
		super(builder);
	}

	public void createReport(Date endDate, int months)  {
		
		ProductStatsVisitor productVisitor = new ProductStatsVisitor(endDate, months);
		CoreObjectModel model = CoreObjectModel.getInstance();
		model.getProductManager().accept(productVisitor);
		
		builder.drawTitle("Product Report (" + months + " Months)");
		
		Table table = new Table();
		
		Row header = new Row();
		header.addCell(new Cell("Description"));
		header.addCell(new Cell("BarCode"));
		header.addCell(new Cell("Size"));
		header.addCell(new Cell("3-Month Supply"));
		header.addCell(new Cell("Supply: Cur/Avg"));
		header.addCell(new Cell("Supply: Min/Max"));
		header.addCell(new Cell("Supply: Used/Added"));
		header.addCell(new Cell("Shelf Life"));
		header.addCell(new Cell("Used Age: Avg/Max"));
		header.addCell(new Cell("Current Age: Avg/Max"));
		
		table.addRow(header);
		
		for(Product product : productVisitor.getProducts()) {
			Row row = new Row();
			row.addCell(new Cell(product.getDescription()));
			row.addCell(new Cell(product.getBarCode().toString()));
			row.addCell(new Cell(product.getSize().toString()));
			row.addCell(new Cell(Integer.toString(product.getThreeMonthSupply())));
			row.addCell(new Cell(productVisitor.getCurAvgSupply(product)));
			row.addCell(new Cell(productVisitor.getMinMaxSupply(product)));
			row.addCell(new Cell(productVisitor.getUsedAddedSupply(product)));
			row.addCell(new Cell(Integer.toString(product.getShelfLife()) + " months"));
			row.addCell(new Cell(productVisitor.getAvgMaxUsedAge(product)));
			row.addCell(new Cell(productVisitor.getAvgMaxCurrentAge(product)));
			table.addRow(row);
		}

		builder.drawTable(table);
		
		builder.finish();
	}

}
