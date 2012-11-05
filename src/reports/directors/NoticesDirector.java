package reports.directors;

import model.CoreObjectModel;
import model.entities.Product;
import model.entities.ProductContainer;
import model.entities.ProductGroup;
import model.managers.StorageUnitManager;
import reports.visitors.NoticesPreOrderVisitor;
import reports.visitors.NoticesVisitor;
import reports.visitors.Visitor;
import gui.reports.Builder;

public class NoticesDirector extends Director {

	public NoticesDirector(Builder builder) {
		super(builder);
	}

	public void createReport() {
		NoticesVisitor visitor = new NoticesVisitor();
		
		StorageUnitManager manager = CoreObjectModel.getInstance().getStorageUnitManager();
		manager.acceptPostOrder(visitor);
		
		NoticesPreOrderVisitor preOrder = new NoticesPreOrderVisitor(visitor);
		manager.acceptPreOrder(preOrder);
		
		
		getBuilder().drawTitle("Notices");
		
		getBuilder().drawText("3-Month Supply Warnings", 20);
		for(ProductGroup group : preOrder.getGroups()){
			getBuilder().drawText(getProductGroupText(group), 16);
			for(Product product : preOrder.getProductsForGroup(group)){
				getBuilder().drawText(getProductText(product, group), 15);
			}
		}
		
		getBuilder().finish();
	}

	private String getProductText(Product product, ProductGroup group) {
		ProductContainer container = group.getStorageUnit().getContainerByProduct(product);
		return "- " + container.getName() + "::" 
		+ product.getDescription() 
		+ " ("+ product.getSize().toString() +")"; 
	}

	private String getProductGroupText(ProductGroup group) {
		return "Product Group " 
				+ group.getStorageUnit().getName() 
				+ "::" + group.getName()
				+ " has a 3-month supply (" + group.getThreeMonthSupply().toString() +")"
				+ " that is inconsistent with the following products:\n";
	}	
}
