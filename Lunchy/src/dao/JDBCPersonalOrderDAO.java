package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.PersonalOrder;

public class JDBCPersonalOrderDAO implements IPersonalOrderDAO {
	
	Connection connection;
	int size = 0;
	//Statement statement;
    //ResultSet resultSet;
    
	private String errMessage = null;
	private ArrayList<PersonalOrder> collection;
	
	public JDBCPersonalOrderDAO(Connection connection) {
		this.connection = connection;
		collection = getAllPersonalOrder();
	}

	@Override
	public int addPersonalOrder(PersonalOrder personalOrder) {
		collection.add(personalOrder);
		
		@SuppressWarnings("unused")
		int ret = 0;
		
		String query =
				"insert into personal_order (id, worker_id, gen_order_id) values("+
						personalOrder.getID() + "," +
						personalOrder.getWorkerID() + "," +
						personalOrder.getGeneralOrderID() + ")";
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
	public boolean updatePersonalOrder(PersonalOrder personalOrder) {
		collection.set(personalOrder.getID(), personalOrder);
		
		int ret = 0;  // for result
		
		String query =
				"update personal_order set " +
				"worker_id = " + personalOrder.getWorkerID() + "," +
				"gen_order_id = " + personalOrder.getGeneralOrderID() + "," +
				" where id = " + personalOrder.getID();
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
	public PersonalOrder getPersonalOrderByID(int ID) {
		PersonalOrder result = null;
		for (PersonalOrder personalOrder: collection) {
			if (personalOrder.getID() == ID) {
				result = personalOrder;
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
	public ArrayList<PersonalOrder> getAllPersonalOrder() {
		
		ArrayList<PersonalOrder> result = new ArrayList<>();
		
		String queryTarget = "select * from personal_order";

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

		result = getPersonalOrderCollection(rsResult);
		
		size = result.size();
		
		return result;
	}
	
	private ArrayList<PersonalOrder> getPersonalOrderCollection(List<String[]> Data) {
		int tempID;
		int tempWorkerID;
		int tempGeneralOrderID;
		
		ArrayList<PersonalOrder> result = new ArrayList<>();
		
		int maxId = 0;
		
		for (String[] line: Data) {
			tempID = Integer.parseInt(line[0]);
			tempWorkerID = Integer.parseInt(line[1]);
			tempGeneralOrderID = Integer.parseInt(line[2]);
			result.add(new PersonalOrder(tempID, tempWorkerID, tempGeneralOrderID));
			if (tempID > maxId) {
				maxId = tempID;
			}
		}
		PersonalOrder.initId(result.size());
		return result;
	}
	
	@Override
	public boolean removePersonalOrder(int ID) {
		int deletingItem = -1;
		for (int i = 0; i < collection.size(); i++) {
			if (collection.get(i).getID() == ID) {
				deletingItem = i;
				break;
			}
		}
		
		if (deletingItem != -1) {
			
			@SuppressWarnings("unused")
			int ret = 0;
			
			String query =
					"delete from personal_order where "+
							"id = " + collection.get(deletingItem).getID();
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

}
