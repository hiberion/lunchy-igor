package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.MenuItem;
import entities.Worker;

public class JDBCWorkerDAO implements IWorkerDAO {
	
	Connection connection;
	int size = 0;
	//Statement statement;
    //ResultSet resultSet;
    
	private String errMessage = null;
	private ArrayList<Worker> collection;
	
    public JDBCWorkerDAO(Connection connection) {
		this.connection = connection;
		collection = getAllWorker();
	}

	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Worker getWorkerByID(int ID) {
		return collection.get(ID);
	}

	@Override
	public ArrayList<Worker> getAllWorker() {
		
		ArrayList<Worker> result = new ArrayList<>();
		
		String queryTarget = "select * from worker";

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

		result = getWorkerCollection(rsResult);
		
		size = result.size();
		
		return result;
	}
	
	private ArrayList<Worker> getWorkerCollection(List<String[]> Data) {
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
