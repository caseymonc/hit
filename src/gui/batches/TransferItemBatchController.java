package gui.batches;

import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import model.CoreObjectModel;
import model.controllers.ItemController;
import model.controllers.ProductController;
import model.controllers.ProductGroupController;
import model.controllers.StorageUnitController;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductContainer;


/**
 * Controller class for the transfer item batch view.
 */
public class TransferItemBatchController extends Controller implements
		ITransferItemBatchController, Observer {
	
	CoreObjectModel COM;
	ItemController itemController;
	ProductController productController;
	StorageUnitController SU;
	ProductGroupController PGC;
	ProductContainerData _target;
	HashMap<Product, ProductData> productMap;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the transfer item batch view.
	 * @param target Reference to the storage unit to which items are being transferred.
	 */
	public TransferItemBatchController(IView view, ProductContainerData target) {
		super(view);
		COM = CoreObjectModel.getInstance();
		itemController = COM.getItemController();
		productController = COM.getProductController();
		PGC = COM.getProductGroupController();
		SU = COM.getStorageUnitController();
		productMap = new HashMap<Product, ProductData>();
		_target = target;
		construct();
		itemController.addObserver(this);
	}
	
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected ITransferItemBatchView getView() {
		return (ITransferItemBatchView)super.getView();
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
		String barcodeToChange = getView().getBarcode();
		ProductData selectedProduct = getView().getSelectedProduct();
		if(selectedProduct != null)
		{
			getView().setItems(getItemsForView());
		}
		updateTarget();
		ProductContainer container = (ProductContainer)_target.getTag();
		ProductData prodData[] = getProductsForView(container);
		getView().setProducts(prodData);
		if(barcodeToChange.length() == 12) {
			if(itemController.hasItem(barcodeToChange)){
				getView().enableItemAction(true);
			}	
			else{
				getView().displayErrorMessage("The entered barcode is invalid. \""
				+ barcodeToChange +"\" does not exist.");
				getView().setBarcode("");
			}
		}
		if(selectedProduct != null)
		{
			Product p = (Product)selectedProduct.getTag();
			getView().selectProduct(productMap.get(p));
		}
	}
	
	private void updateTarget()
	{
		_target.setTag(SU.getStorageUnitByName(_target.getName()));
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
		String barCode = getView().getBarcode();
		if(barCode.length() == 12)
			loadValues();
		else
			getView().enableItemAction(false);
	}

	/**
	 * This method is called when the "Item Barcode" field in the
	 * transfer item batch view is changed by the user.
	 */
	@Override
	public void barcodeChanged() {
		enableComponents();
	}
	
	/**
	 * This method is called when the "Use Barcode Scanner" setting in the
	 * transfer item batch view is changed by the user.
	 */
	@Override
	public void useScannerChanged() {
	}
	
	/**
	 * This method is called when the selected product changes
	 * in the transfer item batch view.
	 */
	@Override
	public void selectedProductChanged() {
		loadValues();		
	}
	
	/**
	 * This method is called when the user clicks the "Transfer Item" button
	 * in the transfer item batch view.
	 */
	@Override
	public void transferItem() {
		String barcodeOfItemToTransfer = getView().getBarcode();
		ProductContainer container = (ProductContainer)_target.getTag();
		itemController.moveItem(barcodeOfItemToTransfer, container);
		getView().setBarcode("");
//		ProductData selectedProduct = getView().getSelectedProduct();
//		if(selectedProduct!=null)
//		{
//			int count = Integer.parseInt(selectedProduct.getCount());
//			count++; //hack for being able to ignore updating my productdata obj
//			String newCount = Integer.toString(count);
//			selectedProduct.setCount(newCount);
//		}
		//loadValues();
		
	}

	private ProductData[] getProductsForView(ProductContainer container)
	{
		Collection<Product> products = container.getAllProducts();
		ProductData prodData[] = new ProductData[products.size()];
		int i=0;
		for(Product p: products)
		{
			ProductData pd = new ProductData(p);
			ProductContainer product = (ProductContainer)_target.getTag();
			String count = Integer.toString(product.getItemsByProduct(p).size());
			pd.setCount(count);
			prodData[i] = pd;
			productMap.put(p, pd);
			++i;
		}
		return prodData;
	}
	private ItemData[] getItemsForView()
	{
		ProductData selectedProduct = getView().getSelectedProduct();
		ProductContainer pc = (ProductContainer)_target.getTag();
		Collection<Item> items = pc.getAllItems();
		ItemData itemDataObjs[] = new ItemData[items.size()];
		int i=0;
		for(Item it: items)
		{
			ItemData theItem = new ItemData(it);
			itemDataObjs[i] = theItem;
			++i;
		}
		getView().setItems(itemDataObjs);
		return itemDataObjs;
	}
	
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void redo() {
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the transfer item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the transfer item batch view.
	 */
	@Override
	public void done() {
		getView().close();
	}

	@Override
	public void update(Observable o, Object observerHint) {
		//make sure observerHint is instanceof then cast
		//get selected product
//		ProductData selectedProduct = getView().getSelectedProduct();
//		//use product controller and the selected products name to renew the product
//		if(selectedProduct!=null)
//		{
//			Product oldProduct = (Product)selectedProduct.getTag(); 
//			Product updatedProduct =  productController.getProductByBarCode(oldProduct.getBarCode());
//			ProductData updatedProductData = new ProductData(updatedProduct);
//			getView().selectProduct(updatedProductData);
//		}
		
		loadValues();
		//throw new UnsupportedOperationException("Not supported yet.");
	}

}

