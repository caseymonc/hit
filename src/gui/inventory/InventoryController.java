package gui.inventory;

import gui.common.*;
import gui.item.*;
import gui.product.*;

import java.util.*;

import model.CoreObjectModel;
import model.Hint;
import model.controllers.ItemController;
import model.controllers.ProductController;
import model.controllers.ProductGroupController;
import model.controllers.StorageUnitController;
import model.entities.Item;
import model.entities.Product;
import model.entities.ProductContainer;
import model.entities.ProductGroup;
import model.entities.Size;
import model.entities.StorageUnit;

/**
 * Controller class for inventory view.
 * 
 * This class implements Observer.  It observes all of the model-level controllers.
 */
public class InventoryController extends Controller implements IInventoryController, Observer {

	private ProductController pController;
	private ItemController iController;
	private ProductGroupController pgController;
	private StorageUnitController suController;
	private Map<ProductContainer, ProductContainerData> dataForContainer;
	private ProductContainerData root;
	/**
	 * Constructor.
	 *  
	 * @param view Reference to the inventory view
	 */
	public InventoryController(IInventoryView view) {
		super(view);

		CoreObjectModel COM = CoreObjectModel.getInstance();
		
		suController = COM.getStorageUnitController();
		pgController = COM.getProductGroupController();
		iController = COM.getItemController();
		pController = COM.getProductController();
		
		suController.addObserver(this);
		pgController.addObserver(this);
		iController.addObserver(this);
		pController.addObserver(this);
		
		construct();
	}

