package dao;

import java.util.ArrayList;

import entities.Category;

public interface ICategoryDAO {
	
	//public int addCategory(Category category);
	//public boolean updateCategoty(Category category);
	public boolean updateAll();
	public Category getCategoryByID(int ID);
	public ArrayList<Category> getAllCategory();
	
	
	//ArrayList<Category> getCategoryCol();
	//boolean putCategoryCol(ArrayList<Category> categoryCol);
}
