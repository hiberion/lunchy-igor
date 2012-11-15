package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Category;

public class JDBCCategoryDAO implements ICategoryDAO {
	
	Connection connection;
	int size = 0;
	//Statement statement;
    //ResultSet resultSet;
    @SuppressWarnings("unused")
	private String errMessage = null;
    private ArrayList<Category> collection;
    
    public JDBCCategoryDAO(Connection connection) {
		this.connection = connection;
		collection = getAllCategory();
	}

	@Override
	public boolean updateAll() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Category getCategoryByID(int ID) {
		return collection.get(ID);
	}

	@Override
	public ArrayList<Category> getAllCategory() {
		
		ArrayList<Category> result = new ArrayList<>();
		
		String queryTarget = "select * from category";

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

		result = getCategoryCollection(rsResult);
		
		size = result.size();
		
		return result;
	}
	
	private ArrayList<Category> getCategoryCollection(List<String[]> Data) {
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