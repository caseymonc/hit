package model;

public interface Command{
	
	/**
	 * Do an action
	 */
	void doAction();
	
	/**
	 * Undo the action
	 */
	void undoAction();
}
