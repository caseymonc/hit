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

}
