package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.MenuItem;

public class JDBCMenuItemDAO implements IMenuItemDAO {
	
	Connection connection;
	Statement statement;
    ResultSet resultSet;
    String errMessage = null;
    private ArrayList<MenuItem> collection;
	
	public JDBCMenuItemDAO(Connection connection) {
		this.connection = connection;
		collection = getAllMenuItem();
	}

	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<MenuItem> getAllMenuItem() {
		
		ArrayList<MenuItem> result = new ArrayList<>();
		
		try {
			String queryTarget = "select * from menuitem";
			String queryCountRecords = "select count(*) from menuitem";
	        //String query = "insert into \"Menu\"(ID, \"Name\") values (6, 'RRR') ";
	        statement = connection.createStatement();
	        resultSet = statement.executeQuery(queryCountRecords);
	        int rsSize = 0;
	        while (resultSet.next())
	        	rsSize = resultSet.getInt(1);
	        resultSet.close();
	        
	        resultSet = statement.executeQuery(queryTarget);	        
	        try {
	        	      	
	        	String[][] data = new String[rsSize][6];
	        	int index = 0;
	        	
	        	while (resultSet.next()) {
	        		data[index][0] = resultSet.getString(1);
	        		data[index][1] = resultSet.getString(2);
	        		data[index][2] = resultSet.getString(3);
	        		data[index][3] = resultSet.getString(4);
	        		data[index][4] = resultSet.getString(5);
	        		data[index][5] = resultSet.getString(6);
	        		//System.out.println(data[index][0] + " " + data[index][1] + " " + data[index][2] + " " +
	        			//	data[index][3] + " " + data[index][4] + " " + data[index][5]);
	        		index++;
	        	}
	        	resultSet.close();
	        	
	        	result = getMenuItemCollection(data);
	           
	        } catch (SQLException e) {
	            errMessage = e.getMessage();
	            System.out.println(errMessage);
	        }			
		} catch (SQLException e) {
			errMessage = e.getMessage();
	        System.out.println(errMessage);
		}
				
		
		return result;
	}
	
	private ArrayList<MenuItem> getMenuItemCollection(String[][] Data) {
		int tempID;
		String tempName;
		int tempCategory;
		String tempDescript;
		double tempPrice;
		boolean tempAvail = true;
		
		ArrayList<MenuItem> result = new ArrayList<>();
		
		int maxId = 0;
		
		for (String[] line: Data) {
			tempID = Integer.parseInt(line[0]);
			tempName = line[1];
			tempCategory = Integer.parseInt(line[2]);
			tempDescript = (line[3] == null) ? new String("") : line[3];
			tempPrice = Double.parseDouble(line[4]);
			tempAvail = (line[5]).equals("t") ? true : false;
			result.add(new MenuItem(tempID, tempName, tempCategory,
					tempDescript, tempPrice, tempAvail));
			if (tempID > maxId) {
				maxId = tempID;
			}
		}
		MenuItem.initId(result.size());
		return result;
	}

	@Override
	public int addMenuItem(MenuItem menuItem) {
		collection.add(menuItem);
		@SuppressWarnings("unused")
		int ret = 0;
		
		String query =
				"insert into menuitem (id, name, category_id, description, price, availability) values("+
						menuItem.getID() + ",'" +
						menuItem.getName() + "'," +
						menuItem.getCategory() + ",'" +
						menuItem.getDescr() + "'," +
						menuItem.getPrice() + "," +
						menuItem.getAvail() + ")";
		//System.out.println(query);
		
		try {
	        statement = connection.createStatement();
	        ret = statement.executeUpdate(query);
	        			
		} catch (SQLException e) {
			errMessage = e.getMessage();
	        System.out.println(errMessage);
		}
		
		return collection.size()-1;
	}

	@Override
	public boolean updateMenuItem(MenuItem menuItem) {
		collection.set(menuItem.getID(), menuItem);
		
		int ret = 0;  // for result
		
		String query =
				"update menuitem set " +
				"name = '" + menuItem.getName() + "'," +
				"category_id = " + menuItem.getCategory() + "," +
				"description = '" + menuItem.getDescr() + "'," +
				"price = " + menuItem.getPrice() + "," +
				"availability = " + menuItem.getAvail() + 
				" where id = " + menuItem.getID();
		//System.out.println(query);
		
		try {
	        statement = connection.createStatement();
	        ret = statement.executeUpdate(query);
	        			
		} catch (SQLException e) {
			errMessage = e.getMessage();
	        System.out.println(errMessage);
		}
		
		if (ret == 1)
			return true;
		else
			return false;
	}

	@Override
	public MenuItem getMenuItemByID(int ID) {
		MenuItem result = collection.get(ID);
		return result;
	}


}
