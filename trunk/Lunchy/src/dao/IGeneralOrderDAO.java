package dao;

import java.util.ArrayList;
import java.util.Date;

import entities.GeneralOrder;

public interface IGeneralOrderDAO {
	
	public int addGeneralOrder(GeneralOrder generalOrder);
	public boolean updateGeneralOrder(GeneralOrder generalOrder);
	public GeneralOrder getGeneralOrderByID(int ID);	
	public boolean updateAll();
	public GeneralOrder getGeneralOrderByDate(Date date);
	//public GeneralOrder getGenerlaOrderByDate(String date);
	//public MenuItemPersonalOrder getMenuItemPersonalOrderByID(int ID);
	public ArrayList<GeneralOrder> getAllGeneralOrder();
	
}
