package gui.batches;

import com.itextpdf.text.Document;
import gui.common.*;
import gui.inventory.*;
import gui.product.*;
import java.io.IOException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import gui.item.ItemData;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.BarCodeGenerator;
import model.CoreObjectModel;
import model.controllers.*;
import model.entities.BarCode;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductGroup;
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
	
	/**
	 * The facade interface to Model.  SIngleton class
	 */
	CoreObjectModel COM;
	
	/**
	 * The facade in charge of Storage Units and moving items
	 */
	StorageUnitController storageUnitController;
	
	/**
	 * The facade in charge of Storage Units and moving items
	 */
	ProductController productController;
	
	/**
	 * The facade in charge of Storage Units and moving items
	 */
	ItemController itemController;
        
        /**
         * list of ItemDatas used by the GUI to show what items were added during 
         * the batch.
         */
        List<ItemData> addedItems;
        
        /**
         * list of ProductDatas used by the GUI to show what products were added
         * during the batch.
         */
        List<ProductData> addedProducts;
        
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
		storageUnitController = COM.getStorageUnitController();
		productController = COM.getProductController();
		itemController = COM.getItemController();

                addedItems = new ArrayList<ItemData>();
                addedProducts = new ArrayList<ProductData>();
                
		construct();
		getView().enableItemAction(false);
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
		getView().enableItemAction(itemController.enableAddItem(count, 
                        entryDate, barCode));
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
		enableComponents();
	}

	/**
	 * This method is called when the "Use Barcode Scanner" setting in the
	 * add item batch view is changed by the user.
	 */
	@Override
	public void useScannerChanged() {
		//
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
                
		String nameOfProductContainer = _target.getName();
		BarCode productBarcode = new BarCode(getView().getBarcode());
		
		//assert(productBarcode.isValid());
		
		Product product = productController.getProductByBarCode(productBarcode);
		if(product == null) {		
			//open the other dialogue box to prompt for a new product.
			getView().displayAddProductView();
			product = productController.getProductByBarCode(productBarcode);
			assert(product != null);
			assert(product.getBarCode().isValid());
		}
		//we have a good product now
		ProductData prodData = new ProductData(product);
                
		StorageUnit storageUnit = 
                        storageUnitController.getStorageUnitByName(nameOfProductContainer);

		int count = Integer.parseInt(getView().getCount());
		for(; count > 0; --count)
		{
			Date entryDate = getView().getEntryDate();

			BarCode itemBarcode = BarCodeGenerator.getInstance().generateBarCode();
			
			assert(Item.canCreate(itemBarcode, entryDate, null, 
                                product, storageUnit));
			
			Item i = new Item(itemBarcode, entryDate, null, product, storageUnit);
			
			itemController.addItem(i, storageUnit);
                        
                        addItemData(new ItemData(i), storageUnit);
                        addProductData(prodData);
		}
                
                getView().setProducts(getAddedProducts());
                getView().selectProduct(prodData);
                selectedProductChanged();
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
                addedProducts.add(prodData);
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
	}

	/**
	 * This method is called when the user clicks the "Undo" button
	 * in the add item batch view.
	 */
	@Override
	public void undo() {
	}

	/**
	 * This method is called when the user clicks the "Done" button
	 * in the add item batch view.
	 */
	@Override
	public void done() {
		itemController.printItemLabelsOnAddBatchClose();
	}
	
}

