package gui.batches;

import gui.common.*;
import gui.inventory.*;
import gui.product.*;
import model.CoreObjectModel;
import model.controllers.ItemController;
import model.entities.Product;



/**
 * Controller class for the transfer item batch view.
 */
public class TransferItemBatchController extends Controller implements
		ITransferItemBatchController {
	
	CoreObjectModel COM;
	ItemController itemController;
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
		if(itemController.hasItem(barcodeToChange)){
			getView().enableItemAction(true);
		}	
		else{
			getView().displayErrorMessage("The entered barcode does not exist.");
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
	}
	
	/**
	 * This method is called when the user clicks the "Transfer Item" button
	 * in the transfer item batch view.
	 */
	@Override
	public void transferItem() {
		String barcodeOfItemToTransfer = getView().getBarcode();
		ProductData productContainerToPutItemIn = getView().getSelectedProduct();
		//How do I change ProductData item to a product? Or do I just grab the name
		//from the productData and change the move item to accept a name to find a 
		//product?
		itemController.moveItem(barcodeOfItemToTransfer, null);
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

