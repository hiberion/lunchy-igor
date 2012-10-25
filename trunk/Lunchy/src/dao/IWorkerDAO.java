package dao;

import java.util.ArrayList;

import entities.Worker;;

public interface IWorkerDAO {
	public ArrayList<Worker> getWorkerCol();
	public boolean putWorkerCol(ArrayList<Worker> workerCol);
}
