package dao;

import java.util.ArrayList;

import entities.MenuItem;

public interface IMenuItemDAO {
	public int addMenuItem(MenuItem menuItem);
	public boolean updateMenuItem(MenuItem menuItem);
	public boolean updateAll();
	public MenuItem getMenuItemByID(int ID);
	public ArrayList<MenuItem> getAllMenuItem();
	/////public Collection getAllMenuItem();
	
	//public ArrayList<MenuItem> getMenuItemCol();
	//public boolean putMenuItemCol(ArrayList<MenuItem> menuItemCol);
}
