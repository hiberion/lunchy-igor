package dao;

import java.util.ArrayList;

import entities.PersonalOrder;

public interface IPersonalOrderDAO {
	
	public int addPersonalOrder(PersonalOrder personalOrder);
	public boolean updatePersonalOrder(PersonalOrder personalOrder);
	public PersonalOrder getPersonalOrderByID(int ID);
	public boolean updateAll();
	public boolean removePersonalOrder(int ID); 
	//public MenuItemPersonalOrder getMenuItemPersonalOrderByID(int ID);
	public ArrayList<PersonalOrder> getAllPersonalOrder();
	
}
