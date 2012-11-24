package model.persistence.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.persistence.ConnectionManager;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ProductDO;
import model.persistence.DataObjects.ProductGroupDO;

public class DBProductDAO extends ProductDAO {
	
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
	public void create(DataObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(DataObject obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(DataObject obj) {
		// TODO Auto-generated method stub

	}

}
