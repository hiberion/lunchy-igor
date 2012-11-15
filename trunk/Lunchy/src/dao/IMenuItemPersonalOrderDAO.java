package dao;

import java.util.ArrayList;

import entities.MenuItemPersonalOrder;

public interface IMenuItemPersonalOrderDAO {
	
	public int addMenuItemPersonalOrder(MenuItemPersonalOrder menuItemPersonalOrder);
	public boolean updateMenuItemPersonalOrder(MenuItemPersonalOrder menuItemPersonalOrder);
	public MenuItemPersonalOrder getMenuItemPersonalOrderByMenuItemID(int ID);
	public MenuItemPersonalOrder getMenuItemPersonalOrderByPersonalOrderID(int personalOrderID);
	//public MenuItemPersonalOrder getMenuItemPersonalOrderByMenuItemIDandPersonalOrderID(int menuItemID, int personalOrderID);
	public boolean updateAll();
	public boolean removeMenuItemPersonalOrder(int menuItemID, int personalOrderID);
	//public MenuItemPersonalOrder getMenuItemPersonalOrderByID(int ID);
	public ArrayList<MenuItemPersonalOrder> getAllMenuItemPersonalOrder();

}
