package dao;

import igor.lunchy.ParseTextFile;
import java.util.ArrayList;
import entities.Worker;

public class TextFileWorkerDAO implements IWorkerDAO {
	
	String fileName = "Data/workers.txt";

	@Override
	public ArrayList<Worker> getWorkerCol() {
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
		Worker.initId(result.size());
		return result;
	}

	@Override
	public boolean putWorkerCol(ArrayList<Worker> workerCol) {
		int colSize = workerCol.size();
		String[][] r1 = new String[colSize][2];
		for (int i = 0; i < colSize; i++) {
			r1[i] = workerCol.get(i).toStringArray();
		}
		
		boolean result = ParseTextFile.putStringTableToFile(fileName, r1);
		
		return result;
	}

}