	/**
	 * Returns a reference to the view for this controller.
	 */
	@Override
	protected IInventoryView getView() {
		return (IInventoryView)super.getView();
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	public void loadValues() {
		dataForContainer = new HashMap<ProductContainer, ProductContainerData>();
		
		ProductContainerData root = new ProductContainerData();
		
		this.root = root;
		
		List<StorageUnit> units = suController.getAllStorageUnits();
		
		StorageUnit[] unitsArray = new StorageUnit[units.size()];
		
		for(int i = 0; i < unitsArray.length; i++) {
			unitsArray[i] = units.get(i);
		}
		
		Arrays.sort(unitsArray, new Comparator<StorageUnit>() {
			public int compare(StorageUnit arg0, StorageUnit arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}
		});
		
		for(StorageUnit unit : unitsArray) {
			addProductContainer(unit, root);
		}
		
		
		getView().setProductContainers(root);
	}
	
	/** Build the ProductContainer Tree.  
	 * Add the ProductContainers in container to the ProductContainerData object.
	 * @param container
	 * @param parent
	 */
	private void addProductContainer(ProductContainer container, ProductContainerData parent) {
		ProductContainerData unitData = new ProductContainerData(container.getName());
		unitData.setTag(container);
		
		dataForContainer.put(container, unitData);
		
		Collection<ProductGroup> groups = container.getAllProductGroup();
		
		ProductGroup[] groupsArray = new ProductGroup[groups.size()];
		
		int i = 0;
		for(ProductGroup group : groups) {
			groupsArray[i] = group;
			i++;
		}
		
		Arrays.sort(groupsArray, new Comparator<ProductGroup>() {
			public int compare(ProductGroup arg0, ProductGroup arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}
		});
		
		for(ProductGroup group : groupsArray) {
			addProductContainer(group, unitData);
		}
		parent.addChild(unitData);
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently
	 * allowed to interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's view
	 * have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
		return;
	}
	
	//
	// IInventoryController overrides
	//

	/**
	 * Returns true if and only if the "Add Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canAddStorageUnit() {
		return true;
	}
	
	/**
	 * Returns true if and only if the "Add Items" menu item should be enabled.
	 */
	@Override
	public boolean canAddItems() {
		return true;
	}
	
	/**
	 * Returns true if and only if the "Transfer Items" menu item should be enabled.
	 */
	@Override
	public boolean canTransferItems() {
		return true;
	}
	
	/**
	 * Returns true if and only if the "Remove Items" menu item should be enabled.
	 */
	@Override
	public boolean canRemoveItems() {
		return true;
	}

	/**
	 * Returns true if and only if the "Delete Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteStorageUnit() {
		StorageUnit group = (StorageUnit) getView().getSelectedProductContainer().getTag();
		return suController.canDeleteStorageUnit(group);
	}
	
	/**
	 * This method is called when the user selects the "Delete Storage Unit" menu item.
	 */
	@Override
	public void deleteStorageUnit() {
		StorageUnit group = (StorageUnit) getView().getSelectedProductContainer().getTag();
		suController.deleteStorageUnit(group);
	}

	/**
	 * Returns true if and only if the "Edit Storage Unit" menu item should be enabled.
	 */
	@Override
	public boolean canEditStorageUnit() {
		return true;
	}

	/**
	 * Returns true if and only if the "Add Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canAddProductGroup() {
		return true;
	}

	/**
	 * Returns true if and only if the "Delete Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteProductGroup() {
		ProductGroup group = (ProductGroup) getView().getSelectedProductContainer().getTag();
		return pgController.canDeleteProductGroup(group);
	}

	/**
	 * Returns true if and only if the "Edit Product Group" menu item should be enabled.
	 */
	@Override
	public boolean canEditProductGroup() {
		return true;
	}
	
	/**
	 * This method is called when the user selects the "Delete Product Group" menu item.
	 */
	@Override
	public void deleteProductGroup() {
		ProductGroup group = (ProductGroup) getView().getSelectedProductContainer().getTag();
		pgController.deleteProductGroup(group);
	}

	private Random rand = new Random();
	
	private String getRandomBarcode() {
		Random rand = new Random();
		StringBuilder barcode = new StringBuilder();
		for (int i = 0; i < 12; ++i) {
			barcode.append(((Integer)rand.nextInt(10)).toString());
		}
		return barcode.toString();
	}

	/**
	 * This method is called when the selected item container changes.
	 */
	@Override
	public void productContainerSelectionChanged() {
		ProductContainer selectedContainer = 
						(ProductContainer)getView().getSelectedProductContainer().getTag();
		System.out.println("Drawing Products");
		List<ProductData> productDataList = new ArrayList<ProductData>();		
		if (selectedContainer != null) {
			System.out.println("Got Selected Container: " + selectedContainer.getName());
			Collection<Product> products = selectedContainer.getAllProducts();
			System.out.println(products.toString());
			for (Product product : products) {
				ProductData productData = new ProductData();			
				productData.setBarcode(product.getBarCode().toString());
				int itemCount = selectedContainer.getItemsByProduct(product).size();
				productData.setCount(Integer.toString(itemCount));
				productData.setDescription(product.getDescription());
				productData.setShelfLife(product.getShelfLife() + " months");
				productData.setSize(SizeFormatter.format(product.getSize()));
				productData.setSupply("10 count");
				productData.setTag(product);
				
				productDataList.add(productData);
			}
		}
		getView().setProducts(productDataList.toArray(new ProductData[0]));
		
		getView().setItems(new ItemData[0]);
		getView().setContextSupply("");
		getView().setContextGroup("");
		getView().setContextUnit("");
		
		if (selectedContainer != null) {
			StorageUnit unit = selectedContainer.getStorageUnit();
			getView().setContextUnit(unit.getName());
			if (selectedContainer instanceof ProductGroup) {
				getView().setContextGroup(selectedContainer.getName());
				Size tmSupply = ((ProductGroup) selectedContainer).getThreeMonthSupply();
				getView().setContextSupply(SizeFormatter.format(tmSupply));
			}
		}
	}

	/**
	 * This method is called when the selected item changes.
	 */
	@Override
	public void productSelectionChanged() {
		List<ItemData> itemDataList = new ArrayList<ItemData>();		
		ProductData selectedProduct = getView().getSelectedProduct();
		ProductContainerData selectedContainer = getView().getSelectedProductContainer();
		
		Product product = (Product) selectedProduct.getTag();
		ProductContainer container = (ProductContainer) selectedContainer.getTag();
		if (selectedProduct != null) {
			List<Item> items = sortItemsByEntryDate(container.getItemsByProduct(product));
			for (Item item : items) {
				ItemData data = new ItemData();
				data.setBarcode(item.getBarCode().getBarCode());
				data.setEntryDate(item.getEntryDate());
				data.setExpirationDate(item.getExpirationDate());
				data.setProductGroup(container.getName());
				data.setStorageUnit(container.getStorageUnit().getName());
				data.setTag(item);
				itemDataList.add(data);
			}
		}
		getView().setItems(itemDataList.toArray(new ItemData[0]));
	}

	/**
	 * This method is called when the selected item changes.
	 */
	@Override
	public void itemSelectionChanged() {
		List<ItemData> itemDataList = new ArrayList<ItemData>();
		
		String selectedItemBarCode = getView().getSelectedItem().getBarcode();
		
		ProductData selectedProduct = getView().getSelectedProduct();
		Product product = (Product) selectedProduct.getTag();

		ProductContainerData selectedContainer = getView().getSelectedProductContainer();
		ProductContainer container = (ProductContainer) selectedContainer.getTag();
		ItemData selectedItem = null;
		
		if (selectedProduct != null) {
			List<Item> items = sortItemsByEntryDate(container.getItemsByProduct(product));
			for (Item item : items) {
				ItemData data = new ItemData();
				String barCode = item.getBarCode().getBarCode();
				data.setBarcode(barCode);
				if (barCode.equalsIgnoreCase(selectedItemBarCode)) {
					selectedItem = data;
				}
				data.setEntryDate(item.getEntryDate());
				data.setExpirationDate(item.getExpirationDate());
				data.setProductGroup(container.getName());
				data.setStorageUnit(container.getStorageUnit().getName());
				data.setTag(item);
				itemDataList.add(data);
			
			}
		}
		getView().setItems(itemDataList.toArray(new ItemData[0]));
		getView().selectItem(selectedItem);
	}
	/**
	 * 
	 * @return 
	 */
	private List<Item> sortItemsByEntryDate(Set<Item> items) {
		List<Item> sortedItems = new ArrayList<Item>();
		for(Item item : items) {
			sortedItems.add(item);
		}
		Collections.sort(sortedItems, new Item.ItemComparator());
		return sortedItems;
	}
	
	/**
	 * Returns true if and only if the "Delete Product" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteProduct() {
                Product p = (Product)getView().getSelectedProduct().getTag();
                ProductContainer c = 
                        (ProductContainer)getView().getSelectedProductContainer().getTag();
                return pController.canRemoveProductFromContainer(p, c);
	}

	/**
	 * This method is called when the user selects the "Delete Product" menu item.
	 */
	@Override
	public void deleteProduct() {
	}

	/**
	 * Returns true if and only if the "Edit Item" menu item should be enabled.
	 */
	@Override
	public boolean canEditItem() {
		return (getView().getSelectedItem() != null);
	}

	/**
	 * This method is called when the user selects the "Edit Item" menu item.
	 */
	@Override
	public void editItem() {
		getView().displayEditItemView();
	}

	/**
	 * Returns true if and only if the "Remove Item" menu item should be enabled.
	 */
	@Override
	public boolean canRemoveItem() {
		return (getView().getSelectedItem() != null);
	}

	/**
	 * This method is called when the user selects the "Remove Item" menu item.
	 */
	@Override
	public void removeItem() {
		iController.removeItem((Item) getView().getSelectedItem().getTag());
	}

	/**
	 * Returns true if and only if the "Edit Product" menu item should be enabled.
	 */
	@Override
	public boolean canEditProduct() {
		return true;
	}

	/**
	 * This method is called when the user selects the "Add Product Group" menu item.
	 */
	@Override
	public void addProductGroup() {
		getView().displayAddProductGroupView();
	}
	
	/**
	 * This method is called when the user selects the "Add Items" menu item.
	 */
	@Override
	public void addItems() {
		getView().displayAddItemBatchView();
	}
	
	/**
	 * This method is called when the user selects the "Transfer Items" menu item.
	 */
	@Override
	public void transferItems() {
		getView().displayTransferItemBatchView();
	}
	
	/**
	 * This method is called when the user selects the "Remove Items" menu item.
	 */
	@Override
	public void removeItems() {
		getView().displayRemoveItemBatchView();
	}

	/**
	 * This method is called when the user selects the "Add Storage Unit" menu item.
	 */
	@Override
	public void addStorageUnit() {
		getView().displayAddStorageUnitView();
	}

	/**
	 * This method is called when the user selects the "Edit Product Group" menu item.
	 */
	@Override
	public void editProductGroup() {
		getView().displayEditProductGroupView();
	}

	/**
	 * This method is called when the user selects the "Edit Storage Unit" menu item.
	 */
	@Override
	public void editStorageUnit() {
		getView().displayEditStorageUnitView();
	}

	/**
	 * This method is called when the user selects the "Edit Product" menu item.
	 */
	@Override
	public void editProduct() {
		getView().displayEditProductView();
	}
	
	/**
	 * This method is called when the user drags a product into a
	 * product container.
	 * 
	 * @param productData Product dragged into the target product container
	 * @param containerData Target product container
	 */
	@Override
	public void addProductToContainer(ProductData productData, 
										ProductContainerData containerData) {	
		ProductContainer targetContainer = (ProductContainer) containerData.getTag();
		Product product = (Product) productData.getTag();
		StorageUnit targetUnit = targetContainer.getStorageUnit();
		pController.moveProductToContainer(product, targetContainer);
		
	}

	/**
	 * This method is called when the user drags an item into
	 * a product container.
	 * 
	 * @param itemData Item dragged into the target product container
	 * @param containerData Target product container
	 */
	@Override
	public void moveItemToContainer(ItemData itemData,
									ProductContainerData containerData) {
		ProductContainer container = (ProductContainer) containerData.getTag();
		Item item = (Item) itemData.getTag();
		iController.moveItem(item, container.getStorageUnit());
	}

        /** Updates the InventoryController.  Called from the observers when there
         * is a change.
         * 
         * @param oObj - the observable object that is notifying the observer
         * @param hint - the hint about what what updated
         */
	@Override
	public void update(Observable oObj, Object hint) {
		System.out.println("Notified Observers");
		if ((oObj instanceof StorageUnitController 
				|| oObj instanceof ProductGroupController)) {
			updateProductContainer(hint);
		}else if (oObj instanceof ProductController) {
			updateProduct(hint);
		}else if (oObj instanceof ItemController) {
			updateItem(hint);
		}
	}
	
	private void updateProduct(Object observerHint) {
		ProductContainerData selectedData = getView().getSelectedProductContainer();
		if (observerHint instanceof Hint) {
			Hint hint = (Hint)observerHint;
			Product product = (Product)hint.getExtra();
			if (hint.getHint() == Hint.Value.Add) {
				productContainerSelectionChanged();
			}else if (hint.getHint() == Hint.Value.Edit) {
				productContainerSelectionChanged();
			}else if (hint.getHint() == Hint.Value.Delete) {
				productContainerSelectionChanged();
			}
		}
	}
	/**
	 *	
	 * @param observerHint has the hint and the Item that was changed
	 */
	private void updateItem(Object observerHint) {
		ProductContainerData selectedData = getView().getSelectedProductContainer();
		if (observerHint instanceof Hint) {
			
			Hint hint = (Hint)observerHint;
			Item item = (Item)hint.getExtra();
			
			if (hint.getHint() == Hint.Value.Add) {
				
				itemSelectionChanged();
				
			} else if (hint.getHint() == Hint.Value.Edit) {
				
				itemSelectionChanged();
			
			} else if (hint.getHint() == Hint.Value.Delete) {
				
				productSelectionChanged();
			
			} else if (hint.getHint() == Hint.Value.Move) {
				
				itemSelectionChanged();
			
			}
		}
	}
	
	private void updateProductContainer(Object observerHint) {
		ProductContainerData selectedData = getView().getSelectedProductContainer();
		if (observerHint instanceof Hint) {
			Hint hint = (Hint)observerHint;
			ProductContainer container = (ProductContainer)hint.getExtra();
			if (hint.getHint() == Hint.Value.Add || hint.getHint() == Hint.Value.Edit) {
				loadValues();
				ProductContainerData selectedContainer = dataForContainer.get(container);
				getView().selectProductContainer(selectedContainer);
			}else if (hint.getHint() == Hint.Value.Delete) {
				getView().deleteProductContainer(selectedData);
				selectedData = null;
			}
		}
	}
}

