package dao;

import igor.lunchy.ParseTextFile;

import java.util.ArrayList;

import entities.MenuItemPersonalOrder;

public class TextFileMenuItemPersonalOrderDAO implements IMenuItemPersonalOrderDAO {

	private ArrayList<MenuItemPersonalOrder> collection;
	private String fileName;
	
	public TextFileMenuItemPersonalOrderDAO(String fileName) {
		this.fileName = fileName;
		collection = getMenuItemPersonalOrderCollection();
	}
	
	private ArrayList<MenuItemPersonalOrder> getMenuItemPersonalOrderCollection() {
		int menuItemID;
		int personalOrderID;
		int quantity;
		
		ArrayList<MenuItemPersonalOrder> result = new ArrayList<>();
		String[][] Data = ParseTextFile.getStringTable(fileName, "_", 3);
		
		for (String[] line: Data) {
			menuItemID = Integer.parseInt(line[0]);
			personalOrderID = Integer.parseInt(line[1]);
			quantity = Integer.parseInt(line[2]);
			result.add(new MenuItemPersonalOrder(menuItemID, personalOrderID, quantity));
		}
		
		return result;
	}
	
	private boolean putMenuItemPersonalOrderCollection(ArrayList<MenuItemPersonalOrder> menuItemPersonalOrderCollection) {
		int colSize = menuItemPersonalOrderCollection.size();
		String[][] r1 = new String[colSize][3];
		for (int i = 0; i < colSize; i++) {
			r1[i] = menuItemPersonalOrderCollection.get(i).toStringArray();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		return result;
	}

	@Override
	public boolean updateAll() {
		return putMenuItemPersonalOrderCollection(collection);
	}

	@Override
	public ArrayList<MenuItemPersonalOrder> getAllMenuItemPersonalOrder() {
		ArrayList<MenuItemPersonalOrder> result = new ArrayList<>(collection);
		return result;
	}

	@Override
	public int addMenuItemPersonalOrder(
			MenuItemPersonalOrder menuItemPersonalOrder) {
		collection.add(menuItemPersonalOrder);
		return collection.size()-1;
	}
	
	////  Dopilit
	@Override
	public boolean updateMenuItemPersonalOrder(
			MenuItemPersonalOrder menuItemPersonalOrder) {
		//collection.set(men.getID(), personalOrder);
		int updatingItem = -1; 
		for (int i = 0; i < collection.size(); i++) {
			if ((collection.get(i).getMenuItemId() == menuItemPersonalOrder.getMenuItemId()) &&
				(collection.get(i).getPersonalOrderId() == menuItemPersonalOrder.getPersonalOrderId())) {
				updatingItem = i;
				break;
			}
		}
		
		if (updatingItem != -1) {
			collection.set(updatingItem, menuItemPersonalOrder);
			return true;
		} else
			return false;
	}

	@Override
	public MenuItemPersonalOrder getMenuItemPersonalOrderByMenuItemID(int ID) {
		MenuItemPersonalOrder result = null;
		for (MenuItemPersonalOrder menuItemPersonalOrder: collection) {
			if (menuItemPersonalOrder.getMenuItemId() == ID) {
				result = menuItemPersonalOrder;
				break;
			}
		}
		return result;
	}

	@Override
	public boolean removeMenuItemPersonalOrder(int menuItemID,
			int personalOrderID) {
		int deletingItem = -1;
		for (int i = 0; i < collection.size(); i++) {
			if ((collection.get(i).getMenuItemId() == menuItemID) &&
				(collection.get(i).getPersonalOrderId() == personalOrderID)) {
				deletingItem = i;
				break;
			}
		}
		
		if (deletingItem != -1) {
			collection.remove(deletingItem);
			return true;
		} else
			return false;
	}

	@Override
	public MenuItemPersonalOrder getMenuItemPersonalOrderByPersonalOrderID(
			int personalOrderID) {
		MenuItemPersonalOrder result = null;
		for (MenuItemPersonalOrder menuItemPersonalOrder: collection) {
			if (menuItemPersonalOrder.getPersonalOrderId() == personalOrderID) {
				result = menuItemPersonalOrder;
				break;
			}
		}
		return result;
	}

}
