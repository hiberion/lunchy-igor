package dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entities.Category;
import entities.MenuItem;
import entities.Worker;

public class FileCollectionManager implements ICategoryDAO {
	public static ArrayList<MenuItem> menuList;
	public static ArrayList<Category> categoryList;
	public static ArrayList<Worker> workerList;
	
	
	public List<Category> getCategories() {
		return Collections.unmodifiableList(categoryList);
	}
	
	@Override
	public boolean putCategoryCol(ArrayList<Category> categoryCol) {
		categoryList.clear();
		categoryList.addAll(categoryCol);
		
		return false;
	}
	
	@Override
	public Category createCategory() {
		// TODO Auto-generated method stub
		return new Category("");
	}
	@Override
	public void removeCategory(int categoryId) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateCategory(Category category) {
		// TODO Auto-generated method stub
		
	}
	
	public void store(String fileName) {}
	public void load(String fileName) {}
}
