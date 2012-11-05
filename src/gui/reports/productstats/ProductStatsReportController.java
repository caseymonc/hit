package gui.reports.productstats;

import gui.common.*;
import gui.reports.Builder;
import gui.reports.HtmlBuilder;
import gui.reports.PdfBuilder;
import java.util.Calendar;
import java.util.Date;
import reports.directors.ProductStatsDirector;

/**
 * Controller class for the product statistics report view.
 */
public class ProductStatsReportController extends Controller implements
		IProductStatsReportController {

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the item statistics report view
	 */
	public ProductStatsReportController(IView view) {
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
	protected IProductStatsReportView getView() {
		return (IProductStatsReportView)super.getView();
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
		try{
			int months = Integer.parseInt(getView().getMonths());
			if(months < 1 || months > 100){
				getView().enableOK(false);
			} else {
				getView().enableOK(true);
			}
		}
		catch(Exception e){
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
		getView().setMonths("3");
		enableComponents();
	}

	//
	// IProductStatsReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * product statistics report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		enableComponents();
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the product statistics report view.
	 */
	@Override
	public void display() {
		FileFormat format = getView().getFormat();
		
		Builder builder;
		if(format == FileFormat.HTML) {
			builder = new HtmlBuilder("Product Stats Report");
		} else {
			builder = new PdfBuilder("Product Stats Report");
		}
		
		ProductStatsDirector director = new ProductStatsDirector(builder);
		
		int months = Integer.parseInt(getView().getMonths());
		
		Calendar calendar = Calendar.getInstance();
		Date endDate = calendar.getTime();
		calendar.add(Calendar.MONTH, -months);
		Date startDate = calendar.getTime();
		
		director.createReport(endDate, months);
		director.displayReport();
	}

}

