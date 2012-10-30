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
		
		int maxId = 0;
		
		for (String[] line: Data) {
			tempID = Integer.parseInt(line[0]);
			tempName = line[1];
			result.add(new Category(tempID, tempName));
			if (tempID > maxId) {
				maxId = tempID;
			}
		}
		
		Category.initId(maxId + 1);
		
		Category.initId(result.size());
		return result;
	}

	@Override
	public boolean putCategoryCol(ArrayList<Category> categoryCol) {
		int colSize = categoryCol.size();
		String[][] r1 = new String[colSize][2];
		for (int i = 0; i < colSize; i++) {
			r1[i] = categoryCol.get(i).toStringArray();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		return result;
	}

}
