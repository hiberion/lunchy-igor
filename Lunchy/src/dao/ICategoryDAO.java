package dao;

import java.util.ArrayList;

import entities.Category;;

public interface ICategoryDAO {
	public ArrayList<Category> getCategoryCol();
	public boolean putCategoryCol(ArrayList<Category> categoryCol);
}
