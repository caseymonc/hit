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

public class DBProductDAO extends ProductDAO {
	
	private static final String CREATE = "INSERT INTO Products " +
			"(creation_date, " +
			"barcode, " +
			"shelf_life, " +
			"three_month_supply, " +
			"size_val, " +
			"size_unit, " +
			"description) VALUES (?,?,?,?,?,?,?)";
	
	/**
	 * gets a list of all Product data objects in the database
	 * @return a list of Product data objects
	 */
	@Override
	public ArrayList<ProductDO> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ProductDO> readAllByContainer(long container_id) throws SQLException {
		ArrayList<ProductDO> productDOs = new ArrayList();
		
		String query = "SELECT Products.* FROM Products "
				+ " JOIN p_pc ON (Products.product_id = p_pc.product_id) "
				+ " WHERE p_pc.container_id = " + container_id;
		
		ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
		Connection connection = connectionManager.getConnection();
		
		Statement stmt = connection.createStatement();
		ResultSet results = stmt.executeQuery(query);
		
		while(results.next()){
			long id = results.getLong("product_id");
			String creationDate = results.getString("creation_date");
			String barcode = results.getString("barcode");
			int shelfLife = results.getInt("shelf_life");
			int threeMonthSupply = results.getInt("three_month_supply");
			float sizeVal = results.getFloat("size_val");
			String sizeUnit = results.getString("size_unit");
			String description = results.getString("description");
			ProductDO productDO = new ProductDO(id, description, creationDate, barcode, 
				shelfLife, threeMonthSupply, sizeVal, sizeUnit);
			productDOs.add(productDO);
		}
		
		return productDOs;
	}
	
//	@Override
//	public DataObject[] readAll() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@Override
	public void create(DataObject obj) throws SQLException {
		if(!(obj instanceof ProductDO))
			throw new IllegalArgumentException("Tried to save a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ProductDO product = (ProductDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(CREATE);
		    stmt.setString(1, product.getCreationDate());
		    stmt.setString(2, product.getBarCode());
		    stmt.setInt(3, product.getShelfLife());
		    stmt.setInt(4, product.getThreeMonthSupply());
		    stmt.setFloat(5, product.getSizeVal());
		    stmt.setString(6, product.getSizeUnit());
		    stmt.setString(7, product.getDescription());
		    
		    if (stmt.executeUpdate() == 1) {
		        keyStmt = connection.createStatement();
		        keyRS = keyStmt.executeQuery("select last_insert_rowid()");
		        keyRS.next();
		        int id = keyRS.getInt(1);   // ID of the new book
		        product.setId(id);
		    }else{
		    	throw new SQLException("Failed to insert StorageUnit");
		    }
		    
		    //updateProductRelationships(product);
		}catch(SQLException e){
			exception = e;
		}finally {
		    if (stmt != null) stmt.close();
		    if (keyRS != null) keyRS.close();
		    if (keyStmt != null) keyStmt.close();
		    if (exception != null) throw exception;
		}
	}
	
	private static final String DELETE = "DELETE FROM Products WHERE product_id=?";
	@Override
	public void delete(DataObject obj) throws SQLException {
		if(!(obj instanceof ProductDO))
			throw new IllegalArgumentException("Tried to create a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ProductDO product = (ProductDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(DELETE);
		    stmt.setLong(1, product.getId());
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

	private static final String UPDATE = "UPDATE Products SET creation_date=?, " +
			"barcode=?, " +
			"shelf_life=?, " +
			"three_month_supply=?, " +
			"size_val=?, " +
			"size_unit=?, " +
			"description=? WHERE product_id=?";
	@Override
	public void update(DataObject obj) throws SQLException {
		if(!(obj instanceof ProductDO))
			throw new IllegalArgumentException("Tried to update a non StorageUnit in " +
					"DBStorageUnitDAO");
		PreparedStatement stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		SQLException exception = null;
		try{
			ProductDO product = (ProductDO)obj;
			ConnectionManager connectionManager = ConnectionManager.getConnectionManager();
			Connection connection = connectionManager.getConnection();
			
			stmt = connection.prepareStatement(UPDATE);
			stmt.setString(1, product.getCreationDate());
		    stmt.setString(2, product.getBarCode());
		    stmt.setInt(3, product.getShelfLife());
		    stmt.setInt(4, product.getThreeMonthSupply());
		    stmt.setFloat(5, product.getSizeVal());
		    stmt.setString(6, product.getSizeUnit());
		    stmt.setString(7, product.getDescription());
		    stmt.setLong(8, product.getId());
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
