package gui.storageunit;

import model.controllers.StorageUnitController;
import model.entities.StorageUnit;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the edit storage unit view.
 */
public class EditStorageUnitController extends Controller 
										implements IEditStorageUnitController {
	
	private StorageUnit unit;
	private StorageUnitController suController;

	/**
	 * Constructor.
	 * 
	 * @param view Reference to edit storage unit view
	 * @param target The storage unit being edited
	 */
	public EditStorageUnitController(IView view, ProductContainerData target) {
		super(view);
		unit = (StorageUnit)target.getTag();
		suController = StorageUnitController.getInstance();
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
	protected IEditStorageUnitView getView() {
		return (IEditStorageUnitView)super.getView();
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
		getView().setStorageUnitName(unit.getName());
		valuesChanged();
	}

	//
	// IEditStorageUnitController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit storage unit view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		StorageUnit unit = getStorageUnit();
		if(suController.canEditStorageUnit(unit, this.unit)){
			getView().enableOK(true);
		}else{
			getView().enableOK(false);
		}
	}
	
	public StorageUnit getStorageUnit(){
		String name = getView().getStorageUnitName();
		StorageUnit unit = null;
		try{
			unit = new StorageUnit(name);
			return unit;
		}catch(IllegalArgumentException e){
			return null;
		}
	}

	/**
	 * This method is called when the user clicks the "OK"
	 * button in the edit storage unit view.
	 */
	@Override
	public void editStorageUnit() {
		StorageUnit unit = getStorageUnit();
		suController.editStorageUnit(unit, this.unit);
	}

}

