package gui.storageunit;

import model.CoreObjectModel;
import model.controllers.StorageUnitController;
import model.entities.StorageUnit;
import gui.common.*;

/**
 * Controller class for the add storage unit view.
 */
public class AddStorageUnitController extends Controller implements
		IAddStorageUnitController {
	
	private StorageUnitController suController;
	/**
	 * Constructor.
	 * 
	 * @param view Reference to add storage unit view
	 */
	public AddStorageUnitController(IView view) {
		super(view);
		CoreObjectModel COM = CoreObjectModel.getInstance();
		suController = COM.getStorageUnitController();
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
	protected IAddStorageUnitView getView() {
		return (IAddStorageUnitView)super.getView();
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
	// IAddStorageUnitController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * add storage unit view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		StorageUnit unit = getStorageUnit();
		if(suController.canAddStorageUnit(unit)){
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
	 * button in the add storage unit view.
	 */
	@Override
	public void addStorageUnit() {
		StorageUnit unit = getStorageUnit();
		suController.addStorageUnit(unit);
	}

}

