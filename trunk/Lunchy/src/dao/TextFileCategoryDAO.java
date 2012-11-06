package dao;

import igor.lunchy.ParseTextFile;
import java.util.ArrayList;
import entities.Category;

public class TextFileCategoryDAO implements ICategoryDAO {
	
	private ArrayList<Category> collection;
	private String fileName;
	
	
	public TextFileCategoryDAO(String fileName) {
		this.fileName = fileName;
		collection = getCategoryCollection(fileName);
	}
	
	private ArrayList<Category> getCategoryCollection(String fileName) {
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
	
	private boolean putCategoryCollection(ArrayList<Category> categoryCollection) {
		int colSize = categoryCollection.size();
		String[][] r1 = new String[colSize][2];
		for (int i = 0; i < colSize; i++) {
			r1[i] = categoryCollection.get(i).toStringArray();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		return result;
	}

	@Override
	public Category getCategoryByID(int ID) {
		Category result = collection.get(ID);
		return result;
	}

	@Override
	public ArrayList<Category> getAllCategory() {
		ArrayList<Category> result = new ArrayList<>(collection);
		return result;
	}

	@Override
	public boolean updateAll() {
		return putCategoryCollection(collection);
	}

}
