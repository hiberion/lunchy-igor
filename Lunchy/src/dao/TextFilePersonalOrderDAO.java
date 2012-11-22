package dao;

import igor.lunchy.ParseTextFile;

import java.util.ArrayList;

import entities.PersonalOrder;

public class TextFilePersonalOrderDAO implements IPersonalOrderDAO {

	private ArrayList<PersonalOrder> collection;
	private String fileName;
	
	public TextFilePersonalOrderDAO(String fileName) {
		this.fileName = fileName;
		collection = getPersonalOrderCollection();
	}
	
	private ArrayList<PersonalOrder> getPersonalOrderCollection() {
		int tempID;
		int workerID;
		int generalOrderID;
		
		ArrayList<PersonalOrder> result = new ArrayList<>();
		String[][] Data = ParseTextFile.getStringTable(fileName, "_", 3);
		
		int maxId = 0;
		
		for (String[] line: Data) {
			tempID = Integer.parseInt(line[0]);
			workerID = Integer.parseInt(line[1]);
			generalOrderID = Integer.parseInt(line[2]);
			result.add(new PersonalOrder(tempID, workerID, generalOrderID));
			if (tempID > maxId) {
				maxId = tempID;
			}
		}
		
		PersonalOrder.initId(maxId + 1);
		
		PersonalOrder.initId(result.size());
		
		return result;
	}
	
	private boolean putPersonalOrderCollection(ArrayList<PersonalOrder> personalOrderCollection) {
		int colSize = personalOrderCollection.size();
		String[][] r1 = new String[colSize][3];
		for (int i = 0; i < colSize; i++) {
			r1[i] = personalOrderCollection.get(i).toStringArray();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		return result;
	}

	@Override
	public boolean updateAll() {
		return putPersonalOrderCollection(collection);
	}

	@Override
	public ArrayList<PersonalOrder> getAllPersonalOrder() {
		ArrayList<PersonalOrder> result = new ArrayList<>(collection);
		return result;
	}

	@Override
	public int addPersonalOrder(PersonalOrder personalOrder) {
		collection.add(personalOrder);
		return collection.size()-1;
	}

	@Override
	public boolean updatePersonalOrder(PersonalOrder personalOrder) {
		collection.set(personalOrder.getId(), personalOrder);
		return true;
	}

	@Override
	public PersonalOrder getPersonalOrderByID(int ID) {
		PersonalOrder result = null;
		for (PersonalOrder personalOrder: collection) {
			if (personalOrder.getId() == ID) {
				result = personalOrder;
				break;
			}
		}
		return result;
	}

	@Override
	public boolean removePersonalOrder(int ID) {
		int deletingItem = -1;
		for (int i = 0; i < collection.size(); i++) {
			if (collection.get(i).getId() == ID) {
				deletingItem = i;
				break;
			}
		}
		
		if (deletingItem != -1) {
			collection.remove(deletingItem);
			return true;
		} else
			return false;
	}
}
