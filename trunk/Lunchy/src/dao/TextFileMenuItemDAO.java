package dao;


import igor.lunchy.ParseTextFile;

import java.util.ArrayList;

import entities.MenuItem;


public class TextFileMenuItemDAO implements IMenuItemDAO {
	
	private ArrayList<MenuItem> collection;
	private String fileName;
	//private int size;
	
	@Override
	public int getSize () {
		return collection.size();
	}
	
	
	public TextFileMenuItemDAO(String fileName) {
		this.fileName = fileName;
		collection = getMenuItemCollection(fileName);
	}
	
	private ArrayList<MenuItem> getMenuItemCollection(String fileName) {
		int tempID;
		String tempName;
		int tempCategory;
		String tempDescript;
		double tempPrice;
		boolean tempAvail = true;
		
		ArrayList<MenuItem> result = new ArrayList<>();
		String[][] Data = ParseTextFile.getStringTable(fileName, "_", 6);
		
		int maxId = 0;
		
		for (String[] line: Data) {
			tempID = Integer.parseInt(line[0]);
			tempName = line[1];
			tempCategory = Integer.parseInt(line[2]);
			tempDescript = line[3];
			tempPrice = Double.parseDouble(line[4]);
			tempAvail = (line[5]).equals("Y") ? true : false;
			result.add(new MenuItem(tempID, tempName, tempCategory,
					tempDescript, tempPrice, tempAvail));
			if (tempID > maxId) {
				maxId = tempID;
			}
		}
		MenuItem.initId(result.size());
		return result;
	}
	
	private boolean putMenuItemCollection(ArrayList<MenuItem> menuItemCol) {
		int colSize = menuItemCol.size();
		String[][] r1 = new String[colSize][6];
		for (int i = 0; i < colSize; i++) {
			r1[i] = menuItemCol.get(i).toStringArray();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		return result;
	}

	@Override
	public ArrayList<MenuItem> getAllMenuItem() {
		ArrayList<MenuItem> result = new ArrayList<>(collection);
		return result;
	}

	@Override
	public boolean updateAll() {
		return putMenuItemCollection(collection);
	}
	
	@Override
	protected void finalize() throws Throwable {
		updateAll();
		super.finalize();
	}

	@Override
	public int addMenuItem(MenuItem menuItem) {
		collection.add(menuItem);
		return collection.size()-1;
	}

	@Override
	public boolean updateMenuItem(MenuItem menuItem) {
		collection.set(menuItem.getID(), menuItem);
		return true;
	}

	@Override
	public MenuItem getMenuItemByID(int ID) {
		MenuItem result = collection.get(ID);
		return result;
	}


	@Override
	public ArrayList<MenuItem> getMenuItemByCategory(int category) {
		ArrayList<MenuItem> result = new ArrayList<>();
		for (MenuItem mi: collection) {
			if (mi.getCategory() == category)
				result.add(mi);
		}
		return result;
	}


	@Override
	public ArrayList<MenuItem> getMenuItemByAvailability(boolean avail) {
		ArrayList<MenuItem> result = new ArrayList<>();
		for (MenuItem mi: collection) {
			if (mi.getAvail() == avail)
				result.add(mi);
		}
		return result;
	}
}
