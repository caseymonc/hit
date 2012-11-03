package reports.visitors;

import common.util.DateUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import model.entities.Item;

public class ItemStatsVisitor implements ItemVisitor {

	private Date endDate;
	private Date startDate;
	
	private Map<Date, Integer> supplyOnDate;
	private List<Integer> usedItemAges;
	private List<Integer> currentItemAges;
	private int maxUsedAge;
	private int maxCurrentAge;
	private int numAddedItems;
	
	/**
	 * The number of months for which to
	 * create this report
	 * @param months
	 */
	public ItemStatsVisitor(Date endDate, int months) {
		
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(endDate);
		this.endDate = calendar.getTime();
		calendar.add(Calendar.MONTH, -months);
		this.startDate = calendar.getTime();
		
		this.supplyOnDate = new TreeMap();
		
		while(formatDate(calendar.getTime()).compareTo(formatDate(endDate)) <= 0) {
			supplyOnDate.put(formatDate(calendar.getTime()), new Integer(0));
			calendar.add(Calendar.DATE, 1);
		}
		
		this.usedItemAges = new ArrayList();
		this.currentItemAges = new ArrayList();
		this.maxUsedAge = 0;
		this.maxCurrentAge = 0;
		this.numAddedItems = 0;
	}

	@Override
	public void visitItem(Item item) {		
		updateSupplyOnDate(item);
		updateItemAges(item);
		
		if(item.getEntryDate().compareTo(startDate) >= 0) {
			numAddedItems++;
		}
	}
	
	/**
	 * Get the current number of items in the system
	 * @return
	 */
	public String getCurrentSupply(){
		return Integer.toString(supplyOnDate.get(formatDate(endDate)));
	}
	
	/**
	 * Get the average number of items in the system
	 * @return
	 */
	public String getAverageSupply(){
		float totalDays = 0;
		float sumSupply = 0;
		
		for(Integer supply : supplyOnDate.values()) {
			totalDays++;
			sumSupply += supply;
		}
		
		return new DecimalFormat("#.#").format(sumSupply / totalDays);
	}
	
	/**
	 * Get the min number of items in the system
	 * @return
	 */
	public String getMinSupply(){
		int minSupply = Integer.MAX_VALUE;
		
		for(Integer supply : supplyOnDate.values()) {
			if(supply < minSupply) {
				minSupply = supply;
			}
		}
		
		return Integer.toString(minSupply);
	}
	
	/**
	 * Get the max number of items in the system
	 * @return
	 */
	public String getMaxSupply(){
		int maxSupply = 0;
		
		for(Integer supply : supplyOnDate.values()) {
			if(supply > maxSupply) {
				maxSupply = supply;
			}
		}
		
		return Integer.toString(maxSupply);
	}
	
	/**
	 * Get the total used items
	 * @return
	 */
	public String getUsedSupply(){
		return Integer.toString(usedItemAges.size());
	}
	
	/**
	 * Get the added items
	 * @return
	 */
	public String getAddedSupply(){
		return Integer.toString(numAddedItems);
	}
	
	/**
	 * Get the average used age
	 * @return
	 */
	public String getAverageUsedAge(){
		return getAverageAge(usedItemAges);
	}
	
	/**
	 * Get the max used age
	 * @return
	 */
	public String getMaxUsedAge(){		
		return Integer.toString(maxUsedAge) + " days";
	}
	
	/**
	 * Get the average current age
	 * @return
	 */
	public String getAverageCurrentAge(){
		return getAverageAge(currentItemAges);
	}
	
	private String getAverageAge(List<Integer> ages) {
		float totalAge = 0;
		float numAges = 0;
		
		for(Integer i : ages) {
			totalAge += i;
			numAges++;
		}
		
		String result = "";
		if(numAges == 0){
			result += "0 days";
		} else {
			result += new DecimalFormat("#.#").format(totalAge / numAges) + " days";
		}
		return result;
	}
	
	/**
	 * Get max current age
	 * @return
	 */
	public String getMaxCurrentAge(){
		return Integer.toString(maxCurrentAge) + " days";
	}

	private void updateSupplyOnDate(Item item) {

		Date entryDate = item.getEntryDate();
		Date exitDate;
		
		if(item.getExitDate() == null) {
			exitDate = endDate;
		} else {
			exitDate = item.getExitDate();
		}
		
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		if(entryDate.before(startDate)) {
			calendar.setTime(startDate);
		} else {
			calendar.setTime(entryDate);
		}
		
		Date curTime = formatDate(calendar.getTime());
		
		while(curTime.compareTo(exitDate) <= 0) {
			Integer count = supplyOnDate.get(curTime);
			count++;
			supplyOnDate.put(curTime, count);
			calendar.add(Calendar.DATE, 1);
			curTime = formatDate(calendar.getTime());
		}
	}
	
	private void updateItemAges(Item item) {
		Date entryDate = formatDate(item.getEntryDate());
		
		if(entryDate.before(startDate)) {
			entryDate = startDate;
		}
		
		if(item.getExitDate() != null) {	
			Date exitDate = formatDate(item.getExitDate());
			
			if(exitDate.before(startDate)){
				return;
			}
			
			long timeDiff = exitDate.getTime() - entryDate.getTime();
			int numDays = (int)Math.ceil((double)timeDiff / 86400000);
			usedItemAges.add(numDays);
			
			if(numDays > maxUsedAge) {
				maxUsedAge = numDays;
			}
		} else {
			long timeDiff = formatDate(endDate).getTime() - entryDate.getTime();
			int numDays = (int)Math.ceil((double)timeDiff / 86400000);
			currentItemAges.add(numDays);
			
			if(numDays > maxCurrentAge) {
				maxCurrentAge = numDays;
			}
		}
	}
	
	private Date formatDate(Date date) {
		return DateUtils.removeTimeFromDate(date);
	}
}