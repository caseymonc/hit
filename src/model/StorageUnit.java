package model;

import java.util.List;

/** StorageUnit
 * A Storage Unit is a room, closet, pantry, cupboard, 
 * or some other enclosed area where 
 * items can be stored.
 */

public class StorageUnit extends ProductContainer implements PersistentItem{
	
	/** Name of the Storage Unit. 
	 * Must be non-empty. Must be unique among all Storage Units.
	 **/
	private String name;

	/** Constructor
	 * 
	 * @param name - The name of the StorageUnit
	 */
	public StorageUnit(String name){
		this.setName(name);
	}
	
	/** Sets the name of the StorageUnit
	 * 
	 * @param name - the new name of the StorageUnit
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Gets the name of the StorageUnit
	 * 
	 * @return the name of the StorageUnit
	 */
	public String getName() {
		return name;
	}

	/*public String sqlCreateStatement() {
		String query = "CREATE TABLE storage_units(" +
				"storage_unit_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT," + 
				");";
		return query;
	}*/

	@Override
	public void addItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addProduct(Product product) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void removeItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canAddProductContainer(ProductContainer productContainer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addProductContainer(ProductContainer productContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeProductContainer(ProductContainer productContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item getItemByBarCode(BarCode barcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getItemByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> getAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProductContainer> getAllProductContainers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductContainer getProductContainerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
