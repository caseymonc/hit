package model.persistence.DAO;

import java.util.ArrayList;
import model.persistence.DataObjects.DataObject;
import model.persistence.DataObjects.ItemDO;

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
