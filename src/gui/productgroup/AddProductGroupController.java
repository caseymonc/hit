package gui.productgroup;

import model.controllers.ProductGroupController;
import model.controllers.StorageUnitController;
import model.entities.ProductContainer;
import model.entities.ProductGroup;
import model.entities.Size;
import model.entities.StorageUnit;
import model.entities.Unit;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the add product group view.
 */
public class AddProductGroupController extends Controller implements
		IAddProductGroupController {
	private ProductGroupController pgController;
	private StorageUnitController suController;
	private ProductContainerData container;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to add product group view
	 * @param container Product container to which the new product group is being added
	 */
	public AddProductGroupController(IView view, ProductContainerData container) {
		super(view);
		pgController = ProductGroupController.getInstance();
		suController = StorageUnitController.getInstance();
		this.container = container;
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
	protected IAddProductGroupView getView() {
		return (IAddProductGroupView)super.getView();
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
		valuesChanged();
	}

	//
	// IAddProductGroupController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * add product group view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		ProductGroup group = getProductGroup();
		ProductContainer container = (ProductContainer)this.container.getTag();
		if(pgController.canAddProductGroup(group, container)){
			getView().enableOK(true);
		}else{
			getView().enableOK(false);
		}
	}
	
	private ProductGroup getProductGroup(){
		String name = getView().getProductGroupName();
		Unit units = getView().getSupplyUnit().toUnit();
		String countString = getView().getSupplyValue();
		
		float count;
		if(countString == null || countString.equals("")){
			return null;
		}else{
			try{
				count = Float.parseFloat(getView().getSupplyValue());
			}catch(NumberFormatException e){
				return null;
			}
		}
		ProductContainer parent = (ProductContainer)container.getTag();
		ProductGroup group = null;
		try{
			group = new ProductGroup(name, parent, new Size(units, count));
			return group;
		}catch(IllegalArgumentException e){
			return null;
		}
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the add product group view.
	 */
	@Override
	public void addProductGroup() {
		ProductGroup group = getProductGroup();
		pgController.addProductGroup(group, group.getContainer());
	}

}

