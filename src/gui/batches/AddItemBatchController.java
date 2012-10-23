package gui.batches;

import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.Timer;
import model.BarCodeGenerator;
import model.Command;
import model.CommandManager;
import model.CoreObjectModel;
import model.controllers.*;
import model.entities.BarCode;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductContainer;
import model.entities.StorageUnit;


/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends Controller implements
		IAddItemBatchController {
	
	

	/**
	 *  The target for which product container was selected
	 */
	private ProductContainerData _target;
	private CommandManager commandManager;
	
	/**
	 * The facade interface to Model.  SIngleton class
	 */
	private CoreObjectModel COM;
	
	/**
	 * The facade in charge of Storage Units and moving items
	 */
	private ProductController productController;
	
	/**
	 * The facade in charge of Storage Units and moving items
	 */
	private ItemController itemController;
		
	/**
	 * list of ItemDatas used by the GUI to show what items were added during 
	 * the batch.
	 */
	private List<ItemData> addedItems;

	/**
	 * list of ProductDatas used by the GUI to show what products were added
	 * during the batch.
	 */
	private List<ProductData> addedProducts;
	 
	/**
	 * the timer needed to track barcode scanner induced add item 
	 */
	private Timer timer;
	
	/**
	 * Barcode for use to check if the barcode actually changed
	 */
	private String previousBarCode;
	
	private final String INVALID_SCAN = "The scanned Barcode was read incorrectly. Please Rescan";
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add item batch view.
	 * @param target Reference to the storage unit to which items are being added.
	 */
	public AddItemBatchController(IView view, ProductContainerData target) {
		super(view);
		
		_target = target;
		COM = CoreObjectModel.getInstance();
		productController = COM.getProductController();
		itemController = COM.getItemController();
		commandManager = new CommandManager();
		addedItems = new ArrayList<ItemData>();
		addedProducts = new ArrayList<ProductData>();
				
		construct();

		timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				timer.stop();
				if(isValidEntry()) {
					addItem();
				}
			}
		});
		timer.setInitialDelay(1000);
		setFieldsToDefault();
	}
	/**
	 * 
	 */
	private void setFieldsToDefault() {
		getView().enableItemAction(false);
		getView().setUseScanner(true);
		getView().setCount("1");
		getView().enableUndo(false);
		getView().enableRedo(false);
		getView().setBarcode("");
		getView().setEntryDate(new Date());
	}
	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IAddItemBatchView getView() {
		return (IAddItemBatchView) super.getView();
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
		String count = getView().getCount();
		String barCode = getView().getBarcode(); //this is the product barcode
		Date entryDate = getView().getEntryDate();
		getView().enableItemAction(itemController.enableAddItem(count, entryDate, barCode));
		
		getView().enableUndo(commandManager.canUndo());
		getView().enableRedo(commandManager.canRedo());
		
	}
	
	/**
	 * This method is called when the "Entry Date" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void entryDateChanged() {
		enableComponents();
	}

	/**
	 * This method is called when the "Count" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void countChanged() {
		enableComponents();
	}

	/**
	 * This method is called when the "Product Barcode" field in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void barcodeChanged() {
		if(getView().getUseScanner()) {
			//start
			if(timer.isRunning()) {
				timer.restart();
			} else {
				timer.start();
			}
		}
		enableComponents();
	}
	
	/**
	 * 
	 */
	private boolean isValidEntry() {
			int count;
 		
			if(getView().getBarcode().equals("")) {
				return false;
			}

			try{
				count = Integer.parseInt(getView().getCount());
			}
			catch(Exception e){
				count = 0;
			}

			if(count < 1){
				return false;
			}

			return true;
	}
	/**
	 * This method is called when the "Use Barcode Scanner" setting in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void useScannerChanged() {
		
	}

	/**
	 * This method is called when the selected product changes
	 * in the add item batch view.
	 */
	@Override
	public void selectedProductChanged() {
		ProductData selectedProduct = getView().getSelectedProduct();
				
		if(selectedProduct != null){
			getView().setItems(getAddedItemsByProduct(selectedProduct));
		}else{
			getView().setItems(new ItemData[0]);
		}
	}

	/**
	 * This method is called when the user clicks the "Add Item" button
	 * in the add item batch view.
	 * note: the only time we can actually get here is if all of the fields in 
		 *  the AddItemBatchView are correct
	 */
	@Override
	public void addItem() {
		//Get the Product
		BarCode productBarcode = new BarCode(getView().getBarcode());
		Product addedProduct = productController.getProductByBarCode(productBarcode);
		if(addedProduct == null) {		
			//open the other dialogue box to prompt for a new product.
			getView().displayAddProductView();
			addedProduct = productController.getProductByBarCode(productBarcode);
			assert(addedProduct != null);
			assert(addedProduct.getBarCode().isValid());
		}
		final Product product = addedProduct;
		
		//Get Items
		final List<ItemData> addedItemData = new ArrayList<ItemData>();
		final List<Item> addedItems = new ArrayList<Item>();
		int itemCount = Integer.parseInt(getView().getCount());
		final int countF = itemCount;
		ProductContainer container = (ProductContainer) _target.getTag();
		final ProductContainer addedContainer = container;
		final StorageUnit storageUnit = container.getStorageUnit();
		for(; itemCount > 0; --itemCount)
		{
			Date entryDate = getView().getEntryDate();
			BarCode itemBarcode = BarCodeGenerator.getInstance().generateBarCode();
			int shelfLife = product.getShelfLife();			
			Calendar calen = Calendar.getInstance();
			calen.add(Calendar.MONTH, shelfLife);
			Date expirationDate = calen.getTime();
			assert(Item.canCreate(itemBarcode, entryDate, expirationDate, 
								product, storageUnit));
			
			Item i = new Item(itemBarcode, entryDate, expirationDate, product, storageUnit);
			addedItems.add(i);
			addedItemData.add(new ItemData(i));
		}
		final ProductData prodData = new ProductData(product);
		Command command = new Command(){
			@Override
			public void doAction() {
				//we have a good product now
				for(int i = 0; i < addedItems.size(); i++)
				{
					Item item = addedItems.get(i);
					ItemData iData = addedItemData.get(i);
					itemController.addItem(item, storageUnit);
					addItemData(iData, storageUnit);
					addProductData(prodData);
				}
						
				getView().setProducts(getAddedProducts());
				getView().selectProduct(prodData);
				selectedProductChanged();
				setFieldsToDefault();
			}

			@Override
			public void undoAction() {
				for(int i = 0; i < addedItems.size(); i++){
					Item item = addedItems.get(i);
					ItemData iData = addedItemData.get(i);
					deleteItem(item);
					removeItemData(iData, prodData);
				}
				
				getView().setProducts(getAddedProducts());
				if(getAddedProducts().length != 0)
					getView().selectProduct(prodData);
				selectedProductChanged();
				setFieldsToDefault();
			}

			private void deleteItem(Item item) {
				itemController.deleteItem(item);
			}

			private void removeProduct() {
				if(productController.canRemoveProduct(product)){
					productController.removeProduct(product);
				}else if(productController
						.canRemoveProductFromContainer(product, addedContainer)){
					productController.removeProductFromContainer(product, addedContainer);
				}
			}
			
		};
		commandManager.doAction(command);
		this.enableComponents();
	}
		
	private void removeItemData(ItemData iData, ProductData prodData) {
		if(addedProducts.contains(prodData)){
			int index = addedProducts.indexOf(prodData);
			int count;

			try {
				count = Integer.parseInt(addedProducts.get(index).getCount());
			}catch (Exception e) {
				count = 0;
			}
			
			count--;
			addedProducts.get(index).setCount(Integer.toString(count));
			
			if(count == 0){
				addedProducts.remove(index);
			}
		}
		
		addedItems.remove(iData);
	}
	
	private void addProductData(ProductData prodData){
		if(addedProducts.contains(prodData)){
			int index = addedProducts.indexOf(prodData);
			int count;

			try {
				count = Integer.parseInt(addedProducts.get(index).getCount());
			}
			catch (Exception e) {
				count = 0;
			}

			count++;
			addedProducts.get(index).setCount(Integer.toString(count));
		} else {
			prodData.setCount("1");
			addedProducts.add(prodData);
		}
	}
	
	private void removeProductData(ProductData prodData){
		int index = addedProducts.indexOf(prodData);
		int count;

		try {
			count = Integer.parseInt(addedProducts.get(index).getCount());
		}
		catch (Exception e) {
			count = 0;
		}

		count--;
		addedProducts.get(index).setCount(Integer.toString(count));
		
		if(count == 0){
			addedProducts.remove(index);
		}
	}

	private void addItemData(ItemData itemData, StorageUnit storageUnit) {
		itemData.setStorageUnit(storageUnit.getName());
		addedItems.add(itemData);
	}

	private ProductData[] getAddedProducts() {
		return addedProducts.toArray(new ProductData[addedProducts.size()]);
	}

	private ItemData[] getAddedItemsByProduct(ProductData prodData) {
		List<ItemData> itemDatas = new ArrayList<ItemData>();

		for(ItemData i : addedItems) {
			Item item = (Item)i.getTag();
			String barcode = item.getProduct().getBarCode().getBarCode();
			if(barcode.equals(prodData.getBarcode())) {
				itemDatas.add(i);
			}
		}

		return itemDatas.toArray(new ItemData[itemDatas.size()]);
	}
		
	/**
	 * This method is called when the user clicks the "Redo" button
	 * in the add item batch view.
	 */
	@Override
	public void redo() {
		if(commandManager.canRedo()){
			commandManager.redo();
		}
		enableComponents();
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the add item batch view.
	 */
	@Override
	public void undo() {
		if(commandManager.canUndo()){
			commandManager.undo();
		}
		enableComponents();
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the add item batch view.
	 */
	@Override
	public void done() {
		itemController.printItemLabelsOnAddBatchClose(addedItems);
		getView().close();
	}
	
}

