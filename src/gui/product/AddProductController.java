package gui.product;

import gui.common.*;
import model.CoreObjectModel;
import model.controllers.ProductController;
import model.entities.BarCode;
import model.entities.Product;
import model.entities.Size;
import model.entities.Unit;

/**
 * Controller class for the add item view.
 */
public class AddProductController extends Controller implements
		IAddProductController {
	    
        String barcode;
    
        /**
	 * The facade interface to Model.  SIngleton class
	 */
	CoreObjectModel COM;
    
        /**
	 * The facade in charge of Storage Units and moving items
	 */
        ProductController productController;
    
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add product view
	 * @param barcode Barcode for the product being added
	 */
	public AddProductController(IView view, String barcode) {
		super(view);
		this.barcode = barcode;
                COM = CoreObjectModel.getInstance();
                productController = COM.getProductController();
                
		construct();
                loadValues();
	}

	//
	// Controller overrides
	//
        
	/**
	 * Returns a reference to the view for this controller.
	 * 
	 * {@pre None}
	 * 
	 * {@post Returns a reference to the view for this controller.}
	 */
	@Override
	protected IAddProductView getView() {
		return (IAddProductView)super.getView();
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
            getView().enableOK(false);
            getView().setBarcode(barcode);
            if(getView().getSizeUnit() == SizeUnits.Count){
                getView().setSizeValue("1");
                getView().enableSizeValue(false);
            }
	}

	//
	// IAddProductController overrides
	//
	
	/**
	 * This method is called when any of the fields in the
	 * add product view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
            BarCode barCode = new BarCode(barcode);
            String description = getView().getDescription();
            float sizeVal;
            int supply;
            int shelfLife;
            boolean canAddProduct = true;
            
            try {
               sizeVal = Float.parseFloat(getView().getSizeValue()); 
            }
            catch(Exception e) {
                sizeVal = 0;
                canAddProduct = false;
            }
            
            try {
                shelfLife = Integer.parseInt(getView().getShelfLife());
            }
            catch(Exception e) {
                shelfLife = 0;
                canAddProduct = false;
            }
            
            try {
                supply = Integer.parseInt(getView().getSupply());
            }
            catch(Exception e) {
                supply = 0;
                canAddProduct = false;
            }
            
            Size size = new Size(getView().getSizeUnit().toUnit(), sizeVal);
            
            if(size.getUnits() == Unit.count){
                getView().setSizeValue("1");
                getView().enableSizeValue(false);
            } else {
                getView().enableSizeValue(true);
            }
            
            if(canAddProduct == false){
                getView().enableOK(false);
                return;
            }
            
            if(Product.canCreate(description, barCode, shelfLife, supply, size)){
                getView().enableOK(true);
            } else {
                getView().enableOK(false);
            }
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the add product view.
	 */
	@Override
	public void addProduct() {
            BarCode barCode = new BarCode(barcode);
            String description = getView().getDescription();
            float sizeVal = Float.parseFloat(getView().getSizeValue());
            int supply = Integer.parseInt(getView().getSupply());
            int shelfLife = Integer.parseInt(getView().getShelfLife());
            
            Size size = new Size(getView().getSizeUnit().toUnit(), sizeVal);
            
            Product product = new Product(description, barCode, shelfLife, supply, size);

            productController.addProduct(product);
	}
        
}

