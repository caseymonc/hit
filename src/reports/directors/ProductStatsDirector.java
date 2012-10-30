package reports.directors;

import gui.reports.Builder;
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

		for(Product product : productVisitor.getProducts()) {
			System.out.println("Current supply for " + product.toString() + ": " + productVisitor.getCurrentSupply(product));
			System.out.println("Average supply for " + product.toString() + ": " + productVisitor.getAverageSupply(product));
			System.out.println("Minimum supply for " + product.toString() + ": " + productVisitor.getMinSupply(product));
			System.out.println("Maximum supply for " + product.toString() + ": " + productVisitor.getMaxSupply(product));
			System.out.println("Used supply for " + product.toString() + ": " + productVisitor.getUsedSupply(product));
			System.out.println("Added supply for " + product.toString() + ": " + productVisitor.getAddedSupply(product));
			System.out.println("Average used age for " + product.toString() + ": " + productVisitor.getAverageUsedAge(product));
			System.out.println("Max used age for " + product.toString() + ": " + productVisitor.getMaxUsedAge(product));
			System.out.println("Average current age for " + product.toString() + ": " + productVisitor.getAverageCurrentAge(product));
			System.out.println("Max current age for " + product.toString() + ": " + productVisitor.getMaxCurrentAge(product));
			System.out.println("");
		}
	}

}
