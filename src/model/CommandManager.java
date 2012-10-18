package model;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {
	private List<Command> commands;
	private int position;
	
	/**
	 * Construct the CommandManager
	 * position starts out at -1 because there are no commands in the list
	 * init commands
	 */
	public CommandManager(){
		position = -1;
		commands = new ArrayList<Command>();
	}
	
	/**
	 * Undo the last command
	 * throws UnsupportedOperationException if can't undo
	 */
	public void undo(){
		assert(canUndo());
		
		if(!canUndo())
			throw new UnsupportedOperationException("Cant Undo, Position: " + position);
		
		commands.get(position).undoAction();
		--position;
	}
	
	/**
	 * Undo the last redone command
	 * throws UnsupportedOperationException if can't redo
	 */
	public void redo(){
		assert(canRedo());
		
		if(!canRedo())
			throw new UnsupportedOperationException("Cant Redo, Position: " + position);
		
		++position;
		commands.get(position).doAction();
	}
	
	/**
	 * Asks if the manager can undo the last command
	 * @return false if there are no commands
	 * @return true if there are commands
	 */
	public boolean canUndo(){
		return position >= 0;
	}
	
	/**
	 * Asks if the manager can redo the last undone command
	 * @return false if we are pointing at the last command in the list
	 * @return true if we are not pointing at the last command in the list
	 */
	public boolean canRedo(){
		return position + 1 < commands.size();
	}
	
	/**
	 * Add a command and do the action
	 * @param command the command to add and do
	 */
	public void doAction(Command command){
		if(position < commands.size() - 1)
			commands = commands.subList(0, position);
		commands.add(command);
		redo();
	}
}
