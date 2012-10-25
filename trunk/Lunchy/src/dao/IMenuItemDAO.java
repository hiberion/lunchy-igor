package dao;

import java.util.ArrayList;

import entities.MenuItem;

public interface IMenuItemDAO {
	public ArrayList<MenuItem> getMenuItemCol();
	public boolean putMenuItemCol(ArrayList<MenuItem> menuItemCol);
}
