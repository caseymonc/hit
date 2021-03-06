package gui.reports.removed;

import java.util.Date;

import model.CoreObjectModel;

import reports.directors.RemovedItemsDirector;
import gui.common.*;
import gui.reports.Builder;
import gui.reports.HtmlBuilder;
import gui.reports.PdfBuilder;

/**
 * Controller class for the removed items report view.
 */
public class RemovedReportController extends Controller implements
		IRemovedReportController {

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the removed items report view
	 */
	public RemovedReportController(IView view) {
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
	protected IRemovedReportView getView() {
		return (IRemovedReportView) super.getView();
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
		if(CoreObjectModel.getInstance().hasSinceDate()){
			getView().setSinceLast(true);
			getView().setSinceLastValue(CoreObjectModel.getInstance().getSinceDate());
		}else{
			getView().setSinceDate(true);
			getView().enableSinceLast(false);
		}
	}

	//
	// IExpiredReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * removed items report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		if(getView().getSinceDate()){
			getView().enableSinceDateValue(true);
		}else{
			getView().enableSinceDateValue(false);
		}
	}

	/**
	 * This method is called when the user clicks the "OK"
	 * button in the removed items report view.
	 */
	@Override
	public void display() {
		RemovedItemsDirector director = new RemovedItemsDirector(getBuilder(), getDate());
		director.createReport();
		director.displayReport();
	}
	
	private Date getDate() {
		if(getView().getSinceDate()){
			if(!CoreObjectModel.getInstance().hasSinceDate()){
				CoreObjectModel.getInstance().setSinceDate(getView().getSinceDateValue());
			}
			return getView().getSinceDateValue();
		}else if(getView().getSinceLast()){
			return CoreObjectModel.getInstance().useSinceDate();
		}
		return null;
	}

	public Builder getBuilder(){
		if(getView().getFormat() == FileFormat.HTML){
			return new HtmlBuilder("Removed Items Report");
		}else{
			return new PdfBuilder("Removed Items Report");
		}
	}

}

