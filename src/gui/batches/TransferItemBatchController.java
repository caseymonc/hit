package gui.batches;

import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import model.Command;
import model.CommandManager;
import model.CoreObjectModel;
import model.controllers.ItemController;
import model.controllers.ProductController;
import model.controllers.ProductGroupController;
import model.controllers.StorageUnitController;
import model.entities.Item;
import model.entities.ProductContainer;


/**
 * Controller class for the transfer item batch view.
 */
public class TransferItemBatchController extends Controller implements 
        ITransferItemBatchController{
	
	private CommandManager commandManager;
	private CoreObjectModel COM;
	private ItemController itemController;
	private StorageUnitController SU;
	private ProductContainerData target;
	private ArrayList<ProductData> addedProducts;
	private ArrayList<ItemData> addedItems;
        
    /**
	 * the timer needed to track barcode scanner induced add item 
	 */
	private Timer timer;
        
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
            addedProducts = new ArrayList<ProductData>();
            addedItems = new ArrayList<ItemData>();
            commandManager = new CommandManager();
            this.target = target;

            timer = new Timer(100, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    timer.stop();
                    if(getView().getBarcode().equals("") == false) {
                        transferItem();
                    }
                }
            });
            timer.setInitialDelay(1000);

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
            getView().setUseScanner(true);
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
	    if(getView().getBarcode().equals("")){
	        getView().enableItemAction(false);
	    } else {
	        getView().enableItemAction(true);
	    }
	    
	    getView().enableUndo(commandManager.canUndo());
		getView().enableRedo(commandManager.canRedo());
	}

	/**
	 * This method is called when the "Item Barcode" field in the
	 * transfer item batch view is changed by the user.
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
            if(getView().getSelectedProduct() != null){
                getView().setItems(getItemsForView());
            }
	}
	
	/**
	 * This method is called when the user clicks the "Transfer Item" button
	 * in the transfer item batch view.
	 */
	@Override
	public void transferItem() {
            if(itemController.hasItem(getView().getBarcode()) == false){
                getView().displayErrorMessage("The specified item does not exist.");
                getView().setBarcode("");
                enableComponents();
                return;
            }
            String barcodeOfItemToTransfer = getView().getBarcode();
            final Item item = itemController.getItemByBarCode(barcodeOfItemToTransfer);;
            Command command = new Command(){
            	private ProductContainer oldContainer;
				@Override
				public void doAction() {
					oldContainer = item.getContainer();
					
		            ProductContainer container = (ProductContainer)target.getTag();
		            itemController.moveItem(item.getBarCode().getBarCode(), container);
		            addItemToView(item);
				}

				@Override
				public void undoAction() {
		            itemController.moveItem(item.getBarCode().getBarCode(), oldContainer);		            
		            removeItemFromView(item);
				}
            	
            };
            commandManager.doAction(command);
    		this.enableComponents(); 
	}
	
	private void removeItemFromView(Item item){
		ProductData selectedProduct = getView().getSelectedProduct();
		ProductContainer container = (ProductContainer)target.getTag();
		addedItems.remove(item);
		
		ProductData productData = new ProductData(item.getProduct());
        int index = addedProducts.indexOf(productData);
        addedProducts.get(index).decramentCount();
        
        if(addedProducts.get(index).getCount().equals("0")){
        	addedProducts.remove(index);
        }
		
        ProductData prodData[] = getProductsForView(container);
        getView().setProducts(prodData);
        getView().setBarcode("");
        enableComponents();
        getView().selectProduct(selectedProduct);
        selectedProductChanged();
	}
	
	private void addItemToView(Item item){
		ProductData selectedProduct = getView().getSelectedProduct();
		ProductContainer container = (ProductContainer)target.getTag();
		ItemData itemData = new ItemData(item);
        
        if(addedItems.contains(itemData) == false){
            addedItems.add(itemData);
        }
        
        ProductData productData = new ProductData(item.getProduct());
        
        if(addedProducts.contains(productData)){
            int index = addedProducts.indexOf(productData);
            addedProducts.get(index).incrementCount();                
        } else {
            addedProducts.add(productData);
        }
        
        ProductData prodData[] = getProductsForView(container);
        getView().setProducts(prodData);
        getView().setBarcode("");
        enableComponents();
        getView().selectProduct(selectedProduct);
        selectedProductChanged();
	}

	private ProductData[] getProductsForView(ProductContainer container)
	{
		return addedProducts.toArray(new ProductData[addedProducts.size()]);
	}
        
	private ItemData[] getItemsForView()
	{
            return addedItems.toArray(new ItemData[addedItems.size()]);
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
	 * in the transfer item batch view.
	 */
	@Override
	public void done() {
            getView().close();
	}
}

