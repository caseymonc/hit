package model.persistence.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.persistence.ConnectionManager;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ItemDO;
import model.persistence.DataObjects.ProductDO;
import model.persistence.DataObjects.ProductGroupDO;
import model.persistence.DataObjects.StorageUnitDO;

public class DBItemDAO extends ItemDAO {

	/**
	 * gets a list of all Item data objects in the database
	 * @return a list of Item data objects
	 */
	@Override
	public ArrayList<ItemDO> readAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String readNewestItemBarcode() throws SQLException{
		String query = "SELECT barcode FROM Items "
			+ " ORDER BY item_id DESC";
	
		ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
		Connection connection = connectionManager.getConnection();
		
		Statement stmt = connection.createStatement();
		ResultSet results = stmt.executeQuery(query);
		results.next();
		return results.getString("barcode");
	}
	
	public ArrayList<ItemDO> readAllRemoved() throws SQLException{
		ArrayList<ItemDO> itemDOs = new ArrayList();
		
		String query = "SELECT * FROM Items "
				+ " WHERE container_id = -1";
		
		ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
		Connection connection = connectionManager.getConnection();
		
		Statement stmt = connection.createStatement();
		ResultSet results = stmt.executeQuery(query);
		
		while(results.next()){
			long id = results.getLong("item_id");
			String barcode = results.getString("barcode");
			String entryDate = results.getString("entry_date");
			String exitDate = results.getString("exit_date");
			String expirationDate = results.getString("expiration_date");
			long productId = results.getLong("product_id");
			ItemDO itemDo = new ItemDO(id, barcode, entryDate, exitDate, 
				expirationDate, productId, -1);
			
			itemDOs.add(itemDo);
		}
		
		return itemDOs;
	}
	
	@Override
	public ArrayList<ItemDO> readAllByProduct(long productId, long containerId) 
			throws SQLException{
		ArrayList<ItemDO> itemDOs = new ArrayList();
		
		String query = "SELECT * FROM Items "
				+ " WHERE product_id = " + productId + " AND container_id = " + containerId;
		
		ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
		Connection connection = connectionManager.getConnection();
		
		Statement stmt = connection.createStatement();
		ResultSet results = stmt.executeQuery(query);
		
		while(results.next()){
			long id = results.getLong("item_id");
			String barcode = results.getString("barcode");
			String entryDate = results.getString("entry_date");
			String exitDate = results.getString("exit_date");
			String expirationDate = results.getString("expiration_date");
			ItemDO itemDo = new ItemDO(id, barcode, entryDate, exitDate, 
				expirationDate, productId, containerId);
			
			itemDOs.add(itemDo);
		}
		
		return itemDOs;
	}
	
//	@Override
//	public DataObject[] readAll() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	private static final String CREATE = "INSERT INTO Items " +
							"(barcode, " +
							"entry_date, " +
							"exit_date, " +
							"expiration_date, " +
							"product_id, " +
							"container_id) VALUES (?,?,?,?,?,?)";
	@Override
	public void create(DataObject obj) throws SQLException {
		if(!(obj instanceof ItemDO))
			throw new IllegalArgumentException("Tried to save a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ItemDO item = (ItemDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(CREATE);
		    stmt.setString(1, item.getBarCode());
		    stmt.setString(2, item.getEntryDate());
		    stmt.setString(3, item.getExitDate());
		    stmt.setString(4, item.getExpirationDate());
		    stmt.setLong(5, item.getProductId());
		    stmt.setLong(6, item.getContainerId());
		    
		    if (stmt.executeUpdate() == 1) {
		        keyStmt = connection.createStatement();
		        keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		        keyRS.next();
		        int id = keyRS.getInt(1);   // ID of the new book
		        item.setId(id);
		    }else{
		    	throw new SQLException("Failed to insert StorageUnit");
		    }
		    
		    new DBppcDAO().create(item);
		}catch(SQLException e){
			exception = e;
		}finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		    if (exception != null) throw exception;
		}
	}

	private static final String DELETE = "DELETE FROM Items WHERE item_id=?";
	@Override
	public void delete(DataObject obj) throws SQLException {
		if(!(obj instanceof ItemDO))
			throw new IllegalArgumentException("Tried to create a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ItemDO item = (ItemDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(DELETE);
		    stmt.setLong(1, item.getId());
		    int rowsAffected = stmt.executeUpdate();
		    rowsAffected = 0;
		}catch(SQLException e){
			exception = e;
		}finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		    if (exception != null) throw exception;
		}
	}

	private static final String UPDATE = "UPDATE Items SET barcode=?, " +
							"entry_date=?, " +
							"exit_date=?, " +
							"expiration_date=?, " +
							"container_id=? WHERE item_id=?";
	@Override
	public void update(DataObject obj) throws SQLException {
		if(!(obj instanceof ItemDO))
			throw new IllegalArgumentException("Tried to update a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ItemDO item = (ItemDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(UPDATE);
		    stmt.setString(1, item.getBarCode());
		    stmt.setString(2, item.getEntryDate());
		    stmt.setString(3, item.getExitDate());
		    stmt.setString(4, item.getExpirationDate());
		    stmt.setLong(5, item.getContainerId());
		    stmt.setLong(6, item.getId());
		    stmt.executeUpdate();
		    new DBppcDAO().create(item);
		}catch(SQLException e){
			exception = e;
		}finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		    if (exception != null) throw exception;
		}
	}

}
