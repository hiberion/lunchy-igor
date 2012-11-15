package dao;

import java.util.ArrayList;

import entities.MenuItemPersonalOrder;

public class HibernateMenuItemPersonalOrderDAO implements
		IMenuItemPersonalOrderDAO {

	@Override
	public int addMenuItemPersonalOrder(
			MenuItemPersonalOrder menuItemPersonalOrder) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean updateMenuItemPersonalOrder(
			MenuItemPersonalOrder menuItemPersonalOrder) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MenuItemPersonalOrder getMenuItemPersonalOrderByMenuItemID(int ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MenuItemPersonalOrder getMenuItemPersonalOrderByPersonalOrderID(
			int personalOrderID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeMenuItemPersonalOrder(int menuItemID,
			int personalOrderID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<MenuItemPersonalOrder> getAllMenuItemPersonalOrder() {
		// TODO Auto-generated method stub
		return null;
	}

}
