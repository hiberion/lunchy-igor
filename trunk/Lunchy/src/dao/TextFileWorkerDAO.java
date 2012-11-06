package dao;

import igor.lunchy.ParseTextFile;
import java.util.ArrayList;
import entities.Worker;

public class TextFileWorkerDAO implements IWorkerDAO {
	
	private ArrayList<Worker> collection;
	private String fileName;
	
	
	public TextFileWorkerDAO(String fileName) {
		this.fileName = fileName;
		collection = getWorkerCollection(fileName);
	}
	
	private ArrayList<Worker> getWorkerCollection(String fileName) {
		int tempID;
		String tempName;
		
		ArrayList<Worker> result = new ArrayList<>();
		String[][] Data = ParseTextFile.getStringTable(fileName, "_", 2);
		
		int maxId = 0;
		
		for (String[] line: Data) {
			tempID = Integer.parseInt(line[0]);
			tempName = line[1];
			result.add(new Worker(tempID, tempName));
			if (tempID > maxId) {
				maxId = tempID;
			}
		}
		
		Worker.initId(maxId + 1);
		
		Worker.initId(result.size());
		return result;
	}
	
	private boolean putWorkerCollection(ArrayList<Worker> workerCollection) {
		int colSize = workerCollection.size();
		String[][] r1 = new String[colSize][2];
		for (int i = 0; i < colSize; i++) {
			r1[i] = workerCollection.get(i).toStringArray();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		return result;
	}

	@Override
	public Worker getWorkerByID(int ID) {
		Worker result = collection.get(ID);
		return result;
	}

	@Override
	public ArrayList<Worker> getAllWorker() {
		ArrayList<Worker> result = new ArrayList<>(collection);
		return result;
	}

	@Override
	public boolean updateAll() {
		return putWorkerCollection(collection);
	}

}
