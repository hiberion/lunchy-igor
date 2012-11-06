package entities;

public class PersonalOrder {
	private int ID;
	private int workerID;
	private int generalOrderID;
	//private double orderSum;
	
	private static int curID = 0;
	
	public PersonalOrder() {
		
	}
	
	public PersonalOrder(int id, int wID, int goID) {
		ID = id;
		workerID = wID;
		generalOrderID = goID;
		//orderSum = 0.0;
	}
	
	public synchronized static int newId() {
		return curID++;
	}
	public synchronized static void initId(int maxId) {
		curID = maxId;
	}
	
	public int getID() {
		return ID;
	}
	public int getWorkerID() {
		return workerID;
	}
	public int getGeneralOrderID() {
		return generalOrderID;
	}
	/*
	public double getOrderSum() {
		return orderSum;
	}
	*/
	
	public void setWorkerID(int id) {
		workerID = id;
	}
	public void setGeneralOrderID(int id) {
		generalOrderID = id;
	}
	
	/*
	public void setOrderSum(double sum) {
		orderSum = sum;
	}
	*/
	
	public String toString() {
		return ID+" "+workerID+" "+generalOrderID;
	}	
}