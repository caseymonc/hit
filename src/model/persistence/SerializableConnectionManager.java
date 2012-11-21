package model.persistence;

import java.sql.Connection;

public class SerializableConnectionManager extends ConnectionManager {

	@Override
	public void startTransaction() { }

	@Override
	public Connection getConnection() {
		return null;
	}

	@Override
	public void setTransactionSuccessful() { }

	@Override
	public void endTransaction() { }

}
