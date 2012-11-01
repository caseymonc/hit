package reports.visitors;

import common.util.DateUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
	public ItemStatsVisitor(int months) {
		
		Calendar calendar = Calendar.getInstance();
		endDate = formatDate(calendar.getTime());
		calendar.add(Calendar.MONTH, -months);
		startDate = formatDate(calendar.getTime());
		
		supplyOnDate = new TreeMap<Date, Integer>();
		
		while(formatDate(calendar.getTime()).compareTo(endDate) <= 0) {
			supplyOnDate.put(formatDate(calendar.getTime()), new Integer(0));
			calendar.add(Calendar.DATE, 1);
		}
		
		usedItemAges = new ArrayList<Integer>();
		currentItemAges = new ArrayList<Integer>();
		maxUsedAge = 0;
		maxCurrentAge = 0;
		numAddedItems = 0;
	}

	@Override
	public void visitItem(Item item) {		
		updateSupplyOnDate(item);
		updateItemAges(item);
		
		if(item.getEntryDate().after(startDate)) {
			numAddedItems++;
		}
	}
	
	/**
	 * Get the current number of items in the system
	 * @return
	 */
	public String getCurrentSupply(){
		return Integer.toString(supplyOnDate.get(formatDate(new Date())));
	}
	
	/**
	 * Get the average number of items in the system
	 * @return
	 */
	public String getAverageSupply(){
		int totalDays = 0;
		int sumSupply = 0;
		
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
		int totalAge = 0;
		int numAges = 0;
		
		for(Integer i : ages) {
			totalAge += i;
			numAges++;
		}
		
		String result = "";
		if(numAges == 0){
			result += "0 days";
		} else {
			result += new DecimalFormat("#.#").format(Float.toString(totalAge / numAges)) + " days";
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

		Date entryDate = formatDate(item.getEntryDate());
		Date exitDate;
		
		if(item.getExitDate() == null) {
			exitDate = formatDate(endDate);
		} else {
			exitDate = formatDate(item.getExitDate());
		}
		
		Calendar calendar = Calendar.getInstance();
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
		if(item.getExitDate() != null) {	
			Date exitDate = formatDate(item.getExitDate());
			Date entryDate = formatDate(item.getEntryDate());
			long timeDiff = exitDate.getTime() - entryDate.getTime();
			int numDays = (int)Math.ceil((double)timeDiff / 86400000);
			usedItemAges.add(numDays);
			
			if(numDays > maxUsedAge) {
				maxUsedAge = numDays;
			}
		} else {
			Date today = formatDate(Calendar.getInstance().getTime());
			Date entryDate = formatDate(item.getEntryDate());
			long timeDiff = today.getTime() - entryDate.getTime();
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