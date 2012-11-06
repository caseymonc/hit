package reports.visitors;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import model.CoreObjectModel;
import model.entities.Product;

public class ProductStatsVisitor implements ProductVisitor {

	/** A list of all visited producte*/
	private List<Product> products;
	
	/** A list of all ItemStats acquired per Product*/
	private Map<Product, ItemStatsVisitor> itemStats;

	Date endDate;
	private int months;
	
	/**
	 * The number of months for which to create this report
	 * @param months
	 */
	public ProductStatsVisitor(Date endDate, int months){
		this.endDate = endDate;
		this.months = months;
		products = new ArrayList();
		itemStats = new HashMap();
	}
	
	@Override
	public void visitProduct(Product product) {
		products.add(product);
		
		Date productCreationDate = product.getCreationDate();
		
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(endDate);
		calendar.add(Calendar.MONTH, -months);
		Date startDate = calendar.getTime();
		
		if(startDate.before(productCreationDate)) {
			startDate = productCreationDate;
		}
		
		ItemStatsVisitor itemVisitor = new ItemStatsVisitor(startDate, endDate);
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

	public String getCurAvgSupply(Product product){
		String result = "";
		result += itemStats.get(product).getCurrentSupply();
		result += "/";
		result += itemStats.get(product).getAverageSupply();
		return result;
	}
	
	public String getMinMaxSupply(Product product){
		String result = "";
		result += itemStats.get(product).getMinSupply();
		result += "/";
		result += itemStats.get(product).getMaxSupply();
		return result;
	}
	
	public String getUsedAddedSupply(Product product){
		String result = "";
		result += itemStats.get(product).getUsedSupply();
		result += "/";
		result += itemStats.get(product).getAddedSupply();
		return result;
	}
	
	public String getAvgMaxUsedAge(Product product){
		String result = "";
		result += itemStats.get(product).getAverageUsedAge();
		result += "/";
		result += itemStats.get(product).getMaxUsedAge(); 
		return result;
	}
	
	public String getAvgMaxCurrentAge(Product product){
		String result = "";
		result += itemStats.get(product).getAverageCurrentAge();
		result += "/";
		result += itemStats.get(product).getMaxCurrentAge();
		return result;
	}
}
