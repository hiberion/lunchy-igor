package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.MenuItemPersonalOrder;

public class JDBCMenuItemPersonalOrderDAO implements
		IMenuItemPersonalOrderDAO {
	
	Connection connection;
	int size = 0;
	//Statement statement;
    //ResultSet resultSet;
    
	private String errMessage = null;
	private ArrayList<MenuItemPersonalOrder> collection;
	
	public JDBCMenuItemPersonalOrderDAO(Connection connection) {
		this.connection = connection;
		collection = getAllMenuItemPersonalOrder();
	}

	@Override
	public int addMenuItemPersonalOrder(
			MenuItemPersonalOrder menuItemPersonalOrder) {
		
		collection.add(menuItemPersonalOrder);
		
		@SuppressWarnings("unused")
		int ret = 0;
		
		String query =
				"insert into menuitem_personalorder (menuitem_id, personalorder_id, count) values("+
						menuItemPersonalOrder.getMenuItemID() + "," +
						menuItemPersonalOrder.getPersonalOrderID() + "," +
						menuItemPersonalOrder.getItemCount() + ")";
		//System.out.println(query);
		
		try {
	        Statement statement = connection.createStatement();
	        ret = statement.executeUpdate(query);
	        			
		} catch (SQLException e) {
			errMessage = e.getMessage();
	        System.out.println(errMessage);
		}
		
		size++;
		
		return collection.size()-1;
	}

	@Override
	public boolean updateMenuItemPersonalOrder(
			MenuItemPersonalOrder menuItemPersonalOrder) {
		
		int updatingItem = -1; 
		for (int i = 0; i < collection.size(); i++) {
			if ((collection.get(i).getMenuItemID() == menuItemPersonalOrder.getMenuItemID()) &&
				(collection.get(i).getPersonalOrderID() == menuItemPersonalOrder.getPersonalOrderID())) {
				updatingItem = i;
				break;
			}
		}
		
		if (updatingItem != -1) {
			collection.set(updatingItem, menuItemPersonalOrder);
			
			@SuppressWarnings("unused")
			int ret = 0;  // for result
			
			String query =
					"update menuitem_personalorder set " +
					"menuitem_id = " + menuItemPersonalOrder.getMenuItemID() + "," +
					"personalorder_id = " + menuItemPersonalOrder.getPersonalOrderID() + "," +
					"count = " + menuItemPersonalOrder.getItemCount() +
					" where menuitem_id = " + menuItemPersonalOrder.getMenuItemID() + " and " + 
					" personalorder_id = " + menuItemPersonalOrder.getPersonalOrderID();
			//System.out.println(query);
			
			try {
		        Statement statement = connection.createStatement();
		        ret = statement.executeUpdate(query);
		        			
			} catch (SQLException e) {
				errMessage = e.getMessage();
		        System.out.println(errMessage);
			}
			
			return true;
		} else
			return false;
	}

	@Override
	public MenuItemPersonalOrder getMenuItemPersonalOrderByMenuItemID(int ID) {
		MenuItemPersonalOrder result = null;
		for (MenuItemPersonalOrder menuItemPersonalOrder: collection) {
			if (menuItemPersonalOrder.getMenuItemID() == ID) {
				result = menuItemPersonalOrder;
				break;
			}
		}
		return result;
	}

	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeMenuItemPersonalOrder(int menuItemID,
			int personalOrderID) {
		
		int deletingItem = -1;
		for (int i = 0; i < collection.size(); i++) {
			if ((collection.get(i).getMenuItemID() == menuItemID) &&
				(collection.get(i).getPersonalOrderID() == personalOrderID)) {
				deletingItem = i;
				break;
			}
		}
		
		if (deletingItem != -1) {
			
			@SuppressWarnings("unused")
			int ret = 0;
			
			String query =
					"delete from menuitem_personalorder where "+
							"menuitem_id = " + collection.get(deletingItem).getMenuItemID() + " and " +
							"personalorder_id = " + collection.get(deletingItem).getPersonalOrderID();
			//System.out.println(query);
			
			try {
		        Statement statement = connection.createStatement();
		        ret = statement.executeUpdate(query);
		        			
			} catch (SQLException e) {
				errMessage = e.getMessage();
		        System.out.println(errMessage);
			}
			
			collection.remove(deletingItem);
			return true;
		} else
			return false;
	}

	@Override
	public ArrayList<MenuItemPersonalOrder> getAllMenuItemPersonalOrder() {
		
		ArrayList<MenuItemPersonalOrder> result = new ArrayList<>();
		
		String queryTarget = "select * from menuitem_personalorder";

		final List<String[]> rsResult = new ArrayList<>();
		
		JDBHelper.iterate(connection, queryTarget, new RSProcessor() {
			@Override
			public void process(ResultSet resultSet) throws SQLException {
				String[] data = new String[3];
				data[0] = resultSet.getString(1);
				data[1] = resultSet.getString(2);
				data[2] = resultSet.getString(3);

				rsResult.add(data);
			}
		});

		result = getMenuItemPersonalOrderCollection(rsResult);
		
		size = result.size();
		
		return result;
	}
	
	private ArrayList<MenuItemPersonalOrder> getMenuItemPersonalOrderCollection(List<String[]> Data) {
		int tempMenuItemID;
		int tempPersonalOrderID;
		int tempCount;
		
		ArrayList<MenuItemPersonalOrder> result = new ArrayList<>();
				
		for (String[] line: Data) {
			tempMenuItemID = Integer.parseInt(line[0]);
			tempPersonalOrderID = Integer.parseInt(line[1]);
			tempCount = Integer.parseInt(line[2]);
			result.add(new MenuItemPersonalOrder(tempMenuItemID, tempPersonalOrderID, tempCount));
		}
		
		return result;
	}

	@Override
	public MenuItemPersonalOrder getMenuItemPersonalOrderByPersonalOrderID(
			int personalOrderID) {
		MenuItemPersonalOrder result = null;
		for (MenuItemPersonalOrder menuItemPersonalOrder: collection) {
			if (menuItemPersonalOrder.getPersonalOrderID() == personalOrderID) {
				result = menuItemPersonalOrder;
				break;
			}
		}
		return result;
	}

}
