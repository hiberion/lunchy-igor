package entities;

public class Category {
	private int ID;
	private String name;
	
	private static int curID = 1;
	
	public Category() {
		
	}
	
	public Category(int id, String name) {
		ID = id;
		this.name = name;
	}
	
	public Category(String name) {
		ID = newId();
		this.name = name;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] toStringArray() {
		String[] result = new String[2];
		result[0] = String.valueOf(ID);
		result[1] = name;
		return result;		
	}
	
	public String toString() {
		return String.valueOf(ID)+" "+name;
	}
	
	
	public synchronized static int newId() {
		return curID++;
	}

	public synchronized static void initId(int maxId) {
		curID = maxId;
	}
}
