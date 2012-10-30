package entities;

public class Worker {
	private int ID;
	private String fullName;
	
	private static int curID = 1;
	
	public Worker(int id, String fullName) {
		ID = id;
		this.fullName = fullName;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return fullName;
	}
	
	public String[] toStringArray() {
		String[] result = new String[2];
		result[0] = String.valueOf(ID);
		result[1] = fullName;
		return result;		
	}
	
	public String toString() {
		String[] p = toStringArray(); // in-between result
		return p[0]+" "+p[1];
	}
	
	public synchronized static int newId() {
		return curID++;
	}

	public synchronized static void initId(int maxId) {
		curID = maxId;
	}
	
}
