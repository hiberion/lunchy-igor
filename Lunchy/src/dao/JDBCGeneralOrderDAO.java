package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.GeneralOrder;

public class JDBCGeneralOrderDAO implements IGeneralOrderDAO {

	Connection connection;
	int size = 0;
	
	private String errMessage = null;
    private ArrayList<GeneralOrder> collection;
	
	public JDBCGeneralOrderDAO(Connection connection) {
		this.connection = connection;
		collection = getAllGeneralOrder();
	}
	
	@Override
	public int addGeneralOrder(GeneralOrder generalOrder) {
		collection.add(generalOrder);
		@SuppressWarnings("unused")
		int ret = 0;
		
		String query =
				"insert into general_order (id, date) values("+
						generalOrder.getID() + ",'" +
						generalOrder.getOrderDate() + "')";
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
	public boolean updateGeneralOrder(GeneralOrder generalOrder) {
		collection.set(generalOrder.getID(), generalOrder);
		
		int ret = 0;  // for result
		
		String query =
				"update general_order set " +
				"date = '" + generalOrder.getOrderDate() + "'," + 
				" where id = " + generalOrder.getID();
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
	public GeneralOrder getGeneralOrderByID(int ID) {
		GeneralOrder result = null;
		for (GeneralOrder generalOrder: collection) {
			if (generalOrder.getID() == ID) {
				result = generalOrder;
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
	public GeneralOrder getGeneralOrderByDate(Date date) {
		Date modifDate = null;
		String strDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		strDate = sdf.format(date);
		try {
			modifDate = sdf.parse(strDate);
		} catch (ParseException e) {
			System.out.println("getOrderDateForm2 Error");
		}
		GeneralOrder result = null;
		for (GeneralOrder generalOrder: collection) {
			if (generalOrder.getOrderDateForm2().equals(modifDate)) {
				result = generalOrder;
				break;
			}
		}
		return result;
	}

	@Override
	public ArrayList<GeneralOrder> getAllGeneralOrder() {
		
		ArrayList<GeneralOrder> result = new ArrayList<>();
		
		String queryTarget = "select * from general_order";

		final List<String[]> rsResult = new ArrayList<>();

		JDBHelper.iterate(connection, queryTarget, new RSProcessor() {
			@Override
			public void process(ResultSet resultSet) throws SQLException {
				String[] data = new String[2];
				data[0] = resultSet.getString(1);
				data[1] = resultSet.getString(2);

				rsResult.add(data);
			}
		});

		result = getGeneralOrderCollection(rsResult);
		
		size = result.size();
		
		return result;
	}
	
	private ArrayList<GeneralOrder> getGeneralOrderCollection(List<String[]> Data) {
		int tempID;
		String tempDate;
		
		ArrayList<GeneralOrder> result = new ArrayList<>();
		
		int maxId = 0;
		
		for (String[] line: Data) {
			tempID = Integer.parseInt(line[0]);
			tempDate = line[1];
			result.add(new GeneralOrder(tempID, tempDate));
			if (tempID > maxId) {
				maxId = tempID;
			}
		}
		return result;
	}

}
