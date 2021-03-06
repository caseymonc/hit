package gui.reports.expired;


import reports.directors.ExpiredItemDirector;
import gui.common.*;
import gui.reports.Builder;
import gui.reports.HtmlBuilder;
import gui.reports.PdfBuilder;


/**
 * Controller class for the expired items report view.
 */
public class ExpiredReportController extends Controller implements
										IExpiredReportController {

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the expired items report view
	 */
	public ExpiredReportController(IView view) {
		super(view);

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
	protected IExpiredReportView getView() {
		return (IExpiredReportView)super.getView();
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
	}

	//
	// IExpiredReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * expired items report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the expired items report view.
	 */
	@Override
	public void display() {
		ExpiredItemDirector director = new ExpiredItemDirector(getBuilder());
		director.createReport();
		director.displayReport();
	}
	
	public Builder getBuilder(){
		if(getView().getFormat() == FileFormat.HTML){
			return new HtmlBuilder("Expired Items Report");
		}else{
			return new PdfBuilder("Expired Items Report");
		}
	}

}

