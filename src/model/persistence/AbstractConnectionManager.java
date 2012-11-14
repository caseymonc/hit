package model.persistence;

public abstract class AbstractConnectionManager {
	
	/**
	 * Start a database transaction
	 */
	public abstract void startTransaction();
	
	/**
	 * Get a connection to the database
	 */
	public abstract void getConnection();
	
	/**
	 * Set the database transaction as successful and
	 * allow the changes to be committed
	 */
	public abstract void setTransactionSuccessful();
	
	/**
	 * End a database transaction.  Save changes if
	 * The transaction was successful
	 */
	public abstract void endTransaction();
}
