package gui.batches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.CoreObjectModel;
import model.controllers.ItemController;
import model.entities.BarCode;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductContainer;
import model.entities.ProductGroup;
import gui.common.*;
import gui.item.ItemData;
import gui.product.*;

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends Controller implements
		IRemoveItemBatchController {
	
	
	private ItemController iController;
	private CoreObjectModel model;
	private List<Product> removedProducts;
	private HashMap<Product, Set<Item>> removedItems;
	private HashMap<Product, ProductData> productDataForProduct;
	private HashMap<Item, ItemData> itemDataForItem;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the remove item batch view.
	 */
	public RemoveItemBatchController(IView view) {
		super(view);
		model = CoreObjectModel.getInstance();
		iController = model.getItemController();
		removedProducts = new ArrayList<Product>();
		removedItems = new HashMap<Product, Set<Item>>();
		productDataForProduct = new HashMap<Product, ProductData>();
		itemDataForItem = new HashMap<Item, ItemData>();
		construct();
		
	}
	
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IRemoveItemBatchView getView() {
		return (IRemoveItemBatchView)super.getView();
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
		enableComponents();
		Product selectedProduct = getSelectedProduct();
		System.out.println("Selected Product: " + selectedProduct);
		List<ProductData> productDataList = new ArrayList<ProductData>();
		for(Product product : removedProducts){
			ProductData productData = new ProductData();			
		    productData.setBarcode(product.getBarCode().toString());
		    int itemCount = removedItems.get(product).size();
		    productData.setCount(Integer.toString(itemCount));
		    productData.setDescription(product.getDescription());
		    productData.setShelfLife(product.getShelfLife() + " months");
		    productData.setSize(SizeFormatter.format(product.getSize()));
		    productData.setSupply(SizeFormatter.format(product.getThreeMonthSize()));
		    productData.setTag(product);
		    productDataForProduct.put(product, productData);
		    productDataList.add(productData);
		}
		
		getView().setProducts(productDataList.toArray(new ProductData[0]));
		
		if(selectedProduct != null){
			ProductData data = productDataForProduct.get(selectedProduct);
			getView().selectProduct(data);
			
			
			List<ItemData> itemDataList = new ArrayList<ItemData>();
			Set<Item> selectedItems = removedItems.get(selectedProduct);
			System.out.println("Selected Items: " + selectedItems);
			for(Item item : selectedItems){
				ItemData itemData = new ItemData();
				itemData.setBarcode(item.getBarCode().getBarCode());
				itemData.setEntryDate(item.getEntryDate());
				itemData.setExpirationDate(item.getExpirationDate());
                                
                String groupName = "";
                String unitName = "";
                
                itemData.setProductGroup(groupName);
                itemData.setStorageUnit(unitName);
                itemData.setTag(item);
				itemDataList.add(itemData);
			}
			
			getView().setItems(itemDataList.toArray(new ItemData[0]));
		}
	}

	private Product getSelectedProduct() {
		ProductData data = getView().getSelectedProduct();
		if(data == null)
			return null;
		return (Product) data.getTag();
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently
	 * allowed to interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's view
	 * have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
		getView().enableRedo(false);
		getView().enableUndo(false);
		Item item = getItemFromBarCode();
		if(item != null){
			getView().enableItemAction(true);
		}else{
			getView().enableItemAction(false);
		}
	}

	private Item getItemFromBarCode() {
		BarCode barCode = new BarCode(getView().getBarcode());
		Item item = iController.getItemManager().getItemByBarCode(barCode);
		return item;
	}

	/**
	 * This method is called when the "Item Barcode" field is changed
	 * in the remove item batch view by the user.
	 */
	@Override
	public void barcodeChanged() {
		enableComponents();
	}
	
	/**
	 * This method is called when the "Use Barcode Scanner" setting is changed
	 * in the remove item batch view by the user.
	 */
	@Override
	public void useScannerChanged() {
	}
	
	/**
	 * This method is called when the selected product changes
	 * in the remove item batch view.
	 */
	@Override
	public void selectedProductChanged() {
		loadValues();
	}
	
	/**
	 * This method is called when the user clicks the "Remove Item" button
	 * in the remove item batch view.
	 */
	@Override
	public void removeItem() {
		Item item = getItemFromBarCode();
		if(item != null){
			iController.removeItem(item);
			addRemovedItem(item);
			loadValues();
		}
	}
	
	private void addRemovedItem(Item item) {
		if(!removedProducts.contains(item.getProduct())){
			removedProducts.add(item.getProduct());
			Set<Item> items = new HashSet<Item>();
			
			removedItems.put(item.getProduct(), items);
		}
		Set<Item> items = removedItems.get(item.getProduct());
		items.add(item);
		removedItems.put(item.getProduct(), items);
	}

	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the remove item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the remove item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the remove item batch view.
	 */
	@Override
	public void done() {
		getView().close();
	}

}

