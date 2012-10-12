package gui.batches;

import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;
import java.util.Collection;
import model.CoreObjectModel;
import model.controllers.ItemController;
import model.controllers.ProductController;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductContainer;


/**
 * Controller class for the transfer item batch view.
 */
public class TransferItemBatchController extends Controller implements
		ITransferItemBatchController {
	
	CoreObjectModel COM;
	ItemController itemController;
	ProductController productController;
	ProductContainerData _target;
	
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
		_target = target;
		construct();
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
		ProductContainer container = (ProductContainer)_target.getTag();
		ProductData prodData[] = getProductsForView(container);
		getView().setProducts(prodData);
		if(barcodeToChange.length() == 12) {
			if(itemController.hasItem(barcodeToChange)){
				getView().enableItemAction(true);
			}	
			else{
				getView().displayErrorMessage("The entered barcode does not exist.");
			}
		}
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
		ProductData prodData[] = getProductsForView(container);
		getView().setProducts(prodData);
		//getView().setItems(items);
		System.out.println("This is the name of the container transfering to:"+container.getName());
		itemController.moveItem(barcodeOfItemToTransfer, container);
		loadValues();
	}

	private ProductData[] getProductsForView(ProductContainer container)
	{
		Collection<Product> products = container.getAllProducts();
		ProductData prodData[] = new ProductData[products.size()];
		int i=0;
		for(Product p: products)
		{
			ProductData pd = new ProductData(p);
			prodData[i] = pd;
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

}

