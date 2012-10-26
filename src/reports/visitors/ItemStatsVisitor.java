package reports.visitors;

import model.entities.Item;

public class ItemStatsVisitor implements ItemVisitor {

	/**
	 * The number of months for which to
	 * create this report
	 * @param months
	 */
	public ItemStatsVisitor(int months) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void visitItem(Item item) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Get the current number of items in the system
	 * @return
	 */
	public int getCurrentSupply(){
		return 0;
	}
	
	/**
	 * Get the average number of items in the system
	 * @return
	 */
	public float getAverageSupply(){
		return 0;
	}
	
	/**
	 * Get the min number of items in the system
	 * @return
	 */
	public int getMinSupply(){
		return 0;
	}
	
	/**
	 * Get the max number of items in the system
	 * @return
	 */
	public int getMaxSupply(){
		return 0;
	}
	
	/**
	 * Get the total used items
	 * @return
	 */
	public int getUsedSupply(){
		return 0;
	}
	
	/**
	 * Get the added items
	 * @return
	 */
	public int getAddedSupply(){
		return 0;
	}
	
	/**
	 * Get the average used age
	 * @return
	 */
	public float getAverageUsedAge(){
		return 0;
	}
	
	/**
	 * Get the max used age
	 * @return
	 */
	public int getMaxUsedAge(){
		return 0;
	}
	
	/**
	 * Get the average current age
	 * @return
	 */
	public float getAverageCurrentAge(){
		return 0;
	}
	
	/**
	 * Get max current age
	 * @return
	 */
	public int getMaxCurrentAge(){
		return 0;
	}

}
