package dao;

import java.util.ArrayList;

import entities.Worker;

public interface IWorkerDAO {
	
	//public int addWorker(Worker worker);
	//public boolean updateWorker(Worker worker);
	public boolean updateAll();
	public Worker getWorkerByID(int ID);
	public ArrayList<Worker> getAllWorker();
	
	
	//public ArrayList<Worker> getWorkerCol();
	//public boolean putWorkerCol(ArrayList<Worker> workerCol);
}
