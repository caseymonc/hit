package gui.reports.supply;

import gui.common.*;
import gui.reports.Builder;
import gui.reports.HtmlBuilder;
import gui.reports.PdfBuilder;
import reports.directors.SupplyDirector;

/**
 * Controller class for the N-month supply report view.
 */
	public class SupplyReportController extends Controller implements
		ISupplyReportController {
/*---	STUDENT-INCLUDE-BEGIN

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the N-month supply report view
	 */	
	public SupplyReportController(IView view) {
		super(view);
		
		getView().setMonths("3");
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
	protected ISupplyReportView getView() {
		return (ISupplyReportView)super.getView();
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
		try {
			int months = Integer.parseInt(getView().getMonths());
			if (months >= 1 && months <= 100) {
				getView().enableOK(true);
			} else {
				getView().enableOK(false);
			}
		} catch(NumberFormatException e) {
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
		
	}

	//
	// IExpiredReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * N-month supply report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the N-month supply report view.
	 */
	@Override
	public void display() {
		SupplyDirector director = new SupplyDirector(getBuilder(), getMonths());
		director.createReport();
		director.displayReport();
	}
	
	private int getMonths(){
		return Integer.parseInt(getView().getMonths());
	}

	public Builder getBuilder(){
		if(getView().getFormat() == FileFormat.HTML){
			return new HtmlBuilder(getMonths() + "-Month-Supply");
		}else{
			return new PdfBuilder(getMonths() + "-Month-Supply");
		}
	}
}

