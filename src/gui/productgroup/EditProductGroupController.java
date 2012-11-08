package gui.productgroup;

import model.controllers.ProductGroupController;
import model.entities.ProductContainer;
import model.entities.ProductGroup;
import model.entities.Size;
import model.entities.Unit;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the edit product group view.
 */
public class EditProductGroupController extends Controller 
										implements IEditProductGroupController {
	
	private ProductGroup group;
	private ProductGroupController pgController;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to edit product group view
	 * @param target Product group being edited
	 */
	public EditProductGroupController(IView view, ProductContainerData target) {
		super(view);
		group = (ProductGroup) target.getTag();
		pgController = ProductGroupController.getInstance();
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
	protected IEditProductGroupView getView() {
		return (IEditProductGroupView)super.getView();
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
		float supplyVal;
		Unit supplyUnit = getView().getSupplyUnit().toUnit();
		Size size;
		
		try{
			supplyVal = Float.parseFloat(getView().getSupplyValue());
		} catch(Exception e){
			getView().enableOK(false);
			return;
		}
		
		try{
			size = new Size(supplyUnit, supplyVal);
		} catch(Exception e){
			getView().enableOK(false);
			return;
		}
		
		this.group.setThreeMonthSupply(size);
		ProductGroup group = getProductGroup();
		
		if(group == null || !pgController.canEditProductGroup(group, this.group)){
			getView().enableOK(false);
			return;
		}
		
		getView().enableOK(true);
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
		getView().setProductGroupName(group.getName());
		getView().setSupplyUnit(SizeUnits.fromUnit(group.getThreeMonthSupply().getUnits()));
		getView().setSupplyValue(group.getThreeMonthSupply().getSize() + "");
	}
	
	

	//
	// IEditProductGroupController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit product group view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		enableComponents();
	}
	
	private ProductGroup getProductGroup(){
		String name = getView().getProductGroupName();
		Unit units = getView().getSupplyUnit().toUnit();
		String countString = getView().getSupplyValue();
		
		float count;
		if(countString == null || countString.equals("")){
			count = 0;
		}else{
			try{
				count = Float.parseFloat(getView().getSupplyValue());
			}catch(NumberFormatException e){
				count = 0;
			}
		}
		ProductContainer parent = this.group.getContainer();
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
	 * button in the edit product group view.
	 */
	@Override
	public void editProductGroup() {
		ProductGroup group = getProductGroup();
		pgController.editProductGroup(group, this.group);
	}

}

