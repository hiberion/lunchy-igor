package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Worker;

public class JDBCWorkerDAO implements IWorkerDAO {
	
	Connection connection;
	Statement statement;
    ResultSet resultSet;
    String errMessage = null;
    
    public JDBCWorkerDAO(Connection connection) {
		this.connection = connection;
	}

	public ArrayList<Worker> getWorkerCol() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean putWorkerCol(ArrayList<Worker> workerCol) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Worker getWorkerByID(int ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Worker> getAllWorker() {
		
		ArrayList<Worker> result = new ArrayList<>();
		
		try {
			String queryTarget = "select * from worker";
			String queryCountRecords = "select count(*) from worker";
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
	        	
	        	result = getWorkerCollection(data);
	           
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
	
	private ArrayList<Worker> getWorkerCollection(String[][] Data) {
		int tempID;
		String tempName;
		
		ArrayList<Worker> result = new ArrayList<>();
		
		int maxId = 0;
		
		for (String[] line: Data) {
			tempID = Integer.parseInt(line[0]);
			tempName = line[1];
			result.add(new Worker(tempID, tempName));
			if (tempID > maxId) {
				maxId = tempID;
			}
		}
		Worker.initId(result.size());
		return result;
	}

}
