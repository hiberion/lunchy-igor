package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.MenuItem;

public class JDBCMenuItemDAO implements IMenuItemDAO {
	
	Connection connection;
	
    
    private String errMessage = null;
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
		
		String queryTarget = "select * from menuitem";

		final List<String[]> rsResult = new ArrayList<>();

		JDBHelper.iterate(connection, queryTarget, new RSProcessor() {
			@Override
			public void process(ResultSet resultSet) throws SQLException {
				String[] data = new String[6];
				data[0] = resultSet.getString(1);
				data[1] = resultSet.getString(2);
				data[2] = resultSet.getString(3);
				data[3] = resultSet.getString(4);
				data[4] = resultSet.getString(5);
				data[5] = resultSet.getString(6);

				rsResult.add(data);
			}
		});

		result = getMenuItemCollection(rsResult);
		
		return result;
	}
	
	private ArrayList<MenuItem> getMenuItemCollection(List<String[]> Data) {
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

	/* (non-Javadoc)
	 * @see dao.IMenuItemDAO#addMenuItem(entities.MenuItem)
	 */
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
	        Statement statement = connection.createStatement();
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
	        Statement statement = connection.createStatement();
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
