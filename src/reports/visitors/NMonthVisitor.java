package reports.visitors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.CoreObjectModel;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.Size;

public class NMonthVisitor implements ProductVisitor, ProductGroupVisitor {

	private List<Product> products;
	private List<ProductGroup> productGroups;
	private int months;
	public NMonthVisitor(int months){
		this.months = months;
		this.products = new ArrayList<Product>();
		this.productGroups = new ArrayList<ProductGroup>();
	}
	
	@Override
	public void visitProduct(Product product) {
		int nMonthSupply = product.getThreeMonthSupply();
		nMonthSupply *= ((float)months/3f);
		
		CoreObjectModel model = CoreObjectModel.getInstance();
		Collection<Item> items = model.getProductManager().getItemsByProduct(product);
		if(items == null)
			items = new ArrayList<Item>();
		
		if(items.size() < nMonthSupply)
			products.add(product);
	}

	@Override
	public void visitGroup(ProductGroup group) {
		// TODO Auto-generated method stub
		
	}

}
