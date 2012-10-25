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
		
		for (MenuItem.curID = 1; MenuItem.curID <= Data.length; MenuItem.curID++) {
			tempID = Integer.parseInt(Data[MenuItem.curID-1][0]);
			tempName = Data[MenuItem.curID-1][1];
			tempCategory = Integer.parseInt(Data[MenuItem.curID-1][2]);
			tempDescript = Data[MenuItem.curID-1][3];
			tempPrice = Double.parseDouble(Data[MenuItem.curID-1][4]);
			tempAvail = (Data[MenuItem.curID-1][5]).equals("Y") ? true : false;
			
			result.add(new MenuItem(tempID, tempName, tempCategory,
					tempDescript, tempPrice, tempAvail));
		}
		
		return result;
	}
	
	public boolean putMenuItemCol(ArrayList<MenuItem> menuItemCol) {
		int colSize = menuItemCol.size();
		String[][] r1 = new String[colSize][6];
		for (int i = 0; i < colSize; i++) {
			r1[i] = menuItemCol.get(i).ToStrArr();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		return result;
	}
}
