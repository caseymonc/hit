package reports.directors;

import gui.reports.Builder;
import gui.reports.Cell;
import gui.reports.Row;
import gui.reports.Table;
import model.CoreObjectModel;
import model.entities.Product;
import reports.visitors.ProductStatsVisitor;

public class ProductStatsDirector extends Director {
	
	public ProductStatsDirector(Builder builder) {
		super(builder);
	}

	@Override
	public void createReport() {
		
	}

	@Override
	public void createReport(int months) {
		ProductStatsVisitor productVisitor = new ProductStatsVisitor(months);
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
			row.addCell(new Cell(product.getThreeMonthSupply()));
			row.addCell(new Cell(productVisitor.getCurrentSupply(product), productVisitor.getAverageSupply(product)));
			row.addCell(new Cell(productVisitor.getMinSupply(product), productVisitor.getMaxSupply(product)));
			row.addCell(new Cell(productVisitor.getUsedSupply(product), productVisitor.getAddedSupply(product)));
			row.addCell(new Cell(product.getShelfLife()));
			row.addCell(new Cell(productVisitor.getAverageUsedAge(product), productVisitor.getMaxUsedAge(product)));
			row.addCell(new Cell(productVisitor.getAverageCurrentAge(product), productVisitor.getMaxCurrentAge(product)));
			table.addRow(row);
		}

		builder.drawTable(table);
	}

}
