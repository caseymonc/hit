package reports.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.CoreObjectModel;
import model.entities.Product;

public class ProductStatsVisitor implements ProductVisitor {

	/** A list of all visited producte*/
	private List<Product> products;
	
	/** A list of all ItemStats acquired per Product*/
	private Map<Product, ItemStatsVisitor> itemStats;

	private int months;
	
	/**
	 * The number of months for which to create this report
	 * @param months
	 */
	public ProductStatsVisitor(int months){
		this.months = months;
		products = new ArrayList<Product>();
		itemStats = new HashMap<Product, ItemStatsVisitor>();
	}
	
	@Override
	public void visitProduct(Product product) {
		products.add(product);
		
		ItemStatsVisitor itemVisitor = new ItemStatsVisitor(months);
		CoreObjectModel model = CoreObjectModel.getInstance();
		model.getProductManager().accept(itemVisitor, product);
		itemStats.put(product, itemVisitor);
	}
	
	/**
	 * Get the products for which stats have been acquired
	 * @return
	 */
	public List<Product> getProducts(){
		return products;
	}

	public int getCurrentSupply(Product product) {
		return itemStats.get(product).getCurrentSupply();
	}
	
	public float getAverageSupply(Product product) {
		return itemStats.get(product).getAverageSupply();
	}
	
	public int getMaxSupply(Product product) {
		return itemStats.get(product).getMaxSupply();
	}
	
	public int getMinSupply(Product product) {
		return itemStats.get(product).getMinSupply();
	}
	
	public int getAddedSupply(Product product) {
		return itemStats.get(product).getAddedSupply();
	}
	
	public int getUsedSupply(Product product) {
		return itemStats.get(product).getUsedSupply();
	}
	
	public int getMaxCurrentAge(Product product) {
		return itemStats.get(product).getMaxCurrentAge();
	}
	
	public int getMaxUsedAge(Product product) {
		return itemStats.get(product).getMaxUsedAge();
	}
	
	public float getAverageCurrentAge(Product product) {
		return itemStats.get(product).getAverageCurrentAge();
	}
	
	public float getAverageUsedAge(Product product) {
		return itemStats.get(product).getAverageUsedAge();
	}
}
