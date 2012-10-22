package reports.visitors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.CoreObjectModel;
import model.entities.Product;

public class ProductStatsVisitor implements ProductVisitor {

	private List<Product> products;
	private Map<Product, ItemStatsVisitor> itemStats;
	
	public ProductStatsVisitor(){
		products = new ArrayList<Product>();
		itemStats = new HashMap<Product, ItemStatsVisitor>();
	}
	
	@Override
	public void visitProduct(Product product) {
		products.add(product);
		
		ItemStatsVisitor itemVisitor = new ItemStatsVisitor();
		CoreObjectModel model = CoreObjectModel.getInstance();
		model.getProductManager().accept(itemVisitor, product);
		itemStats.put(product, itemVisitor);
	}
	
	public List<Product> getProducts(){
		return products;
	}

}
