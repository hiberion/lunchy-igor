package entities;

public class Worker {
	private int ID;
	private String fullName;
	
	public static int curID = 1;
	
	public Worker(int id, String fullName) {
		ID = id;
		this.fullName = new String(fullName);
	}
	
	public int getID() {
		return ID;
	}
	
	public String getName() {
		return fullName;
	}
	
	public String[] ToStrArr() {
		String[] result = new String[2];
		result[0] = String.valueOf(ID);
		result[1] = new String(fullName);
		return result;		
	}
	
	public String ToStr() {
		String result;
		result = ToStrArr()[0] + " " + ToStrArr()[1];
		return result;
	}
	
}
