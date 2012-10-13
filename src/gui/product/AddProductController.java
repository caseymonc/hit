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
         * flag to determine the previous unit of the size.
         */
        private boolean unitIsCount;
        
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
                unitIsCount = false;
                
		construct();
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
            String description = getView().getDescription();
            String sizeVal = getView().getSizeValue();
            Unit sizeUnit = getView().getSizeUnit().toUnit();
            String supply = getView().getSupply();
            String shelfLife = getView().getShelfLife();
            
            getView().enableBarcode(false);
            
            if(getView().getSizeUnit() == SizeUnits.Count){
                getView().enableSizeValue(false);
            } else {
                getView().enableSizeValue(true);
            }
            
            if(productController.canAddProduct(barcode, description, sizeVal, 
                    sizeUnit, supply, shelfLife)){
                getView().enableOK(true);
            } else {
                getView().enableOK(false);
            }
            
            
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
            getView().setBarcode(barcode);
            getView().setShelfLife("0");
            getView().setSupply("0");
            valuesChanged();
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
            if(getView().getSizeUnit() == SizeUnits.Count){
                unitIsCount = true;
                getView().setSizeValue("1");
            } else if(unitIsCount == true) {
                unitIsCount = false;
                getView().setSizeValue("0");
            }
            
            enableComponents();
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
            
            try{
                productController.addProduct(product);
            }
            catch(Exception e){
                getView().displayErrorMessage("The product could not be added.");
            }
	}
        
}

