package reports.visitors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import model.CoreObjectModel;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductGroup;
import model.entities.Size;
import model.entities.StorageUnit;
import model.entities.Unit;

public class NMonthVisitor implements ProductVisitor, Visitor {

	/** All of the visited products*/
	private List<Product> products;
	
	/** All of the visited ProductGroups*/
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
	public void visitStorageUnit(StorageUnit unit) { }

	@Override
	public void visitProductGroup(ProductGroup group) {
		Size size = group.getThreeMonthSupply();
		Size nMonthSupply = new Size(size.getUnits(), size.getSize() * ((float)months/3f));
		if(nMonthSupply.getSize() > 0){
			productGroups.add(group);
		}
	}
	
	public List<Product> getProducts(){
		return products;
	}

}
