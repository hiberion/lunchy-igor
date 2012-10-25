package dao;

import igor.lunchy.ParseTextFile;
import java.util.ArrayList;
import entities.Category;

public class TextFileCategoryDAO implements ICategoryDAO {
	
	String fileName = "Data/category.txt";

	@Override
	public ArrayList<Category> getCategoryCol() {
		int tempID;
		String tempName;
		
		ArrayList<Category> result = new ArrayList<>();
		String[][] Data = ParseTextFile.getStringTable(fileName, "_", 2);
		
		for (Category.curID = 0; Category.curID < Data.length; Category.curID++) {
			tempID = Integer.parseInt(Data[Category.curID][0]);
			tempName = Data[Category.curID][1];
			
			result.add(new Category(tempID, tempName));
		}
		
		return result;
	}

	@Override
	public boolean putCategoryCol(ArrayList<Category> categoryCol) {
		int colSize = categoryCol.size();
		String[][] r1 = new String[colSize][2];
		for (int i = 0; i < colSize; i++) {
			r1[i] = categoryCol.get(i).ToStrArr();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		return result;
	}

}
