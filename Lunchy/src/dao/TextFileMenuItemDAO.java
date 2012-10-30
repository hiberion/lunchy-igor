package dao;


import igor.lunchy.ParseTextFile;

import java.util.ArrayList;

import entities.MenuItem;


public class TextFileMenuItemDAO implements IMenuItemDAO {
	
	String fileName = "Data/menu.txt";
	@Override
	public ArrayList<MenuItem> getMenuItemCol() {
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
	
	public boolean putMenuItemCol(ArrayList<MenuItem> menuItemCol) {
		int colSize = menuItemCol.size();
		String[][] r1 = new String[colSize][6];
		for (int i = 0; i < colSize; i++) {
			r1[i] = menuItemCol.get(i).toStringArray();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		return result;
	}
}
