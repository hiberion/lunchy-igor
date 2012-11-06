package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Category;

public class JDBCCategoryDAO implements ICategoryDAO {
	
	Connection connection;
	Statement statement;
    ResultSet resultSet;
    String errMessage = null;
    
    public JDBCCategoryDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Category getCategoryByID(int ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Category> getAllCategory() {
		
		ArrayList<Category> result = new ArrayList<>();
		
		try {
			String queryTarget = "select * from category";
			String queryCountRecords = "select count(*) from category";
	        //String query = "insert into \"Menu\"(ID, \"Name\") values (6, 'RRR') ";
	        statement = connection.createStatement();
	        resultSet = statement.executeQuery(queryCountRecords);
	        int rsSize = 0;
	        while (resultSet.next())
	        	rsSize = resultSet.getInt(1);
	        resultSet.close();
	        
	        resultSet = statement.executeQuery(queryTarget);	        
	        try {
	        	      	
	        	String[][] data = new String[rsSize][2];
	        	int index = 0;
	        	
	        	while (resultSet.next()) {
	        		data[index][0] = resultSet.getString(1);
	        		data[index][1] = resultSet.getString(2);
	        		//System.out.println(data[index][0] + " " + data[index][1]);
	        		index++;
	        	}
	        	resultSet.close();
	        	
	        	result = getCategoryCollection(data);
	           
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
	
	private ArrayList<Category> getCategoryCollection(String[][] Data) {
		int tempID;
		String tempName;
		
		ArrayList<Category> result = new ArrayList<>();
		
		int maxId = 0;
		
		for (String[] line: Data) {
			tempID = Integer.parseInt(line[0]);
			tempName = line[1];
			result.add(new Category(tempID, tempName));
			if (tempID > maxId) {
				maxId = tempID;
			}
		}
		Category.initId(result.size());
		return result;
	}

}