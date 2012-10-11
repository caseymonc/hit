package gui.product;

import gui.common.*;
import model.CoreObjectModel;
import model.controllers.ProductController;
import model.entities.BarCode;
import model.entities.Product;
import model.entities.Size;
import model.entities.Unit;

/**
 * Controller class for the edit product view.
 */
public class EditProductController extends Controller implements IEditProductController {
    
        /**
	 * The facade interface to Model.  SIngleton class
	 */
	private CoreObjectModel COM;
    
        /**
	 * The facade in charge of Storage Units and moving items
	 */
        private ProductController productController;
        
        /**
         * The productData given by the view
         */
	private ProductData productData;
        
        /** 
         * flag to determine the previous unit of the size.
         */
        private boolean unitIsCount;
        
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the edit product view
	 * @param target Product being edited
	 */
	public EditProductController(IView view, ProductData target) {
		super(view);
                
                COM = CoreObjectModel.getInstance();
                productController = COM.getProductController();
                productData = target;
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
	protected IEditProductView getView() {
		return (IEditProductView)super.getView();
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
            String barcode = getView().getBarcode();
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
            
            if(productController.canEditProduct(barcode, description, sizeVal, 
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
            Product product = (Product)productData.getTag();
            String shelfLife = Integer.toString(product.getShelfLife());
            String supply = Integer.toString(product.getThreeMonthSupply());
            SizeUnits sizeUnit = SizeUnits.fromUnit(product.getSize().getUnits());

            String sizeValue;
            try {
                float f = product.getSize().getSize();
                int i = (int) f;
                sizeValue = (i == f) ? String.valueOf(i) : String.valueOf(f);
            }
            catch (Exception e) {
                sizeValue = "";
            }
            
            getView().setBarcode(productData.getBarcode());
            getView().setDescription(productData.getDescription());
            getView().setSizeValue(sizeValue);
            getView().setSizeUnit(sizeUnit);
            getView().setShelfLife(shelfLife);
            getView().setSupply(supply);
            
            valuesChanged();
	}

	//
	// IEditProductController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit product view is changed by the user.
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
	 * button in the edit product view.
	 */
	@Override
	public void editProduct() {
            BarCode barCode = new BarCode(getView().getBarcode());
            String description = getView().getDescription();
            float sizeVal = Float.parseFloat(getView().getSizeValue());
            int supply = Integer.parseInt(getView().getSupply());
            int shelfLife = Integer.parseInt(getView().getShelfLife());
            
            Size size = new Size(getView().getSizeUnit().toUnit(), sizeVal);
            
            Product product = new Product(description, barCode, shelfLife, supply, size);
            
            try{
                productController.EditProduct(barCode, product);
            }
            catch(IllegalArgumentException e){
                getView().displayErrorMessage("Sorry but the product could not be edited.");
            }
	}

}

