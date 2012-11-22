package model.persistence.DAO;

import java.util.ArrayList;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ProductDO;

public class SerializeProductDAO extends ProductDAO {

	/**
	 * gets a list of all Product data objects in the database
	 * @return a list of Product data objects
	 */
	@Override
	public ArrayList<ProductDO> readAll() {
		// TODO Auto-generated method stub
		return null;
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
