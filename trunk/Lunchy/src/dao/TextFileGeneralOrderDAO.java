package dao;

import igor.lunchy.ParseTextFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entities.GeneralOrder;

public class TextFileGeneralOrderDAO implements IGeneralOrderDAO {
	
	private ArrayList<GeneralOrder> collection;
	private String fileName;
	
	public TextFileGeneralOrderDAO(String fileName) {
		this.fileName = fileName;
		collection = getGeneralOrderCollection();
	}
	
	private ArrayList<GeneralOrder> getGeneralOrderCollection() {
		int tempID;
		String tempDate;
		
		ArrayList<GeneralOrder> result = new ArrayList<>();
		String[][] Data = ParseTextFile.getStringTable(fileName, "_", 2);
		
		int maxId = 0;
		
		for (String[] line: Data) {
			tempID = Integer.parseInt(line[0]);
			tempDate = line[1];
			result.add(new GeneralOrder(tempID, tempDate));
			if (tempID > maxId) {
				maxId = tempID;
			}
		}
		
		GeneralOrder.initId(maxId + 1);
		
		GeneralOrder.initId(result.size());
		
		return result;
	}
	
	private boolean putGeneralOrderCollection(ArrayList<GeneralOrder> generalOrderCollection) {
		int colSize = generalOrderCollection.size();
		String[][] r1 = new String[colSize][2];
		for (int i = 0; i < colSize; i++) {
			r1[i] = generalOrderCollection.get(i).toStringArray();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		//System.out.println("DEBUG, TFGODAO");
		
		return result;
	}

	@Override
	public boolean updateAll() {
		return putGeneralOrderCollection(collection);
	}

	@Override
	public ArrayList<GeneralOrder> getAllGeneralOrder() {
		ArrayList<GeneralOrder> result = new ArrayList<>(collection);
		return result;
	}

	@Override
	public int addGeneralOrder(GeneralOrder generalOrder) {
		collection.add(generalOrder);
		return collection.size()-1;
	}

	@Override
	public boolean updateGeneralOrder(GeneralOrder generalOrder) {
		collection.set(generalOrder.getId(), generalOrder);
		return true;
	}

	@Override
	public GeneralOrder getGeneralOrderByID(int ID) {
		GeneralOrder result = null;
		for (GeneralOrder generalOrder: collection) {
			if (generalOrder.getId() == ID) {
				result = generalOrder;
				break;
			}
		}
		return result;
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

}
