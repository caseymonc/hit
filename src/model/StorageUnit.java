package model;

/** StorageUnit
 * A Storage Unit is a room, closet, pantry, cupboard, 
 * or some other enclosed area where 
 * items can be stored.
 */

public class StorageUnit extends ProductContainer implements PersistentItem{
	/** Name of the Storage Unit. 
	 * @Constraint Must be non-empty. Must be unique among all Storage Units.
	 **/
	private String name;

	public StorageUnit(String name){
		this.setName(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String sqlCreateStatement() {
		String query = "CREATE TABLE storage_units(" +
				"storage_unit_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT," + 
				");";
		return query;
	}

	@Override
	public void addItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		
	}
	
}
