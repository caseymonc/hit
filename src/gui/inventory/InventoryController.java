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
		return (getView().getSelectedProductContainer() != null);
	}

	/**
	 * This method is called when the user selects the "Delete Product Group" menu item.
	 */
	@Override
	public void deleteProductGroup() {
		ProductGroup group = (ProductGroup) getView().getSelectedProductContainer().getTag();
		pgController.deleteProductGroup(group);
	}
	
	/**
	 * This method is called when the selected item container changes.
	 */
	@Override
	public void productContainerSelectionChanged() {
		ProductContainer selectedContainer =
						(ProductContainer)getView().getSelectedProductContainer().getTag();
		List<ProductData> productDataList = new ArrayList<ProductData>();
		Collection<Product> products;
		if (selectedContainer != null) {
			products = sortProductByDescription(selectedContainer.getAllProducts());
		} else {
			products = sortProductByDescription(pController.getAllProducts());
		}
		
		for (Product product : products) {
			ProductData productData = new ProductData();
			productData.setBarcode(product.getBarCode().toString());
			int itemCount;
			if (selectedContainer != null) {
				itemCount = selectedContainer.getItemsByProduct(product).size();
			} else {
				try {
					itemCount = pController.getItemsByProduct(product).size();
				}
				catch(Exception e) {
					itemCount = 0;
					getView().displayErrorMessage(e.getMessage());
				}
			}
			productData.setCount(Integer.toString(itemCount));
			productData.setDescription(product.getDescription());
			productData.setShelfLife(product.getShelfLife() + " months");
			productData.setSize(SizeFormatter.format(product.getSize()));
			productData.setSupply(SizeFormatter.format(product.getThreeMonthSize()));
			productData.setTag(product);

			productDataList.add(productData);
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
		} else {
						getView().setContextUnit("All");
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

		if (selectedProduct != null) {
			Product product = (Product) selectedProduct.getTag();
			ProductContainer container = (ProductContainer) selectedContainer.getTag();
			List<Item> items;
			if (container != null) {
				items = sortItemsByEntryDate(container.getItemsByProduct(product));
			} else {
				items = sortItemsByEntryDate((Set<Item>)pController.getItemsByProduct(product));
			}
			for (Item item : items) {
				ItemData data = new ItemData();
				data.setBarcode(item.getBarCode().getBarCode());
				data.setEntryDate(item.getEntryDate());
				data.setExpirationDate(item.getExpirationDate());
								
				String groupName = "";
				String unitName = "";
				ProductContainer cont = item.getContainer();
				if (cont instanceof ProductGroup){
					groupName = cont.getName();
					unitName = cont.getStorageUnit().getName();
				}
				else{
					unitName = cont.getName();
				}
				
				data.setProductGroup(groupName);
				data.setStorageUnit(unitName);
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

		Product p = (Product)getView().getSelectedProduct().getTag();
		ProductContainer pc = (ProductContainer)getView().getSelectedProductContainer().getTag();
		List<Item> items;
		if (pc != null) {
			items = sortItemsByEntryDate(pc.getItemsByProduct(p));
		} else {
			items = sortItemsByEntryDate((Set<Item>)pController.getItemsByProduct(p));
		}
		List<ItemData> itemDataList = new ArrayList<ItemData>();

		String selectedItemBarCode = getView().getSelectedItem().getBarcode();
		ItemData selectedItem = null;
		
		for (Item item : items) {
			
			ItemData data = new ItemData(item);
			String barCode = item.getBarCode().getBarCode();
			
			if (barCode.equalsIgnoreCase(selectedItemBarCode)) {
				selectedItem = data;
			}
			
			itemDataList.add(data);
		}
		
		getView().setItems(itemDataList.toArray(new ItemData[0]));
		getView().selectItem(selectedItem);
	}
	/**
	 * Sorts items by entry date
	 * @return a sorted list of Items.
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
	 * Sorts products by their description
	 * @return a sorted list of Products.
	 */
	private List<Product> sortProductByDescription(Collection<Product> products) {
			List<Product> sortedProducts = new ArrayList<Product>();
			for(Product product : products) {
					sortedProducts.add(product);
			}
			Collections.sort(sortedProducts, new Product.ProductComparator());
			return sortedProducts;
	}
		
	/**
	 * Returns true if and only if the "Delete Product" menu item should be enabled.
	 */
	@Override
	public boolean canDeleteProduct() {
		if (getView().getSelectedProduct() == null)
			return false;
		
		Product p = (Product)getView().getSelectedProduct().getTag();
		ProductContainer c = 
				(ProductContainer)getView().getSelectedProductContainer().getTag();
		
		if (c != null) {
			return pController.canRemoveProductFromContainer(p, c);
		} else {
			return pController.canRemoveProduct(p);
		}
	}

	/**
	 * This method is called when the user selects the "Delete Product" menu item.
	 */
	@Override
	public void deleteProduct() {
				Product p = (Product)getView().getSelectedProduct().getTag();
				ProductContainer c = 
						(ProductContainer)getView().getSelectedProductContainer().getTag();
				if (c != null){
					pController.removeProductFromContainer(p, c);
				} else {
					pController.removeProduct(p);
				}
				productContainerSelectionChanged();
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
			return (getView().getSelectedProduct() != null);
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
		ProductContainer currentContainer = 
			(ProductContainer) getView().getSelectedProductContainer().getTag();
		pController.moveProductToContainer(product, targetContainer, currentContainer);
		
	}

	/**
	 * This method is called when the user drags an item into
	 * a product container.
	 * 
	 * @param itemData Item dragged into the target product container
	 * @param containerData Target product container
	 */
	@Override
	public void moveItemToContainer(ItemData itemData, ProductContainerData containerData) {
		Item item = (Item) itemData.getTag();
		ProductContainer targetContainer = (ProductContainer) containerData.getTag();
		Product product = item.getProduct();
		ProductContainer currentContainer = item.getContainer();
		pController.moveProductToContainer(product, targetContainer, currentContainer);
		
		iController.transferItem(item, targetContainer.getStorageUnit());
	}

		/** Updates the InventoryController.  Called from the observers when there
		 * is a change.
		 * 
		 * @param oObj - the observable object that is notifying the observer
		 * @param hint - the hint about what what updated
		 */
	@Override
	public void update(Observable oObj, Object hint) {
		if ((oObj instanceof StorageUnitController 
				|| oObj instanceof ProductGroupController)){
			updateProductContainer(hint);
		} else if (oObj instanceof ProductController) {
			updateProduct(hint);
		} else if (oObj instanceof ItemController) {
			updateItem(hint);
		}
	}
	
	private void updateProduct(Object observerHint) {
				ProductData selectedProduct = getView().getSelectedProduct();
		if (observerHint instanceof Hint) {
			Hint hint = (Hint)observerHint;
			Product product = (Product)hint.getExtra();
			if (hint.getHint() == Hint.Value.Add) {
				productContainerSelectionChanged();
			} else if (hint.getHint() == Hint.Value.Edit) {
				productContainerSelectionChanged();
			} else if (hint.getHint() == Hint.Value.Delete) {
				productContainerSelectionChanged();
			} else if (hint.getHint() == Hint.Value.Move){
				productContainerSelectionChanged();
			} else if (hint.getHint() == Hint.Value.Move){
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
				ProductData selectedProduct = getView().getSelectedProduct();
		if (observerHint instanceof Hint) {
			Hint hint = (Hint)observerHint;
			Item item = (Item)hint.getExtra();
			
			if (hint.getHint() == Hint.Value.Add) {
				
				productContainerSelectionChanged();
				
			} else if (hint.getHint() == Hint.Value.Edit) {
				itemSelectionChanged();
			
			} else if (hint.getHint() == Hint.Value.Delete) {
				productContainerSelectionChanged();
				getView().selectProduct(selectedProduct);
				productSelectionChanged();

			} else if (hint.getHint() == Hint.Value.Move) {
				productContainerSelectionChanged();
				getView().selectProduct(selectedProduct);
				productSelectionChanged();
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
								productContainerSelectionChanged();
			} else if (hint.getHint() == Hint.Value.Delete) {
				getView().deleteProductContainer(selectedData);
				selectedData = null;
			}
		}
	}
}

