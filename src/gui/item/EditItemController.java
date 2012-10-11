package gui.item;

import gui.common.*;
import java.util.Calendar;
import java.util.Date;
import model.CoreObjectModel;
import model.controllers.ItemController;
import model.entities.Item;

/**
 * Controller class for the edit item view.
 */
public class EditItemController extends Controller implements IEditItemController 
{
	/**
	 * The item that was selected when Edit was clicked
	 */
	ItemData _target;
	
	/**
	 * the facade to the Model
	 */
	CoreObjectModel COM;
	
	/**
	 * The model's controller
	 */
	ItemController IC;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to edit item view
	 * @param target Item that is being edited
	 */
	public EditItemController(IView view, ItemData target) {
		super(view);
		_target = target;
		getView().enableDescription(false);
		getView().enableBarcode(false);
		construct();
		COM = CoreObjectModel.getInstance();
		IC = COM.getItemController();
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
	protected IEditItemView getView() {
		return (IEditItemView)super.getView();
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
		getView().enableOK(this.canClickOk());
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
		Item i = (Item) this._target.getTag();
		getView().setDescription(i.getProduct().getDescription());
		getView().setBarcode(_target.getBarcode());
		this.enableComponents();
	}

	//
	// IEditItemController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * edit item view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		this.enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the edit item view.
	 */
	@Override
	public void editItem() {
		assert(this.canClickOk());
		Item i = (Item) this._target.getTag();
		IC.updateItemsEntryDate(i, this.getView().getEntryDate());		
	}
	
	private boolean canClickOk() {
		Date d = getView().getEntryDate();
		
		//cannot be a null date
		if(d == null) {
			return false;
		}
		
		//cannot be in the future
		if(d.after(new Date())) {
			System.out.println("future");
			return false;
		}
		//has to be after Jan. 1 2000
		Calendar c = Calendar.getInstance();
		c.set(2000,0,0);
		if(d.before(c.getTime())) {
			System.out.println("before 2000" + c.toString());
			return false;
		}
		
		return true;
	}
}

