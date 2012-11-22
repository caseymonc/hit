package model.persistence.DAO;

import java.util.ArrayList;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ProductGroupDO;

public class DBProductGroupDAO extends ProductGroupDAO {

	/**
	 * gets a list of all ProductGroup data objects in the database
	 * @return a list of ProductGroup data objects
	 */
	@Override
	public ArrayList<ProductGroupDO> readAll() {
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
