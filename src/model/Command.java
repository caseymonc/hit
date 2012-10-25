package model;

/**
 * A Command stores functions for doing and undoing an action. A Command is
 * used for undo/redo.  Whenever an action is performed in a Batch View, a
 * new Command class is created that implements this Command interface.  The 
 * CommandManager class stores and manager all Commands created when actions
 * are performed in a Batch View.
 */
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
