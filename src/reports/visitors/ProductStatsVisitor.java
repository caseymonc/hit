package reports.visitors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		
		Collections.sort(products, new Comparator(){
			@Override
			public int compare(Object p1, Object p2) {
				Product product1 = (Product)p1;
				Product product2 = (Product)p2;
				return product1.getDescription().compareTo(product2.getDescription());
			}
		});
		
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
	
	public String getMaxCurrentAge(Product product) {
		return itemStats.get(product).getMaxCurrentAge();
	}
	
	public String getMaxUsedAge(Product product) {
		return itemStats.get(product).getMaxUsedAge();
	}
	
	public String getAverageCurrentAge(Product product) {
		return itemStats.get(product).getAverageCurrentAge();
	}
	
	public String getAverageUsedAge(Product product) {
		return itemStats.get(product).getAverageUsedAge();
	}
}
