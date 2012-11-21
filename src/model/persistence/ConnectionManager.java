package model.persistence;

import java.sql.Connection;

public abstract class ConnectionManager {
	
	private static ConnectionManager sConnectionManager;

	public static void setConnectionManager(ConnectionManager connectionManager){
		sConnectionManager = connectionManager;
	}
	
	public static ConnectionManager getConnectionManager(){
		if(sConnectionManager == null){
			throw new IllegalStateException("The Connection Manager was never set");
		}
		return sConnectionManager;
	}
	
	/**
	 * Start a database transaction
	 */
	public abstract void startTransaction();
	
	/**
	 * Get a connection to the database
	 */
	public abstract Connection getConnection();
	
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
