package model.persistence.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.persistence.ConnectionManager;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ProductGroupDO;

public class DBProductGroupDAO extends ProductGroupDAO {

	private static final String READ_ALL = "SELECT * FROM ProductContainers "
			+ "WHERE parent_id IS NOT NULL";
	private static final String INSERT = "INSERT INTO ProductContainers (name, " +
			"three_month_supply_value, three_month_supply_unit, parent_id) values (?, ?, ?, ?)";
	
	private static final String UPDATE = "UPDATE ProductContainers SET name=?, " +
			"three_month_supply_value=?, three_month_supply_unit=?, parent_id=? WHERE container_id=?";
	private static final String DELETE = "DELETE FROM ProductContainers WHERE container_id=?";
	/**
	 * gets a list of all ProductGroup data objects in the database
	 * @return a list of ProductGroup data objects
	 */
	@Override
	public ArrayList<ProductGroupDO> readAll() throws SQLException{
		ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
		Connection connection = connectionManager.getConnection();
		
		Statement stmt = connection.createStatement();
		ResultSet results = stmt.executeQuery(READ_ALL);
		
		ArrayList<ProductGroupDO> groupDos = new ArrayList();
		
		while(results.next()){
			long id = results.getLong("container_id");
			String name = results.getString("name");
			long contId = results.getLong("parent_id");
			float supplyVal = results.getFloat("three_month_supply_value");
			String supplyUnit = results.getString("three_month_supply_unit");
			ProductGroupDO groupDO = new ProductGroupDO(id, name, contId, supplyVal, supplyUnit);
			groupDos.add(groupDO);
		}
		
		return groupDos;
	}

//	@Override
//	public DataObject[] readAll() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
	public void create(DataObject obj) throws SQLException {
		if(!(obj instanceof ProductGroupDO))
			throw new IllegalArgumentException("Tried to save a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ProductGroupDO group = (ProductGroupDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(INSERT);
		    stmt.setString(1, group.getName());
		    stmt.setFloat(2, group.getThreeMonthSupplyVal());
		    stmt.setString(3, group.getThreeMonthSupplyUnit());
		    stmt.setLong(4, group.getContainerId());
		    
		    if (stmt.executeUpdate() == 1) {
		        keyStmt = connection.createStatement();
		        keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		        keyRS.next();
		        int id = keyRS.getInt(1);   // ID of the new book
		        group.setId(id);
		    }else{
		    	throw new SQLException("Failed to insert StorageUnit");
		    }
		}catch(SQLException e){
			exception = e;
		}finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		    if (exception != null) throw exception;
		}
	}

	@Override
	public void delete(DataObject obj) throws SQLException {
		if(!(obj instanceof ProductGroupDO))
			throw new IllegalArgumentException("Tried to create a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ProductGroupDO unit = (ProductGroupDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(DELETE);
		    stmt.setLong(1, unit.getId());
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

	@Override
	public void update(DataObject obj) throws SQLException {
		if(!(obj instanceof ProductGroupDO))
			throw new IllegalArgumentException("Tried to save a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ProductGroupDO group = (ProductGroupDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(UPDATE);
		    stmt.setString(1, group.getName());
		    stmt.setFloat(2, group.getThreeMonthSupplyVal());
		    stmt.setString(3, group.getThreeMonthSupplyUnit());
		    stmt.setLong(4, group.getContainerId());
		    stmt.setLong(5, group.getId());
		    stmt.executeUpdate();
		    
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
