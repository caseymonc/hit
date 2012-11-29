package model.persistence;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionManager extends ConnectionManager {

	
	private static final File dbDir = new File("db");
	private static final String dbName = "db" + File.separator + "hit.sqlite";
	private static final String connectionURL = "jdbc:sqlite:" + dbName;
	private Connection connection = null;
	private boolean successful = false;
	private Driver driver;
	
	@Override
	public void startTransaction() {
		if(connection != null)
			return;
		try {
			final String driver = "org.sqlite.JDBC";
			this.driver = (Driver)Class.forName(driver).newInstance();
			
			
			// Open a database connection
			DriverManager.registerDriver(this.driver);
			connection = DriverManager.getConnection(connectionURL);
		    
			// Start a transaction
			connection.setAutoCommit(false);
		} catch(ClassNotFoundException e) {
			// ERROR! Could not load database driver
			e.printStackTrace();
			connection = null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connection = null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connection = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			connection = null;
		}
	}

	@Override
	public Connection getConnection() {
		checkConnection();
		return connection;
	}

	private void checkConnection() {
		if(connection == null){
			throw new IllegalStateException("Start the transaction before trying " +
					"to get a connection");
		}
	}

	@Override
	public void setTransactionSuccessful() {
		checkConnection();
		successful = true;
	}

	@Override
	public void endTransaction() {
		checkConnection();
		try {
			if(successful){
				connection.commit();
			}else{
				connection.rollback();
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			successful = false;
			connection = null;
		}
	}

	private static final String CREATE_PRODUCTS = "CREATE TABLE IF NOT EXISTS \"Products\" "
	+ "("
	+		"\"product_id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "
	+		"\"creation_date\" DATETIME NOT NULL , "
	+		"\"barcode\" TEXT NOT NULL  UNIQUE , "
	+		"\"shelf_life\" INTEGER NOT NULL , "
	+		"\"three_month_supply\" INTEGER NOT NULL , "
	+		"\"size_val\" FLOAT NOT NULL , "
	+		"\"size_unit\" TEXT NOT NULL , "
	+		"\"description\" TEXT NOT NULL"
	+	")";
	
	private static final String CREATE_ITEMS = "CREATE TABLE IF NOT EXISTS \"Items\" "
	+"("
	+		"\"item_id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "
	+		"\"barcode\" VARCHAR(12) NOT NULL  UNIQUE , "
	+		"\"entry_date\" DATETIME NOT NULL , "
	+		"\"exit_date\" DATETIME, "
	+		"\"expiration_date\" DATETIME, "
	+		"\"product_id\" INTEGER NOT NULL , "
	+		"\"container_id\" INTEGER NOT NULL "
	+	")";
		
	private static final String CREATE_PRODUCT_CONTS = "CREATE TABLE IF NOT EXISTS " +
			"\"ProductContainers\" "
	+"("
	+		"\"container_id\" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE , "
	+		"\"name\" TEXT NOT NULL , "
	+		"\"three_month_supply_value\" FLOAT , "
	+		"\"three_month_supply_unit\" TEXT , "
	+		"\"parent_id\" INTEGER "
	+	")";
		
	private static final String CREATE_P_PC = "CREATE TABLE IF NOT EXISTS \"p_pc\" "
	+"("
	+		"\"p_pc_id\" INTEGER PRIMARY KEY  NOT NULL  UNIQUE , "
	+		"\"product_id\" INTEGER NOT NULL , "
	+		"\"container_id\" INTEGER NOT NULL "
	+	")";
	
	public void createTables() throws SQLException{
		if(!dbDir.exists())
			dbDir.mkdir();
		
		startTransaction();
		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(CREATE_PRODUCTS);
		stmt.executeUpdate(CREATE_ITEMS);
		stmt.executeUpdate(CREATE_PRODUCT_CONTS);
		stmt.executeUpdate(CREATE_P_PC);
		
		setTransactionSuccessful();
		endTransaction();
	}
	
}
