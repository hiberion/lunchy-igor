package dao;

import java.util.ArrayList;

import entities.Category;;

public interface ICategoryDAO {
	ArrayList<Category> getCategoryCol();
	boolean putCategoryCol(ArrayList<Category> categoryCol);
	
	Category createCategory();
	void removeCategory(int categoryId);
	void updateCategory(Category category);
}
